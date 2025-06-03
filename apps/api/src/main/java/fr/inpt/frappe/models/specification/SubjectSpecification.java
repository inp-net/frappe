package fr.inpt.frappe.models.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.Minor;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.models.Subject;
import fr.inpt.frappe.models.TeachingUnit;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class SubjectSpecification {
	public static Specification<Subject> hasTeachingUnit(List<Long> tuIds) {
		return (root, query, cb) -> {
			if (tuIds == null || tuIds.isEmpty())
				return null;
			Join<Subject, TeachingUnit> tus = root.join("teachingUnits");
			return tus.get("id").in(tuIds);
		};
	}

	public static Specification<Subject> hasMajor(Long majorId) {
		return (root, query, cb) -> {
			if (majorId == null)
				return null;
			Join<Subject, TeachingUnit> tu = root.join("teachingUnits", JoinType.LEFT);

			Join<TeachingUnit, Major> major = tu.join("majors", JoinType.LEFT);
			Predicate viaMajor = cb.equal(major.get("id"), majorId);

			Join<TeachingUnit, Minor> minor = tu.join("minors", JoinType.LEFT);
			Join<Minor, Major> minorMajor = minor.join("major", JoinType.LEFT);
			Predicate viaMinor = cb.equal(minorMajor.get("id"), majorId);

			return cb.or(viaMajor, viaMinor);
		};
	}

	public static Specification<Subject> hasMinor(List<Long> minorIds) {
		return (root, query, cb) -> {
			if (minorIds == null || minorIds.isEmpty())
				return null;
			Join<Subject, TeachingUnit> tu = root.join("teachingUnits");
			Join<TeachingUnit, Minor> minors = tu.join("minors");
			return minors.get("id").in(minorIds);
		};
	}

	public static Specification<Subject> hasSchool(Long schoolId) {
		return (root, query, cb) -> {
			if (schoolId == null)
				return null;
			Join<Subject, TeachingUnit> tu = root.join("teachingUnits");

			Join<TeachingUnit, Major> major = tu.join("majors", JoinType.LEFT);
			Join<Major, School> school1 = major.join("school", JoinType.LEFT);
			Predicate viaMajor = cb.equal(school1.get("id"), schoolId);

			Join<TeachingUnit, Minor> minor = tu.join("minors", JoinType.LEFT);
			Join<Minor, Major> minorMajor = minor.join("major", JoinType.LEFT);
			Join<Major, School> school2 = minorMajor.join("school", JoinType.LEFT);
			Predicate viaMinor = cb.equal(school2.get("id"), schoolId);

			return cb.or(viaMajor, viaMinor);
		};
	}
}
