package dls.telemetry.features;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import io.micrometer.core.annotation.Timed;

import dls.telemetry.WeatherForecastMapper;
import dls.telemetry.constant.ApplicationConstants;
import dls.telemetry.diagnostics.ApplicationDiagnostics;
import dls.telemetry.features.dto.AddressApiModel;
import dls.telemetry.features.get.GetWeatherForecastResponse;


@RestController
@RequestMapping("/api/weatherForecast")
public class WeatherForecastController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherForecastController.class);
	
	private static String[] Summaries = new String[]{
		    "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
		};
	
	private RestTemplate _restTemplate;
	private ApplicationDiagnostics _diagnostics;
	
	@Autowired
	WeatherForecastMapper _weatherForecastMapper;
	
	public WeatherForecastController(RestTemplate restTemplate, ApplicationDiagnostics diagnostics) {
        this._restTemplate = restTemplate;
        this._diagnostics = diagnostics;
    }
	
	@Timed(value=ApplicationConstants.HTTP_EVENT_PROCESSING_TIME_METRIC_NAME,description=ApplicationConstants.HTTP_EVENT_PROCESSING_TIME_METRIC_DESCRIPTION)
	@GetMapping("/")
    public List<GetWeatherForecastResponse> get(){
		
		LOGGER.info("Request received on WeatherForecastController");
		
		_diagnostics.EventReceived();
		
		// Calling a public API to retrieve random data
		AddressApiModel responseContent
		  = _restTemplate.getForObject("https://random-data-api.com/api/v2/addresses", AddressApiModel.class);
		
		// Connecting to database to retrieve random data
		List<GetWeatherForecastResponse> weatherForecasts = _weatherForecastMapper.findAllWeatherForecast();
		weatherForecasts.size();
		
		Random random = new Random();
        List<GetWeatherForecastResponse> forecastResponses = new ArrayList<>();
        
        for (int index = 1; index <= 5; index++) {
            GetWeatherForecastResponse forecastResponse = new GetWeatherForecastResponse();
            
            forecastResponse.city = responseContent.city;
            forecastResponse.address = responseContent.street_name + " " + responseContent.street_address;
            forecastResponse.date = LocalDate.now().plusDays(index);
            forecastResponse.temperatureC = random.nextInt(55 - (-20) + 1) + (-20);
            forecastResponse.summary = Summaries[random.nextInt(Summaries.length)];
            
            forecastResponses.add(forecastResponse);
        }
        
        return forecastResponses;
    }
}
