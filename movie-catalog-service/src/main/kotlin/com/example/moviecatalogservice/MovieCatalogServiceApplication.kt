package com.example.moviecatalogservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard
import org.springframework.context.annotation.Bean
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
class MovieCatalogServiceApplication{

	@Bean
	@LoadBalanced
	fun getRestTemplate(): RestTemplate {
		val clientHttpRequest = HttpComponentsClientHttpRequestFactory()
		clientHttpRequest.setConnectTimeout(3000)

		return RestTemplate(clientHttpRequest)
	}

	@Bean
	@LoadBalanced
	fun getWebClient (): WebClient.Builder {
		return WebClient.builder()
	}
}

fun main(args: Array<String>) {
	runApplication<MovieCatalogServiceApplication>(*args)
}
