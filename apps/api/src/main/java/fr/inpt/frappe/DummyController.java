package fr.inpt.frappe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.inpt.frappe.models.User;
import fr.inpt.frappe.repositories.UserRepository;

@RestController
public class DummyController {

	@Autowired
	UserRepository users;

	@GetMapping("/")
	public String hello() {
		users.save(new User("un", "homme", 0, null, null, null));

		return "hello world ";
	}
}
