package dls.telemetry.features;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import dls.telemetry.constant.ApplicationConstants;
import dls.telemetry.diagnostics.ApplicationDiagnostics;
import dls.telemetry.features.dto.AddressApiModel;
import dls.telemetry.features.get.GetWeatherForecastResponse;


@RestController
@RequestMapping("/api/weatherForecast")
@Tag(name = "WeatherForecast", description = "Endpoints for managing weather forecast.")
public class WeatherForecastController {

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
	
	@Operation(summary = "Get weather forecast data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "WeatherForecast get found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GetWeatherForecastResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Mandatory parameters are missing from the request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication is required", content = @Content),
            @ApiResponse(responseCode = "404", description = "DlsJavatelemetry not found", content = @Content) })
    @GetMapping("/")
    public List<GetWeatherForecastResponse> get(){
		
		_diagnostics.EventReceived();
		
		try {
			StopWatch watchProcessor = new StopWatch();
			watchProcessor.start();
			
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
	        
	        watchProcessor.stop();
	        
	        _diagnostics.EventProcessed(watchProcessor.getTotalTimeMillis());
	        
	        return forecastResponses;
		}
		catch(Exception exception) {
			_diagnostics.EventProcessingFailed(exception);
		}
		
		return null;
    }
	
	@Operation(summary = "Get health check.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "WeatherForecast health check founf", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GetWeatherForecastResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Mandatory parameters are missing from the request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication is required", content = @Content),
            @ApiResponse(responseCode = "404", description = "DlsJavatelemetry not found", content = @Content) })
    @GetMapping("/health")
	@Timed(value=ApplicationConstants.HTTP_EVENT_PROCESSING_TIME_METRIC_NAME,description=ApplicationConstants.HTTP_EVENT_PROCESSING_TIME_METRIC_DESCRIPTION)
    public GetWeatherForecastResponse health(){

		GetWeatherForecastResponse forecastResponse = new GetWeatherForecastResponse();
        
        forecastResponse.city = "City";
        forecastResponse.address = "Address";          
        forecastResponse.date = LocalDate.now();
        forecastResponse.temperatureC = 0;
        forecastResponse.summary = "Summary";
        
        return forecastResponse;
    }
}
