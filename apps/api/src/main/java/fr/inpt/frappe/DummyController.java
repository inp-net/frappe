package fr.inpt.frappe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.inpt.frappe.auth.AuthUser;
import fr.inpt.frappe.models.User;
import fr.inpt.frappe.repositories.SchoolRepository;
import fr.inpt.frappe.repositories.UserRepository;

@RestController
public class DummyController {

	@Autowired
	UserRepository users;

	@Autowired
	SchoolRepository schools;

	@GetMapping("/")
	public List<User> hello(@AuthenticationPrincipal AuthUser principal) {
		return users.findAll();
	}

	@GetMapping("/me")
	public User me(@AuthenticationPrincipal AuthUser principal) {
		return principal.getUser();
	}
}
