package com.gri.blockchain.explorer

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfiguration {

    @Bean
    fun restTemplate() = RestTemplate()
}