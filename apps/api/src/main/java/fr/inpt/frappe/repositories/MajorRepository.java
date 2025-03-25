package fr.inpt.frappe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.inpt.frappe.models.Major;

public interface MajorRepository extends JpaRepository<Major, Long> {
	Optional<Major> findByUid(String uid);
}
