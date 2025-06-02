package fr.inpt.frappe.models.specification;

import org.springframework.data.jpa.domain.Specification;

import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.School;
import jakarta.persistence.criteria.Join;

public class MajorSpecification {
	public static Specification<Major> hasSchool(Long schoolId) {
		return (root, query, cb) -> {
			if (schoolId == null)
				return null;
			Join<Major, School> school = root.join("school");
			return cb.equal(school.get("id"), schoolId);
		};
	}
}
