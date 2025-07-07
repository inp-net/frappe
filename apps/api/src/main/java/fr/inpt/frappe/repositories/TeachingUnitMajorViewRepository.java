package fr.inpt.frappe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import fr.inpt.frappe.models.TeachingUnit;

public interface TeachingUnitMajorViewRepository extends JpaRepository<TeachingUnit, Long>, JpaSpecificationExecutor<TeachingUnit> {

}
