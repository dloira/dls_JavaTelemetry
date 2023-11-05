package dls.telemetry.diagnostics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.distribution.Histogram;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import dls.telemetry.constant.ApplicationConstants;
import dls.telemetry.features.WeatherForecastController;

@Component
public class ApplicationDiagnostics {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationDiagnostics.class);
	
	private MeterRegistry _meterRegistry;
	
	private Counter _httpEventProcessingExceptions;
	private Counter _httpEventProcessingCount;
	private Timer _httpEventProcessingTime;
	
	public ApplicationDiagnostics(MeterRegistry meterRegistry) {
        this._meterRegistry = meterRegistry;
        
        initializeCounters();
    }
	
	private void initializeCounters() {
		_httpEventProcessingCount = Counter.builder(ApplicationConstants.HTTP_EVENT_PROCESSING_COUNT_METRIC_NAME)
        .description(ApplicationConstants.HTTP_EVENT_PROCESSING_COUNT_METRIC_DESCRIPTION)
        .register(_meterRegistry);
		
		_httpEventProcessingExceptions = Counter.builder(ApplicationConstants.HTTP_EVENT_PROCESSING_COUNT_METRIC_NAME)
		        .description(ApplicationConstants.HTTP_EVENT_PROCESSING_COUNT_METRIC_DESCRIPTION)
		        .register(_meterRegistry);
		
		_httpEventProcessingTime = Timer.builder(ApplicationConstants.HTTP_EVENT_PROCESSING_TIME_METRIC_NAME)
				.description(ApplicationConstants.HTTP_EVENT_PROCESSING_TIME_METRIC_DESCRIPTION)
				.publishPercentiles(0.5, 0.95)
				.publishPercentileHistogram()
				.serviceLevelObjectives(Duration.ofMillis(100))
				.minimumExpectedValue(Duration.ofMillis(1))
				.maximumExpectedValue(Duration.ofSeconds(10))
				.register(_meterRegistry);
	}
	
	public void EventProcessingFailed(Exception error)
	{
		_httpEventProcessingExceptions.increment();
		
		LOGGER.error(error.toString());
	}
	
	public void EventReceived()
    {
        _httpEventProcessingCount.increment();

        LOGGER.info(ApplicationLogging.HTTP_EVENT_RECEIVER);
    }

    public void EventProcessed(long processingTime)
    {
        _httpEventProcessingTime.record(Duration.ofMillis(processingTime));

        LOGGER.info(ApplicationLogging.HTTP_EVENT_PROCESSED);
    }
}
