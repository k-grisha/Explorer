package com.gri.blockchain.explorer.api

import com.gri.blockchain.explorer.entity.EthBlock
import com.gri.blockchain.explorer.repositories.BlockRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class BlockController(
    val repository: BlockRepository
) {

    @GetMapping("/api/v1/blocks/")
    fun getBlock():String{
        return "Hiii sdfgh"
    }

    @PostMapping("/api/v1/blocks/")
    fun createBlock(): EthBlock {
        val bl = EthBlock(
            id = Random.nextLong(),
            miner = "A",
            number = "1",
            timestamp = "123123"
        )
        return repository.save(bl)
    }
}