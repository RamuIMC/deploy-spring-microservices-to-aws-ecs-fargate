package com.in28minutes.microservices.currencyconversionservice.xray;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientFactory;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

import com.amazonaws.xray.proxies.apache.http.HttpClientBuilder;

import feign.Client;
import feign.httpclient.ApacheHttpClient;

import feign.Client;
import feign.Feign;
import feign.Retryer;
import feign.httpclient.ApacheHttpClient;



@Configuration
public class CustomFiegnClientConfiguration {
	
	@Bean
	feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.FULL;
    }
	
	@Bean
	public Client client(ApacheHttpClientFactory httpClientFactory,
			HttpClientConnectionManager httpClientConnectionManager,
			FeignHttpClientProperties httpClientProperties) {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setConnectTimeout(httpClientProperties.getConnectionTimeout())
				.setRedirectsEnabled(httpClientProperties.isFollowRedirects())
				.build();
		//log.debug("Setting up aws xray Client bean for FeignClient.");
		return new ApacheHttpClient(
				HttpClientBuilder.create()
					.setConnectionManager(httpClientConnectionManager)
				    .setDefaultRequestConfig(defaultRequestConfig)
				    .build()
		);
	}
		
	@Bean
	public FeignFormatterRegistrar localDateFeignFormatterRegister() {
		//log.debug("Setting up FeignFormatterRegistrar bean for FeignClient");
		return registry -> {
			DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
			registrar.setUseIsoFormat(true);
			registrar.registerFormatters(registry);
		};
	}

//	@Bean
//    public OkHttpClient feignClient() {
//        return new OkHttpClient();
//    }
}
