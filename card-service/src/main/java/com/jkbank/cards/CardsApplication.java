package com.jkbank.cards;

import com.jkbank.cards.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = CardsContactInfoDto.class)
@OpenAPIDefinition(
		info = @Info(
				title = "Cards Service API Documentation",
				description = " JK Bank - Cards Service REST API Documentation",
				version = "1.0",
				contact = @Contact(
						name = "Jayakrishnan J S",
						email = "jayakrishnan.jkjs@gmail.com",
						url = "https://www.linkedin.com/in/jayakrishnan-j-s-6a5167171/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0.html"
				)
		),
		externalDocs =  @ExternalDocumentation(
				description = "JK Bank - Cards Service Documentation",
				url = "https://www.linkedin.com/in/jayakrishnan-j-s-6a5167171/"
		)
)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
