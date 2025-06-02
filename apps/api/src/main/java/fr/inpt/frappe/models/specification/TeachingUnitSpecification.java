package fr.inpt.frappe.models.specification;

import org.springframework.data.jpa.domain.Specification;

import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.Minor;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.models.TeachingUnit;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class TeachingUnitSpecification {
	public static Specification<TeachingUnit> hasMajor(Long majorId) {
		return (root, query, cb) -> {
			if (majorId == null)
				return null;
			Join<TeachingUnit, Major> major = root.join("majors");
			return cb.equal(major.get("id"), majorId);
		};
	}

	public static Specification<TeachingUnit> hasMinor(Long minorId) {
		return (root, query, cb) -> {
			if (minorId == null)
				return null;
			Join<TeachingUnit, Minor> minor = root.join("minors");
			return cb.equal(minor.get("id"), minorId);
		};
	}

	public static Specification<TeachingUnit> hasSchool(Long schoolId) {
		return (root, query, cb) -> {
			if (schoolId == null)
				return null;

			// Path 1: via Major
			Join<TeachingUnit, Major> major = root.join("majors", JoinType.LEFT);
			Join<Major, School> school1 = major.join("school", JoinType.LEFT);
			Predicate viaMajor = cb.equal(school1.get("id"), schoolId);

			// Path 2: via Minor -> Major -> School
			Join<TeachingUnit, Minor> minor = root.join("minors", JoinType.LEFT);
			Join<Minor, Major> minorMajor = minor.join("major", JoinType.LEFT);
			Join<Major, School> school2 = minorMajor.join("school", JoinType.LEFT);
			Predicate viaMinor = cb.equal(school2.get("id"), schoolId);

			return cb.or(viaMajor, viaMinor);
		};
	}

}
