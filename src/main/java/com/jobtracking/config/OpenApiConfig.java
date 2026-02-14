package com.jobtracking.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Job Tracking API",
                version = "1.0",
                description = "Backend API for Job Tracking System"
        )
)
public class OpenApiConfig {
}
