package fr.inpt.frappe.controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {

	@Operation(summary = "Login", description = "Initiates the login process.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "302", description = "Redirects to the authentication provider"),
			@ApiResponse(responseCode = "400", description = "Invalid input")
	})
	@GetMapping("/login")
	public void login(
			@Parameter(description = "A callback to redirect to after successful login", required = false) @RequestParam(required = false) String callback,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session)
			throws IOException {

		if (callback != null && !callback.isEmpty())
			session.setAttribute("login_callback_url", callback);

		response.sendRedirect("/oauth2/authorization/authentik");
	}

}
