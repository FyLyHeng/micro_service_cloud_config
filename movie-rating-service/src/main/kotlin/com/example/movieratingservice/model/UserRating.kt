package com.example.movieratingservice.model

data class UserRating (
        var userId:String,
        var userRating:MutableList<Rate>
)