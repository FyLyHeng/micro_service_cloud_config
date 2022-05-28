package com.example.moviecatalogservice.service

import com.example.moviecatalogservice.model.*
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TestingService(@Qualifier("getWebClient") private val webClient: WebClient.Builder) {


//    @HystrixCommand(fallbackMethod = "fallbackRating", commandProperties = [
//        HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100"),
//        HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
//        HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//        HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")])


//  change to use thread split


    @HystrixCommand(fallbackMethod = "fallbackRating",threadPoolKey = "movieInfoPool", threadPoolProperties = [
        HystrixProperty(name = "coreSize", value = "20"),
        HystrixProperty(name = "maxQueueSize",value = "10")])
fun userRating(userId: String): UserRating {
        return webClient.build()
                .get()
                .uri("http://movie-rating-service/rating/user/${userId}")
                .retrieve().bodyToMono(UserRating::class.java).block()!!
    }

    private fun fallbackRating(userId: String): UserRating {
        return UserRating(userId, mutableListOf(Rate("1", 0)))
    }


    @HystrixCommand(fallbackMethod = "fallbackCatalog", commandProperties = [
        HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "200"),
        HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
        HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
        HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")])
    fun getMovieInfo(it: Rate): CatalogItem {
        val movie = webClient.build()
                .get()
                .uri("http://movie-info-service/movie/db/${it.movieId.toLong()}")
                .retrieve().bodyToMono(MovieTest::class.java).block()!!

        return CatalogItem("movie.name", movie.overview, it.rating)
    }

    private fun fallbackCatalog(it: Rate): CatalogItem {
        return CatalogItem("No Movie", "", 0)
    }


//    @HystrixCommand(fallbackMethod = "fallbackCatalog", commandProperties = [
//        HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "200"),
//        HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
//        HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//        HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")])
    fun listMovieInfo(): Array<MovieTest> {
        val movie = webClient.build()
                .get().uri("http://movie-info-service/movie/db2")
                .retrieve()
                .bodyToMono(Array<MovieTest>::class.java).log()

        return movie.block()!!
    }


//    @HystrixCommand(fallbackMethod = "fallbackCatalog", commandProperties = [
//        HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "200"),
//        HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
//        HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//        HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")])
    fun listMovieInfoNon(): Mono<Array<MovieTest>> {
        val movie = webClient.build()
                .get().uri("http://movie-info-service/movie/db2")
                .retrieve()
                .bodyToMono(Array<MovieTest>::class.java).log()

        return movie
    }


}