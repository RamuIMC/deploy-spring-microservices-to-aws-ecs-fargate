package com.in28minutes.microservices.currencyconversionservice.xray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.Subsegment;
import com.amazonaws.xray.slf4j.SLF4JSegmentListener;

@Component
public class CustomSegmentListener extends SLF4JSegmentListener {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomSegmentListener.class);

    @Override
    public void onBeginSegment(Segment segment) {
        super.onBeginSegment(segment);

        // Extract the trace ID from the segment
        String traceId = segment.getTraceId().toString();
        
        // Log the trace ID
        logger.info("Trace ID: {}", traceId);
    }

    @Override
    public void onBeginSubsegment(Subsegment subsegment) {
        // Implement as needed
    }

    @Override
    public void beforeEndSubsegment(Subsegment subsegment) {
        // Implement as needed
    }

    @Override
    public void afterEndSubsegment(Subsegment subsegment) {
        // Implement as needed
    }

}
