package com.jkbank.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// If your packages are not in default structure, you can use below annotations to specify the packages for component scanning, JPA repositories and entity scanning
/*@ComponentScan(basePackages = "com.jkbank.accounts.controller")
@EnableJpaRepositories(basePackages = "com.jkbank.accounts.repository")
@EntityScan(basePackages = "com.jkbank.accounts.entity")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // enabling JPA Auditing feature in the application to auto populate createdAt, updatedAt fields
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Service API Documentation",
				description = " JK Bank - Accounts Service REST API Documentation",
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
				description = "JK Bank - Accounts Service Documentation",
				url = "https://www.linkedin.com/in/jayakrishnan-j-s-6a5167171/"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
