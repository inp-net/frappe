package fr.inpt.frappe.models.specification;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.drew.metadata.Tag;
import com.nimbusds.oauth2.sdk.id.Subject;

import fr.inpt.frappe.models.Document;
import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.Minor;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.models.TeachingUnit;
import fr.inpt.frappe.models.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class DocumentSpecification {

	public static Specification<Document> hasTags(List<Long> tagIds) {
		return (root, query, cb) -> {
			if (tagIds == null || tagIds.isEmpty())
				return null;
			Join<Document, Tag> tags = root.join("tags");
			return tags.get("id").in(tagIds);
		};
	}

	public static Specification<Document> hasSubjects(List<Long> subjectIds) {
		return (root, query, cb) -> {
			if (subjectIds == null || subjectIds.isEmpty())
				return null;
			Join<Document, Subject> subject = root.join("subject");
			return subject.get("id").in(subjectIds);
		};
	}

	public static Specification<Document> hasTeachingUnits(List<Long> teachingUnitIds) {
		return (root, query, cb) -> {
			if (teachingUnitIds == null || teachingUnitIds.isEmpty())
				return null;
			Join<Document, Subject> subject = root.join("subject");
			Join<Subject, TeachingUnit> tu = subject.join("teachingUnits");
			return tu.get("id").in(teachingUnitIds);
		};
	}

	public static Specification<Document> hasAuthor(UUID authorId) {
		return (root, query, cb) -> {
			if (authorId == null)
				return null;
			Join<Document, User> u = root.join("author");
			return u.get("id").in(authorId);
		};
	}

	public static Specification<Document> hasMinor(List<Long> minorIds) {
		return (root, query, cb) -> {
			if (minorIds == null || minorIds.isEmpty())
				return null;
			Join<Document, Subject> subject = root.join("subject");
			Join<Subject, TeachingUnit> tu = subject.join("teachingUnits");
			Join<TeachingUnit, Minor> minors = tu.join("minors");
			return minors.get("id").in(minorIds);
		};
	}

	public static Specification<Document> hasMajor(Long majorId) {
		return (root, query, cb) -> {
			if (majorId == null)
				return null;

			// Path 1: Direct via TeachingUnit -> Major
			Join<Document, Subject> subject = root.join("subject");
			Join<Subject, TeachingUnit> tu = subject.join("teachingUnits");

			// Direct Major Join
			Join<TeachingUnit, Major> directMajor = tu.join("majors", JoinType.LEFT);
			Predicate majorDirect = cb.equal(directMajor.get("id"), majorId);

			// Path 2: Indirect via TeachingUnit -> Minor -> Major
			Join<TeachingUnit, Minor> minor = tu.join("minors", JoinType.LEFT);
			Join<Minor, Major> minorMajor = minor.join("major", JoinType.LEFT);

			Predicate majorViaMinor = cb.equal(minorMajor.get("id"), majorId);

			return cb.or(majorDirect, majorViaMinor);
		};
	}

	public static Specification<Document> hasSchool(Long schoolId) {
		return (root, query, cb) -> {
			if (schoolId == null)
				return null;

			// Join: Document -> Subject -> TeachingUnits
			Join<Document, Subject> subject = root.join("subject");
			Join<Subject, TeachingUnit> teachingUnit = subject.join("teachingUnits");

			// Path 1: TeachingUnit -> Majors -> School
			Join<TeachingUnit, Major> directMajor = teachingUnit.join("majors", JoinType.LEFT);
			Join<Major, School> directSchool = directMajor.join("school", JoinType.LEFT);
			Predicate directMatch = cb.equal(directSchool.get("id"), schoolId);

			// Path 2: TeachingUnit -> Minors -> Major -> School
			Join<TeachingUnit, Minor> minor = teachingUnit.join("minors", JoinType.LEFT);
			Join<Minor, Major> minorMajor = minor.join("major", JoinType.LEFT); // Minor -> Major
			Join<Major, School> indirectSchool = minorMajor.join("school", JoinType.LEFT);
			Predicate indirectMatch = cb.equal(indirectSchool.get("id"), schoolId);

			// Combine both paths
			return cb.or(directMatch, indirectMatch);
		};
	}

	public static Specification<Document> hasYears(List<Integer> years) {
		return (root, query, cb) -> {
			if (years == null || years.isEmpty())
				return null;
			return root.get("year").in(years);
		};
	}
}
