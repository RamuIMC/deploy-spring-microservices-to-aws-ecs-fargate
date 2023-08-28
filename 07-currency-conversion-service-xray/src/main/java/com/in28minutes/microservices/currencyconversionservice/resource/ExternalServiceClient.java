/**
 * 
 */
package com.in28minutes.microservices.currencyconversionservice.resource;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.in28minutes.microservices.currencyconversionservice.xray.CustomFiegnClientConfiguration;

/**
 * @author Ramakrishna
 *
 */
@XRayEnabled
@FeignClient(name = "ExternalService", url = "http://localhost:8000", configuration = CustomFiegnClientConfiguration.class)
public interface ExternalServiceClient {
	
	@GetMapping("/api/currency-exchange-microservice/currency-exchange/from/{from}/to/{to}") // Use {} to specify a path variable
	ResponseEntity<CurrencyConversionBean> getCurrency(@PathVariable("from") String from, @PathVariable("to") String to); // Use @PathVariable to bind the parameter

}
