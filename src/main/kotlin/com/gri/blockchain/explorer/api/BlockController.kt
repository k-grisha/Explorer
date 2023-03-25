package com.gri.blockchain.explorer.api

import com.gri.blockchain.explorer.entity.BlockEntity
import com.gri.blockchain.explorer.services.BlockService
import com.gri.blockchain.explorer.services.BlockchainClient
import com.gri.blockchain.explorer.services.geth.dto.GethBlockDto
import com.gri.blockchain.explorer.services.geth.dto.GethTransactionReceiptDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import mu.KLogging

@RestController
class BlockController(
    val blockService: BlockService,
    val blockchainClient: BlockchainClient
) {

    @PostMapping("/api/v1/blocks/all-from/{blockNumber}")
    fun persistAllBlock(@PathVariable blockNumber: Long): Int? {
        if (blockNumber <= 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "blockNumber should be >0");
        var i = 0
        while (true){
            try {
                val block = blockService.fetchAndPersistBlock(blockNumber + i)
                logger.info("Block ${block.number} with ${block.transactions.size} transactions persisted")
                Thread.sleep(1_000)
            } catch (e:Exception){
                logger.error("Can't handle any more blocks", e)
                return i
            }
            i++

        }
    }

    @PostMapping("/api/v1/blocks/{blockNumber}/")
    fun persistBlock(@PathVariable blockNumber: Long): BlockEntity? {
        if (blockNumber <= 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "blockNumber should be >0");
        return blockService.fetchAndPersistBlock(blockNumber)
    }

    @GetMapping("/api/v1/blocks/{blockNumber}/entity")
    fun getBlockEntity(@PathVariable blockNumber: Long): BlockEntity? {
        if (blockNumber <= 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "blockNumber should be >0");
        return blockService.getBlockEntity(blockNumber)
    }

    @GetMapping("/api/v1/blocks/latest/entity")
    fun getLatestBlockEntity(): BlockEntity? {
        return blockService.getLatestBlockEntity()
    }

    @GetMapping("/api/v1/blocks/{blockNumber}/fee")
    fun getFeeForBlockProducer(@PathVariable blockNumber: Long): BigDecimal? {
        if (blockNumber <= 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "blockNumber should be >0");
        return blockService.calcFeeForBlockProducer(blockNumber)
    }

    @GetMapping("/api/v1/transaction/{transactionHash}/receipt")
    fun getTransactionReceipt(@PathVariable transactionHash: String): GethTransactionReceiptDto? {
        return blockchainClient.getTransactionReceipt(transactionHash)
    }

    @GetMapping("/api/v1/blocks/{blockNumber}")
    fun getBlock(@PathVariable blockNumber: Long): GethBlockDto? {
        if (blockNumber <= 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "blockNumber should be >0");
        return blockchainClient.getBlock(blockNumber)
    }

    companion object : KLogging()

}