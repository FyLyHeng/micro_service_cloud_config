package com.example.configservice.controller

import com.example.configservice.util.DbConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
@RefreshScope
class TestingValueController {

    @Value("app.list.value")
    lateinit var v0:String //= app.list.value, it not read from yaml

    @Value("\${app.name}")
    lateinit var v1:String

    @Value("\${spring.empty:this is text default value}")
    lateinit var v2:String

//    @Value("\${app.list.value}")
//    lateinit var v3:MutableList<String>

    @Value("\${app.list.value}")
    var v3:MutableList<String>?=null

    //the two type are work the same.

    @Value("#{\${app.my.mapTest}}")
    lateinit var v4:MutableMap<String,Any>

    @Autowired
    var v5 = DbConfig()

    @GetMapping
    fun getValue(): String {
        return "$v0 -- $v1 -- $v2 -- $v3 -- $v4 -- ${v5.ds}"
    }
}