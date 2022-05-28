package com.example.movieratingservice.controller

import com.example.movieratingservice.model.Rate
import com.example.movieratingservice.model.UserRating
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rating")
class RatingResource {

    @Value("\${app.name}")
    var appName:String?=null

    @GetMapping("/{movieId}")
    fun getRating(@PathVariable movieId:String): Rate {
        return Rate(movieId,3)
    }


    @GetMapping("/user/{userId}")
    fun listRating(@PathVariable userId:String): UserRating {
        println("app.name:$appName")

        return UserRating(userId,mutableListOf(Rate("2",2), Rate("3",2)))
    }
}