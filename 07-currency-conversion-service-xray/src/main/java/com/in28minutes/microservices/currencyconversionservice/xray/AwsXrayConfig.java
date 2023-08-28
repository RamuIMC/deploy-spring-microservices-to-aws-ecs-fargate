package com.in28minutes.microservices.currencyconversionservice.xray;

import java.util.Optional;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.entities.TraceID;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.slf4j.SLF4JSegmentListener;
import com.amazonaws.xray.strategy.SegmentNamingStrategy;
import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy;


@Configuration
public class AwsXrayConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(AwsXrayConfig.class);
static {
		
		
		
		AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard()
			.withDefaultPlugins().withSamplingStrategy(new LocalizedSamplingStrategy()).withSegmentListener(new SLF4JSegmentListener());

		AWSXRay.setGlobalRecorder(builder.build());
		LOGGER.debug("SUCCESS");
}

//	@Bean
//	public RequestInterceptor awsXRayFeignInterceptor() {
//	    return new RequestInterceptor() {
//	        @Override
//	        public void apply(RequestTemplate requestTemplate) {
//	        	TraceID traceId = AWSXRay.getTraceEntity().getTraceId();
//	        	String trace = traceId!=null?traceId.toString():null;
//	            // Add X-Ray tracing headers
//	            requestTemplate.header("AWS-XRAY-TRACE-ID", trace);
//	        }
//	    };
//	}


	@Bean
	public Filter TracingFilter() {
		//return new AWSXRayServletFilter("currency-conversion-service");
		return new AWSXRayServletFilter(SegmentNamingStrategy.dynamic(Optional.ofNullable("currency-conversion-service").orElse(
		        "currency-conversion-xray-filter")));

	}
	
}