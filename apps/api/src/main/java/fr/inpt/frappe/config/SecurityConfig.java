package fr.inpt.frappe.config;

import fr.inpt.frappe.repositories.MajorRepository;
import fr.inpt.frappe.repositories.SchoolRepository;
import fr.inpt.frappe.repositories.UserRepository;

import java.util.List;
import java.util.Map;

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
	private final UserRepository users;

	@Autowired
	private final SchoolRepository schools;

	@Autowired
	private final MajorRepository majors;

	private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	SecurityConfig(UserRepository users, SchoolRepository schools, MajorRepository majors) {
		this.users = users;
		this.schools = schools;
		this.majors = majors;
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
			User user = users
					.findByUid(oidcUser.getPreferredUsername())
					.orElse(new User(oidcUser.getPreferredUsername()));

			logger.debug("Update user data");
			user.setFirstname(oidcUser.getAttribute("firstName"));
			user.setLastname(oidcUser.getAttribute("lastName"));
			user.setYear(utils.parseYearTier(oidcUser.getAttribute("yearTier")));

			Map<String, Object> oidcMajor = oidcUser.getAttribute("major");

			// Map the user's major school to a local school
			List<String> oidcSchools = utils.getSchoolsFromMajor(oidcMajor);
			utils.findSchool(oidcSchools, schools).ifPresent((school) -> user.setSchool(school));

			// Map the user's major to a local major
			utils.getMajorFromMajor(oidcMajor)
					.ifPresent((majorUid) -> majors.findByUid(majorUid).ifPresent(major -> user.setMajor(major)));

			users.save(user);

			return new AuthUser(user, oidcUser);
		};
	}
}
