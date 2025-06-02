package fr.inpt.frappe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import fr.inpt.frappe.models.Major;

public interface MajorRepository extends JpaRepository<Major, Long>, JpaSpecificationExecutor<Major> {
	Optional<Major> findByUid(String uid);
}
