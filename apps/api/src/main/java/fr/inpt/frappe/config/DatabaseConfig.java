package fr.inpt.frappe.config;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
public class DatabaseConfig {

	@Value("${DATABASE_URL:}")
	private String databaseUrl;

	@Bean
	public DataSource dataSource() throws URISyntaxException {
		if (databaseUrl == null || databaseUrl.isEmpty())
			throw new IllegalArgumentException("DATABASE_URL must be set");

		URI dbUri = new URI(databaseUrl);
		return DataSourceBuilder.create()
				.url("jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath())
				.username(dbUri.getUserInfo().split(":")[0])
				.password(dbUri.getUserInfo().split(":")[1])
				.build();
	}
}
