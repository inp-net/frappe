package fr.inpt.frappe.controllers;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import fr.inpt.frappe.controllers.dtos.subject.SubjectCreateDTO;
import fr.inpt.frappe.controllers.dtos.subject.SubjectUpdateDTO;
import fr.inpt.frappe.mappers.SubjectMapper;
import fr.inpt.frappe.models.Subject;
import fr.inpt.frappe.models.specification.SubjectSpecification;
import fr.inpt.frappe.repositories.SubjectRepository;

import java.util.List;

@RestController
@RequestMapping("/subject")
@Tag(name = "Subject", description = "Manage subjects")
public class SubjectController {

	@Autowired
	private SubjectRepository subjects;

	@Autowired
	private SubjectMapper subjectMapper;

	@Operation(summary = "Get all subjects", description = "Returns a list of all available subjects.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The list of subjects")
	})
	@GetMapping("/")
	public List<Subject> list(@RequestParam(required = false) Long schoolId,
			@RequestParam(required = false) Long majorId,
			@RequestParam(required = false) Long minorId,
			@RequestParam(required = false) Long teachingUnitId) {
				
		Specification<Subject> spec = SubjectSpecification.hasSchool(schoolId)
				.and(SubjectSpecification.hasMajor(majorId))
				.and(SubjectSpecification.hasMinor(minorId))
				.and(SubjectSpecification.hasTeachingUnit(teachingUnitId));
		return subjects.findAll(spec);
	}

	@Operation(summary = "Create a new subject", description = "Creates and returns a new subject.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Subject created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "Teaching unit ID not found")
	})
	@PostMapping("/")
	public ResponseEntity<Subject> create(
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The subject") @Valid SubjectCreateDTO subject) {

		Subject subjectToCreate = subjectMapper.createSubjectFromDTO(subject);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(subjects.save(subjectToCreate));
	}

	@Operation(summary = "Get a subject by ID", description = "Retrieves a subject by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Subject retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Subject not found", content = @Content)
	})
	@GetMapping("/{id}")
	public Subject read(@PathVariable Long id) {
		return subjects.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Subject not found"));
	}

	@Operation(summary = "Update a subject", description = "Updates the desired subject.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Subject updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "Eihter subject not found or teaching unit not found", content = @Content)
	})
	@PatchMapping("/{id}")
	public Subject update(@PathVariable Long id,
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated subject") @Valid SubjectUpdateDTO subject) {
		Subject subjectToUpdate = subjects.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Subject not found"));

		subjectMapper.updateSubjectFromDto(subject, subjectToUpdate);
		return subjects.save(subjectToUpdate);
	}

	@Operation(summary = "Delete a subject", description = "Deletes the desired subject.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Subject deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Subject not found", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Subject subject = subjects.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Subject not found"));
		subjects.delete(subject);

		return ResponseEntity.noContent().build();
	}

}
