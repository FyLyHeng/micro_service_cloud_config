package com.example.cloudconfigservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class CloudConfigServiceApplication

fun main(args: Array<String>) {
	runApplication<CloudConfigServiceApplication>(*args)
}
