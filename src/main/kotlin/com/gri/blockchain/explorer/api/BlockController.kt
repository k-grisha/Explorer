package com.gri.blockchain.explorer.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BlockController {

    @GetMapping("/api/v1/blocks/")
    fun getBlock():String{
        return "Hiii sdfgh"
    }
}