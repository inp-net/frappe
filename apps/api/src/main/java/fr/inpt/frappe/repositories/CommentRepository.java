package fr.inpt.frappe.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.inpt.frappe.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
