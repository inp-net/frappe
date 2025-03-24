package fr.inpt.frappe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.inpt.frappe.models.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
