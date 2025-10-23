package org.patientlog.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "PatientLog App",
        version = "1.0",
        description = "RESTful API for PatientLog Backend"
    )
)
public class SwaggerConfig {

}
