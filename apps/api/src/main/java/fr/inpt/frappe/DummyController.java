package fr.inpt.frappe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.inpt.frappe.models.User;
import fr.inpt.frappe.models.UserRepository;

@RestController
public class DummyController {

	@Autowired
	UserRepository users;

	@GetMapping("/")
	public String hello() {
		User user = new User();
		user.setName("bonjour");
		users.save(user);

		return "hello world";
	}
}
