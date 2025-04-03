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
import io.swagger.v3.oas.annotations.tags.Tag;
import fr.inpt.frappe.controllers.dtos.SchoolCreateDTO;
import fr.inpt.frappe.models.School;
import fr.inpt.frappe.repositories.SchoolRepository;

import java.util.List;

@RestController
@RequestMapping("/school")
@Tag(name = "School", description = "Manage schools")
public class SchoolController {

	@Autowired
	private SchoolRepository schools;

	@Operation(summary = "Get all schools", description = "Returns a list of all available schools.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The list of schools")
	})
	@GetMapping("/")
	public List<School> list() {
		return schools.findAll();
	}

	@Operation(summary = "Create a new school", description = "Creates and returns a new school.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "School created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
	})
	@PostMapping("/")
	public ResponseEntity<School> create(
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The school") SchoolCreateDTO school) {
		School createdSchool = schools.save(new School(school.getUid(), school.getName()));
		return ResponseEntity.status(HttpStatus.CREATED).body(createdSchool);
	}

	@Operation(summary = "Get a school by ID", description = "Retrieves a school by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "School retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "School not found", content = @Content)
	})
	@GetMapping("/{id}")
	public School read(@PathVariable Long id) {
		return schools.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"School not found"));
	}

	@Operation(summary = "Update a school", description = "Updates the desired school.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "School updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "School not found", content = @Content)
	})
	@PatchMapping("/{id}")
	public School update(@PathVariable Long id,
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated school name") String name) {
		School school = schools.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"School not found"));
		school.setName(name);
		schools.save(school);
		return school;
	}

	@Operation(summary = "Delete a school", description = "Deletes the desired school.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "School deleted successfully"),
			@ApiResponse(responseCode = "404", description = "School not found", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		School school = schools.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"School not found"));
		schools.delete(school);

		return ResponseEntity.noContent().build();
	}

}
