package com.example.configservice.util

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
//@ConfigurationProperties("db.ds")
class DbSetting {

    var url:String?="a"
    var pass:String? = "a"
}