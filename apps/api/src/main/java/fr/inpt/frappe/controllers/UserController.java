package fr.inpt.frappe.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import fr.inpt.frappe.models.Minor;
import fr.inpt.frappe.models.User;
import fr.inpt.frappe.repositories.MinorRepository;
import fr.inpt.frappe.repositories.UserRepository;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Manage users")
public class UserController {

	@Autowired
	private UserRepository users;

	@Autowired
	private MinorRepository minorRepository;

	@Operation(summary = "Get all users", description = "Returns a list of all available schools.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The list of users")
	})
	@GetMapping("/")
	public List<User> list() {
		return users.findAll();
	}

	@Operation(summary = "Get a user by ID", description = "Retrieves a user by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User retrieves successfully"),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content)
	})
	@GetMapping("/{id}")
	public User read(@PathVariable UUID id) {
		return users.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"User not found"));
	}

	@Operation(summary = "Change user's minor", description = "A user can change his minor")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Minor updated succesfully"),
			@ApiResponse(responseCode = "404", description = "User or new minor not found")
	})
	@PatchMapping("/{id}")
	public User update(@PathVariable UUID id, @RequestParam(required = true) Long minor_id) {
		User user = users.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"user not found"));

		Minor minor = minorRepository.findById(minor_id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"New minor not found"));

		user.setMinor(minor);

		return users.save(user);
	}
}
