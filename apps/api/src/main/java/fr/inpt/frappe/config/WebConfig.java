package fr.inpt.frappe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void configureContentNegotiation(@NonNull ContentNegotiationConfigurer configurer) {
		configurer
				.favorParameter(false)
				.ignoreAcceptHeader(true)
				.defaultContentType(MediaType.APPLICATION_JSON);
	}
}
