package com.prgrms.be.intermark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes("BearerAuth",
					new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")
				)
			)
			.addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("springdoc 테스트")
			.description("Springdoc을 사용한 Swagger UI 테스트")
			.version("1.0.0");
	}
}
