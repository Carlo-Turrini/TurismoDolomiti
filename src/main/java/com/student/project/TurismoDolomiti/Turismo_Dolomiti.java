package com.student.project.TurismoDolomiti;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableJpaAuditing
public class Turismo_Dolomiti extends SpringBootServletInitializer {
	private static final Logger logger = LoggerFactory.getLogger(Turismo_Dolomiti.class);
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Turismo_Dolomiti.class);
	}
	public static void main(String[] args) throws Exception {
		logger.info("Turismo Dolomiti started");
		SpringApplication.run(Turismo_Dolomiti.class, args);
	}
}
	