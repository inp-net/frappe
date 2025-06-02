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

import fr.inpt.frappe.controllers.dtos.minor.MinorCreateDTO;
import fr.inpt.frappe.controllers.dtos.minor.MinorUpdateDTO;
import fr.inpt.frappe.mappers.MinorMapper;
import fr.inpt.frappe.models.Minor;
import fr.inpt.frappe.models.specification.MinorSpecification;
import fr.inpt.frappe.repositories.MinorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/minor")
@Tag(name = "minor", description = "Manage minors")
public class MinorController {

	@Autowired
	private MinorRepository minors;

	@Autowired
	private MinorMapper mapper;

	@Operation(summary = "Get all minors", description = "Returns a list of all available minors.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The list of minors")
	})
	@GetMapping("/")
	public List<Minor> list(@RequestParam(required = false) Long schoolId,
			@RequestParam(required = false) Long majorId) {
		Specification<Minor> spec = Specification.where(MinorSpecification.hasMajor(majorId))
				.and(MinorSpecification.hasSchool(schoolId));
		return minors.findAll(spec);
	}

	@Operation(summary = "Create a new minor", description = "Creates and returns a new minor.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Minor created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "Major uid not found", content = @Content)
	})
	@PostMapping("/")
	public ResponseEntity<Minor> create(
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The minor") @Valid MinorCreateDTO minor) {

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(minors.save(mapper.createMinorFromDto(minor)));
	}

	@Operation(summary = "Get a minor by ID", description = "Retrieves a minor by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Minor retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Minor not found", content = @Content)
	})
	@GetMapping("/{id}")
	public Minor read(@PathVariable Long id) {
		return minors.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Minor not found"));
	}

	@Operation(summary = "Update a minor", description = "Updates the desired minor.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Minor updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "Minor not found", content = @Content)
	})
	@PatchMapping("/{id}")
	public Minor update(@PathVariable Long id,
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated minor") @Valid MinorUpdateDTO minor) {
		Minor minorUpdate = minors.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Minor not found"));

		mapper.updateMinorFromDto(minor, minorUpdate);

		return minors.save(minorUpdate);
	}

	@Operation(summary = "Delete a minor", description = "Deletes the desired minor.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Minor deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Minor not found", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Minor minor = minors.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Minor not found"));
		minors.delete(minor);

		return ResponseEntity.noContent().build();
	}
}
