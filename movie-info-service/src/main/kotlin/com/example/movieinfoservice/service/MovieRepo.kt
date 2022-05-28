package com.example.movieinfoservice.service

import com.example.movieinfoservice.model.Movie
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepo:JpaRepository<Movie,Long> {
}