package dls.telemetry.diagnostics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.distribution.Histogram;

import org.springframework.stereotype.Component;

import dls.telemetry.constant.ApplicationConstants;

@Component
public class ApplicationDiagnostics {

	private MeterRegistry meterRegistry;
	
	private Counter _httpEventProcessingCount;
	private Histogram _httpEventProcessingTime;
	
	public ApplicationDiagnostics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        
        initializeCounters();
    }
	
	private void initializeCounters() {
		_httpEventProcessingCount = Counter.builder(ApplicationConstants.HTTP_EVENT_PROCESSING_COUNT_METRIC_NAME)
        .description(ApplicationConstants.HTTP_EVENT_PROCESSING_COUNT_METRIC_DESCRIPTION)
        .register(meterRegistry);
	}
	
	public void EventReceived()
    {
        _httpEventProcessingCount.increment();

//        _logs.HttpEventReceived();
    }

    public void EventProcessed(long processingTime)
    {
        _httpEventProcessingTime.recordLong(processingTime);

//        _logs.HttpEventProcessed();
    }
}
