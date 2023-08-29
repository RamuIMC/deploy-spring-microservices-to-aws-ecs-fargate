package com.in28minutes.microservices.currencyconversionservice.resource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Subsegment;
import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.in28minutes.microservices.currencyconversionservice.util.containerservice.ContainerMetaDataService;

@RestController
@XRayEnabled
@EnableFeignClients
public class CurrencyConversionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionController.class);

	@Autowired
	private ContainerMetaDataService containerMetaDataService;

	@Value("${CURRENCY_EXCHANGE_URI:http://localhost:8000}")
	private String currencyExchangeHost;

//	@Autowired
//	private RestTemplate restTemplate;
	
	
	private final ExternalServiceClient externalServiceClient;
	
	public CurrencyConversionController(ExternalServiceClient externalServiceClient) {
		this.externalServiceClient = externalServiceClient;
	}
	

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity, @RequestHeader Map<String, String> headers) {
		
		LOGGER.info("REACHED HERE -------------------------");
		printAllHeaders(headers);
//		ResponseEntity<CurrencyConversionBean> responseEntity = restTemplate.getForEntity(
//				currencyExchangeHost + "/api/currency-exchange-microservice/currency-exchange/from/{from}/to/{to}",
//				CurrencyConversionBean.class, createUriVariables(from, to));
		
		ResponseEntity<CurrencyConversionBean> responseEntity = externalServiceClient.getCurrency(from, to);

		CurrencyConversionBean response = responseEntity.getBody();

		BigDecimal convertedValue = quantity.multiply(response.getConversionMultiple());

		String conversionEnvironmentInfo = containerMetaDataService.retrieveContainerMetadataInfo();

		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				convertedValue, response.getExchangeEnvironmentInfo(), conversionEnvironmentInfo);
	}
	private void printAllHeaders(Map<String, String> headers) {
		headers.forEach((key, value) -> {
			LOGGER.info(String.format("Header '%s' = %s", key, value));
		});
	}

	private Map<String, String> createUriVariables(String from, String to) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		return uriVariables;
	}
}
