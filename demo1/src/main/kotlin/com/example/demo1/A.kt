package com.example.demo1

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class A (
        @Id
        var id:Long
)