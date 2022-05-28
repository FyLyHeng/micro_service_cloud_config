package com.example.moviecatalogservice.controller

import com.example.moviecatalogservice.model.CatalogItem
import com.example.moviecatalogservice.service.MovieCatalogService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors


@RestController
@RequestMapping("/catalog")
class MovieCatalogResource(private val movieCatalogService: MovieCatalogService) {


    @GetMapping("/{userId}")
    fun listCatalog(@PathVariable userId: String): List<CatalogItem> {
        val listRating  = movieCatalogService.userRating(userId)
        return listRating.userRating.stream().map { movieCatalogService.getMovieInfo(it) }.collect(Collectors.toList())
    }
}
