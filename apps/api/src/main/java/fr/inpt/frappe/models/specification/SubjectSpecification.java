package fr.inpt.frappe.models.specification;

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
	public static Specification<Subject> hasTeachingUnit(Long tuId) {
		return (root, query, cb) -> {
			if (tuId == null)
				return null;
			Join<Subject, TeachingUnit> tu = root.join("teachingUnits");
			return cb.equal(tu.get("id"), tuId);
		};
	}

	public static Specification<Subject> hasMajor(Long majorId) {
		return (root, query, cb) -> {
			if (majorId == null)
				return null;
			Join<Subject, TeachingUnit> tu = root.join("teachingUnits");
			Join<TeachingUnit, Major> major = tu.join("majors");
			return cb.equal(major.get("id"), majorId);
		};
	}

	public static Specification<Subject> hasMinor(Long minorId) {
		return (root, query, cb) -> {
			if (minorId == null)
				return null;
			Join<Subject, TeachingUnit> tu = root.join("teachingUnits");
			Join<TeachingUnit, Minor> minor = tu.join("minors");
			return cb.equal(minor.get("id"), minorId);
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
