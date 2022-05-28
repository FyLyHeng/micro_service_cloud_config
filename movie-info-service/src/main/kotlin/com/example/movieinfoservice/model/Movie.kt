package com.example.movieinfoservice.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Movie (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id:Long,
        var title:String,
        var overview:String
)