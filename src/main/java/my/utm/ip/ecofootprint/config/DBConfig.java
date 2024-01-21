package my.utm.ip.ecofootprint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DBConfig {
	
	private DriverManagerDataSource dataSource() {
	DriverManagerDataSource source =
	new DriverManagerDataSource();
	source.setDriverClassName("com.mysql.cj.jdbc.Driver");
	source.setUrl("jdbc:mysql://localhost:3306/ecofootprint");
	source.setUsername("root");
	source.setPassword("admin");
	return source;
	}
	
	@Bean
	JdbcTemplate jdbcTemplate() {
	return new JdbcTemplate(dataSource());
	}
}
