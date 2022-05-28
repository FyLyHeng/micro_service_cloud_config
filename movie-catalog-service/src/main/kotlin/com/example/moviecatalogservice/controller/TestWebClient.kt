package com.example.moviecatalogservice.controller

import com.example.moviecatalogservice.model.*
import com.example.moviecatalogservice.service.MovieCatalogService
import com.example.moviecatalogservice.service.TestingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Collectors

@RestController
@RequestMapping("/test")
class TestWebClient(private val movieCatalogService: TestingService) {

    @Value("\${app.name}")
    var appName:String?=null

    @GetMapping("/{userId}")
    fun listCatalog(@PathVariable userId: String): List<CatalogItem> {
        println("app.name:$appName")
        val listRating  = movieCatalogService.userRating(userId)
        return listRating.userRating.stream().map { movieCatalogService.getMovieInfo(it) }.collect(Collectors.toList())
    }

    @GetMapping("/list/blocking/{userId}")
    fun listCatalog1(@PathVariable userId: String): Array<MovieTest> {
        println("app.name:$appName")
        //val listRating  = movieCatalogService.userRating(userId)
        //return listRating.userRating.stream().map { movieCatalogService.getMovieInfo(it) }.collect(Collectors.toList())
        return movieCatalogService.listMovieInfo()
    }

    @GetMapping("/list/nonBlocking/{userId}")
    fun listCatalog2(@PathVariable userId: String): Mono<Array<MovieTest>> {
        return movieCatalogService.listMovieInfoNon()
    }
}