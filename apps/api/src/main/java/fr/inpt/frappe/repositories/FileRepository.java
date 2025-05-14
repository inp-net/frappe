package fr.inpt.frappe.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.inpt.frappe.models.File;

public interface FileRepository extends JpaRepository<File, UUID> {

}
