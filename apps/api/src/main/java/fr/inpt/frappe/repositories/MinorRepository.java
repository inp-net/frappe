package fr.inpt.frappe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import fr.inpt.frappe.models.Minor;

public interface MinorRepository extends JpaRepository<Minor, Long>, JpaSpecificationExecutor<Minor> {

}
