package org.example.rabbitmqtutorial.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Sample API",
                description = "Swagger 테스트.",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig{

    @Bean
    public GroupedOpenApi userOpenApi() {

        return GroupedOpenApi.builder()
                .group("User")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi itemOpenApi() {

        return GroupedOpenApi.builder()
                .group("Item")
                .pathsToMatch("/item/**")
                .build();
    }
}