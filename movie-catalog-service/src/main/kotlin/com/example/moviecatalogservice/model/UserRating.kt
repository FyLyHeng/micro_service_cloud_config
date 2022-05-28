package com.example.moviecatalogservice.model

data class UserRating (
        var userId:String,
        var userRating:MutableList<Rate>
)