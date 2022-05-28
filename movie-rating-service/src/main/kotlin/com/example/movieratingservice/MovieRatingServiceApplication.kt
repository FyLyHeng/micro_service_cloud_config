package com.example.movieratingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class MovieRatingServiceApplication

fun main(args: Array<String>) {
	runApplication<MovieRatingServiceApplication>(*args)
}
