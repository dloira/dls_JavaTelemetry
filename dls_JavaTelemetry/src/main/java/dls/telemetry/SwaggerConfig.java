package dls.telemetry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Swagger 1.0")
            .description("A collection of api's for testing telemetry")
            .version("1.0")
            .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }
}
