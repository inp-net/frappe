package fr.inpt.frappe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.inpt.frappe.controllers.dtos.major.MajorCreateDTO;
import fr.inpt.frappe.controllers.dtos.major.MajorUpdateDTO;
import fr.inpt.frappe.mappers.MajorMapper;
import fr.inpt.frappe.models.Major;
import fr.inpt.frappe.models.specification.MajorSpecification;
import fr.inpt.frappe.repositories.MajorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/major")
@Tag(name = "major", description = "Manage majors")
public class MajorController {

	@Autowired
	private MajorRepository majors;

	@Autowired
	private MajorMapper mapper;

	@Operation(summary = "Get all majors", description = "Returns a list of all available majors.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The list of majors")
	})
	@GetMapping("/")
	public List<Major> list(@RequestParam(required = false) Long schoolId) {
		Specification<Major> spec = Specification.where(MajorSpecification.hasSchool(schoolId));
		return majors.findAll(spec);
	}

	@Operation(summary = "Create a new major", description = "Creates and returns a new major.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Major created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "School uid not found", content = @Content)
	})
	@PostMapping("/")
	public ResponseEntity<Major> create(
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The major") @Valid MajorCreateDTO major) {

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(majors.save(mapper.createMajorFromDto(major)));

	}

	@Operation(summary = "Get a major by ID", description = "Retrieves a major by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Major retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Major not found", content = @Content)
	})
	@GetMapping("/{id}")
	public Major read(@PathVariable Long id) {
		return majors.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Major not found"));
	}

	@Operation(summary = "Update a major", description = "Updates the desired major.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Major updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "Major not found", content = @Content)
	})
	@PatchMapping("/{id}")
	public Major update(@PathVariable Long id,
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated major name and discontinued value") @Valid MajorUpdateDTO major) {
		Major majorUpdate = majors.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Major not found"));

		mapper.updateMajorFromDto(major, majorUpdate);
		majors.save(majorUpdate);
		return majorUpdate;
	}

	@Operation(summary = "Delete a major", description = "Deletes the desired major.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Major deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Major not found", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Major major = majors.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Major not found"));
		majors.delete(major);

		return ResponseEntity.noContent().build();
	}
}
