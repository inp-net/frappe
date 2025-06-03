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
import fr.inpt.frappe.controllers.dtos.teaching_units.TeachingUnitCreateDTO;
import fr.inpt.frappe.controllers.dtos.teaching_units.TeachingUnitUpdateDTO;
import fr.inpt.frappe.mappers.TeachingUnitMapper;
import fr.inpt.frappe.models.TeachingUnit;
import fr.inpt.frappe.models.specification.TeachingUnitSpecification;
import fr.inpt.frappe.repositories.TeachingUnitRepository;

import java.util.List;

@RestController
@RequestMapping("/teachingunit")
@Tag(name = "Teaching Unit", description = "Manage teaching units")
public class TeachingUnitController {

	@Autowired
	private TeachingUnitRepository teachingUnits;

	@Autowired
	private TeachingUnitMapper mapper;

	@Operation(summary = "Get all teaching units", description = "Returns a list of all available teaching units.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The list of teaching units")
	})
	@GetMapping("/")
	public List<TeachingUnit> list(@RequestParam(required = false) Long schoolId,
			@RequestParam(required = false) Long majorId,
			@RequestParam(required = false) List<Long> minorIds) {

		Specification<TeachingUnit> spec = TeachingUnitSpecification.hasSchool(schoolId)
				.and(TeachingUnitSpecification.hasMajor(majorId))
				.and(TeachingUnitSpecification.hasMinor(minorIds));

		return teachingUnits.findAll(spec);
	}

	@Operation(summary = "Create a new teaching unit", description = "Creates and returns a new teaching unit.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Teaching unit created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
	})
	@PostMapping("/")
	public ResponseEntity<TeachingUnit> create(
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The teaching unit") @Valid TeachingUnitCreateDTO teachingUnit) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(teachingUnits.save(mapper.createTeachingUnitFromDto(teachingUnit)));
	}

	@Operation(summary = "Get a teaching unit by ID", description = "Retrieves a teaching unit by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Teaching unit retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Teaching unit not found", content = @Content)
	})
	@GetMapping("/{id}")
	public TeachingUnit read(@PathVariable Long id) {
		return teachingUnits.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Teaching unit not found"));
	}

	@Operation(summary = "Update a teaching unit", description = "Updates the desired teaching unit.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Teaching unit updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "Teaching unit not found", content = @Content)
	})
	@PatchMapping("/{id}")
	public TeachingUnit update(@PathVariable Long id,
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated teaching unit name") @Valid TeachingUnitUpdateDTO teachingUnit) {
		TeachingUnit updatedTeachingUnit = teachingUnits.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Teaching unit not found"));

		mapper.updateTeachingUnitFromDto(teachingUnit, updatedTeachingUnit);

		return teachingUnits.save(updatedTeachingUnit);
	}

	@Operation(summary = "Delete a teaching unit", description = "Deletes the desired teaching unit.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Teaching unit deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Teaching unit not found", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		TeachingUnit teachingUnit = teachingUnits.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Teaching unit not found"));
		teachingUnits.delete(teachingUnit);

		return ResponseEntity.noContent().build();
	}

}
