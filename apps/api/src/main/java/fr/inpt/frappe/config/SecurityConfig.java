package fr.inpt.frappe.config;

import fr.inpt.frappe.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import fr.inpt.frappe.auth.AuthUser;
import fr.inpt.frappe.models.User;
import fr.inpt.frappe.utils;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private final UserRepository userRepository;

	private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	SecurityConfig(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize -> authorize
						.anyRequest().authenticated())
				.oauth2Login(oauth2 -> oauth2
						.userInfoEndpoint(userInfo -> userInfo.oidcUserService(this.oidcUserService())));

		return http.build();
	}

	private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
		final OidcUserService delegate = new OidcUserService();

		return (userRequest) -> {
			logger.debug("OIDC user request");
			OidcUser oidcUser = delegate.loadUser(userRequest);

			logger.debug("Fetch user from database");
			User user = userRepository
					.findByUid(oidcUser.getPreferredUsername())
					.orElse(new User(oidcUser.getPreferredUsername()));

			logger.debug("Update user data");
			user.setFirstname(oidcUser.getAttribute("firstName"));
			user.setLastname(oidcUser.getAttribute("lastName"));
			user.setYear(utils.parseYearTier(oidcUser.getAttribute("yearTier")));
			userRepository.save(user);

			return new AuthUser(user, oidcUser);
		};
	}
}
