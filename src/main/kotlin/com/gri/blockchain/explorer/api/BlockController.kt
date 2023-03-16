package com.gri.blockchain.explorer.api

import com.gri.blockchain.explorer.repositories.BlockRepository
import com.gri.blockchain.explorer.services.geth.GethBlockchainService
import com.gri.blockchain.explorer.services.geth.dto.GethBlockDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class BlockController(
    val repository: BlockRepository,
    val gethBlockchainService: GethBlockchainService
) {


    @GetMapping("/api/v1/blocks/{blockNumber}")
    fun getBlock(@PathVariable blockNumber: Long): GethBlockDto? {
        if (blockNumber <= 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "blockNumber should be >0");
        return gethBlockchainService.fetchBlock(blockNumber)
    }

}