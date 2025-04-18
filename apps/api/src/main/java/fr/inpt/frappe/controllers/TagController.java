package fr.inpt.frappe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import fr.inpt.frappe.repositories.TagRepository;
import fr.inpt.frappe.models.Tag;

import java.util.List;

@RestController
@RequestMapping("/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag", description = "Manage tags")
public class TagController {

	@Autowired
	private TagRepository tags;

	@Operation(summary = "Get all tags", description = "Returns a list of all available tags.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The list of tags")
	})
	@GetMapping("/")
	public List<Tag> list() {
		return tags.findAll();
	}

	@Operation(summary = "Create a new tag", description = "Creates and returns a new tag.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Tag created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
	})
	@PostMapping("/")
	public ResponseEntity<Tag> create(
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The Tag name") String name) {
		Tag createdTag = tags.save(new Tag(name));
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
	}

	@Operation(summary = "Get a tag by ID", description = "Retrieves a tag by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Tag retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Tag not found", content = @Content)
	})
	@GetMapping("/{id}")
	public Tag read(@PathVariable Long id) {
		return tags.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Tag not found"));
	}

	@Operation(summary = "Update a tag", description = "Updates the desired tag.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Tag updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "Tag not found", content = @Content)
	})
	@PatchMapping("/{id}")
	public Tag update(@PathVariable Long id,
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated tag name") String name) {
		Tag tag = tags.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Tag not found"));
		tag.setName(name);
		return tags.save(tag);
	}

	@Operation(summary = "Delete a tag", description = "Deletes the desired tag.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Tag deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Tag not found", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Tag tag = tags.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Tag not found"));
		tags.delete(tag);

		return ResponseEntity.noContent().build();
	}

}
