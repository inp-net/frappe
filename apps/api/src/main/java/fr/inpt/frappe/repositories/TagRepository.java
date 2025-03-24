package fr.inpt.frappe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.inpt.frappe.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}