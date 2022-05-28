package com.example.configservice.util

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("db")
class DbConfig {

//    var ds : MutableList<DbSetting>?=null

    var ds:MutableList<Any>?=null
}