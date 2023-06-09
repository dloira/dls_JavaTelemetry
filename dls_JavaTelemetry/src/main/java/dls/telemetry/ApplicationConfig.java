package dls.telemetry;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import dls.telemetry.features.get.GetWeatherForecastResponse;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = {"dls.telemetry"})
@MapperScan("dls.telemetry")
@MappedTypes({GetWeatherForecastResponse.class})
public class ApplicationConfig {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfig.class, args);
	}

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
        		.setConnectTimeout(Duration.ofMillis(3000))
        		.setReadTimeout(Duration.ofMillis(3000))
        		.build();
    }
}
