package fr.inpt.frappe.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.inpt.frappe.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
