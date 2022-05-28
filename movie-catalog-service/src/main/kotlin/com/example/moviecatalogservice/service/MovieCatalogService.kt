package com.example.moviecatalogservice.service

import com.example.moviecatalogservice.model.CatalogItem
import com.example.moviecatalogservice.model.Movie
import com.example.moviecatalogservice.model.Rate
import com.example.moviecatalogservice.model.UserRating
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class MovieCatalogService(private val restTemplate: RestTemplate) {


    @HystrixCommand(fallbackMethod = "fallbackRating", commandProperties = [
        HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "300"),
        HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
        HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
        HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")])
    fun userRating(userId: String): UserRating {
        return restTemplate.getForObject("http://movie-rating-service/rating/user/${userId}", UserRating::class.java)!!
    }

    private fun fallbackRating(userId: String): UserRating {
        return UserRating(userId, mutableListOf(Rate("0", 0)))
    }


    @HystrixCommand(fallbackMethod = "fallbackCatalog", commandProperties = [
        HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "300"),
        HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
        HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
        HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")])
    fun getMovieInfo(it: Rate): CatalogItem {
        val movie: Movie = restTemplate.getForObject("http://movie-info-service/movie/db/${it.movieId.toLong()}", Movie::class.java)!!
        return CatalogItem(movie.title, movie.overview, it.rating)
    }

    private fun fallbackCatalog(it: Rate): CatalogItem {
        return CatalogItem("No Movie", "", 0)
    }
}