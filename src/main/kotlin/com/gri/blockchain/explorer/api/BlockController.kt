package com.gri.blockchain.explorer.api

import com.gri.blockchain.explorer.entity.BlockEntity
import com.gri.blockchain.explorer.repositories.BlockRepository
import com.gri.blockchain.explorer.repositories.TransactionRepository
import com.gri.blockchain.explorer.services.geth.GethBlockchainService
import com.gri.blockchain.explorer.services.geth.dto.GethBlockDto
import com.gri.blockchain.explorer.services.geth.dto.GethTransactionReceiptDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.random.Random

@RestController
class BlockController(
    val blockRepository:BlockRepository,
    val gethBlockchainService: GethBlockchainService
) {

    @PostMapping("/api/v1/blocks/{blockNumber}/")
    fun persistBlock(@PathVariable blockNumber: Long): BlockEntity? {

        if (blockNumber <= 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "blockNumber should be >0");
        return gethBlockchainService.persist(blockNumber)

    }

    private final val divider18 = BigDecimal("1000000000000000000")
    fun String.hexToBigDecimal(): BigDecimal {
        return BigInteger(this.substring(2), 16).toBigDecimal().divide(divider18)
    }

    @GetMapping("/api/v1/blocks/{blockNumber}/fee")
    fun getBlockFee(@PathVariable blockNumber: Long): BigDecimal? {
        if (blockNumber <= 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "blockNumber should be >0");
        return gethBlockchainService.calcFee(blockNumber)
    }

    @GetMapping("/api/v1/transaction/{transactionHash}/receipt")
    fun getTransactionReceipt(@PathVariable transactionHash: String): GethTransactionReceiptDto? {
        return gethBlockchainService.fetchTransactionReceipt(transactionHash)
    }

    @GetMapping("/api/v1/blocks/{blockNumber}/entity")
    fun getBlockHandle(@PathVariable blockNumber: Long): BlockEntity? {
        if (blockNumber <= 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "blockNumber should be >0");
        return gethBlockchainService.fetchBlockEntity(blockNumber)
    }

    @GetMapping("/api/v1/blocks/{blockNumber}")
    fun getBlock(@PathVariable blockNumber: Long): GethBlockDto? {
        if (blockNumber <= 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "blockNumber should be >0");
        return gethBlockchainService.fetchBlock(blockNumber)
    }

}