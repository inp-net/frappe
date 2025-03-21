package fr.inpt.frappe.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Comment, Long> {

}