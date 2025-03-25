package fr.inpt.frappe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.inpt.frappe.models.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
	Optional<School> findByUid(String uid);
}
