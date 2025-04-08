package fr.inpt.frappe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import fr.inpt.frappe.auth.AuthUser;
import fr.inpt.frappe.controllers.dtos.comment.CommentCreateDTO;
import fr.inpt.frappe.controllers.dtos.comment.CommentUpdateDTO;
import fr.inpt.frappe.mappers.CommentMapper;
import fr.inpt.frappe.models.Comment;
import fr.inpt.frappe.repositories.CommentRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
@Tag(name = "Comment", description = "Manage comments")
public class CommentController {

	@Autowired
	private CommentRepository comments;

	@Autowired
	private CommentMapper mapper;

	@Operation(summary = "Get all comments", description = "Returns a list of all available comments.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The list of comments")
	})
	@GetMapping("/")
	public List<Comment> list() {
		return comments.findAll();
	}

	@Operation(summary = "Create a new comment", description = "Creates and returns a new comment.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Comment created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
	})
	@PostMapping("/")
	public ResponseEntity<Comment> create(
			@AuthenticationPrincipal AuthUser principal,
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The comment") @Valid CommentCreateDTO comment) {

		if (comment.getAuthor() == null)
			comment.setAuthor(principal.getUser().getId());

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(comments.save(mapper.createDocumentFromDto(comment)));

	}

	@Operation(summary = "Get a comment by ID", description = "Retrieves a comment by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Comment retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
	})
	@GetMapping("/{id}")
	public Comment read(@PathVariable UUID id) {
		return comments.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Comment not found"));
	}

	@Operation(summary = "Update a comment", description = "Updates the desired comment.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Comment updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
	})
	@PatchMapping("/{id}")
	public Comment update(@PathVariable UUID id,
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated comment name") @Valid CommentUpdateDTO comment) {
		Comment commentUpdate = comments.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Comment not found"));

		mapper.updateCommentFromDto(comment, commentUpdate);

		return comments.save(commentUpdate);
	}

	@Operation(summary = "Delete a comment", description = "Deletes the desired comment.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		Comment comment = comments.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Comment not found"));
		comments.delete(comment);

		return ResponseEntity.noContent().build();
	}

}
