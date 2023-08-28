package com.in28minutes.microservices.currencyexchangeservice.xray;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorder;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.slf4j.SLF4JSegmentListener;

@Configuration
public class AwsXrayConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AwsXrayConfig.class);
	static {
			
			
			
			AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard()
				.withDefaultPlugins()
				.withSegmentListener(new SLF4JSegmentListener());

			AWSXRay.setGlobalRecorder(builder.build());
			LOGGER.debug("SUCCESS");
	}

	@Bean
	public Filter TracingFilter() {
		return new AWSXRayServletFilter("currency-exchange-service");
	}
}