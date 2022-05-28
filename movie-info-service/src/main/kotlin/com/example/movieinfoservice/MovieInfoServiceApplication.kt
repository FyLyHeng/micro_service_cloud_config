package com.example.movieinfoservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
@EnableEurekaClient
class MovieInfoServiceApplication{

	@Bean
	fun getRestTemplate(): RestTemplate {
		return RestTemplate()
	}

	@Bean
	@LoadBalanced
	fun getWebClient (): WebClient {
		return WebClient.create()
	}
}


fun main(args: Array<String>) {
	runApplication<MovieInfoServiceApplication>(*args)
}
