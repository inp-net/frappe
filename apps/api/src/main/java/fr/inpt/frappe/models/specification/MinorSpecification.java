package fr.inpt.frappe.models.specification;

import org.springframework.data.jpa.domain.Specification;

import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.Minor;
import fr.inpt.frappe.models.School;
import jakarta.persistence.criteria.Join;

public class MinorSpecification {
	public static Specification<Minor> hasMajor(Long majorId) {
		return (root, query, cb) -> {
			if (majorId == null)
				return null;
			Join<Minor, Major> major = root.join("major", jakarta.persistence.criteria.JoinType.INNER);
			return cb.equal(major.get("id"), majorId);
		};
	}

	public static Specification<Minor> hasSchool(Long schoolId) {
		return (root, query, cb) -> {
			if (schoolId == null)
				return null;
			Join<Minor, Major> major = root.join("major", jakarta.persistence.criteria.JoinType.INNER);
			Join<Major, School> school = major.join("school", jakarta.persistence.criteria.JoinType.INNER);
			return cb.equal(school.get("id"), schoolId);
		};
	}
}
