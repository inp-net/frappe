package fr.inpt.frappe.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		String token = jwtProvider.generateToken(authentication);
		String callback = (String) request.getSession().getAttribute("login_callback_url");

		if (callback != null) {
			request.getSession().removeAttribute("login_callback_url");
			response.sendRedirect(callback + "?token=" + token);
			return;
		}

		response.setContentType("application/json");
		response.getWriter().write("{\"token\": \"" + token + "\"}");
		response.getWriter().flush();
	}
}
