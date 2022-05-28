package com.example.movieinfoservice.controller

import com.example.movieinfoservice.model.Movie
import com.example.movieinfoservice.model.MovieSummary
import com.example.movieinfoservice.model.TestDomain
import com.example.movieinfoservice.service.MovieRepo
import org.aspectj.weaver.ast.Test
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@RestController
@RequestMapping("/movie")
class MovieResource(@Qualifier("getWebClient") private val webClient: WebClient,private val restTemplate: RestTemplate,private val movieRepo: MovieRepo) {

    @Value("\${app.name}")
    var appName:String?=null
    @Value("\${apiKey}")
    lateinit var apiKey:String


    @GetMapping("/{movieId}")
    fun getMovieInfo(@PathVariable movieId:Long): Movie {
        println("app.name:$appName")
        val movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/$movieId?api_key=$apiKey"
                ,MovieSummary::class.java)!!
        return Movie(movieId,movieSummary.title,movieSummary.overview)
    }


    @GetMapping("/db/{movieId}")
    fun getMovieInfoDB(@PathVariable movieId:Long): Movie {
        println("app.name:$appName")
        return movieRepo.getById(movieId)
    }

    @GetMapping("/db1")
    fun listMovieInfoDB1(): Page<Movie> {
        println("app.name:$appName")
        val params : Pageable = PageRequest.of(0,10)
        return movieRepo.findAll(params)
    }

    @GetMapping("/db2")
    fun listMovieInfoDB2(): MutableList<Movie> {
        println("app.name:$appName")
        return movieRepo.findAll().subList(0,10)
    }


    @GetMapping("/test/{id}")
    fun getTest(@PathVariable id:Long): TestDomain {
        val a = webClient
                .get()
                .uri("https://jsonplaceholder.typicode.com/posts/${id}")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(TestDomain::class.java).block()
        return a?:TestDomain(1,"test")
    }
}