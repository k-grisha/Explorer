package com.gri.blockchain.explorer.services

import com.gri.blockchain.explorer.entity.BlockEntity
import com.gri.blockchain.explorer.entity.TransactionEntity
import com.gri.blockchain.explorer.repositories.BlockRepository
import com.gri.blockchain.explorer.services.geth.dto.GethBlockDto
import com.gri.blockchain.explorer.services.geth.dto.GethTransactionDto
import com.gri.blockchain.explorer.services.geth.dto.GethTransactionReceiptDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.jvm.optionals.getOrNull

@Service
class BlockService(
    val blockRepository: BlockRepository,
    val gethBlockchainClient: BlockchainClient
) {

    private final val divider18 = BigDecimal("1000000000000000000")


    fun calcFeeForBlockProducer(blockNumber: Long): BigDecimal {
        val block = getBlockEntity(blockNumber)
        val feeSum = block.transactions.map { it.effectiveGasPrice.multiply(BigDecimal(it.gasUsed)) }
            .reduce { acc, n -> acc.plus(n) }
        val burnedFee = block.baseFeePerGas.multiply(BigDecimal(block.gasUsed))
        return feeSum.minus(burnedFee)
    }

    @Transactional
    fun getBlockEntity(blockNumber: Long): BlockEntity {
        return blockRepository.findById(blockNumber).getOrNull() ?: fetchAndPersistBlock(blockNumber)
    }

    fun getLatestBlockEntity(): BlockEntity? {
        return blockRepository.fetchLatestBlock()
    }

    @Transactional
    fun fetchAndPersistBlock(blockNumber: Long): BlockEntity {
        var block = blockRepository.findById(blockNumber).getOrNull()
        if (block == null) {
            block = fetchBlockEntityFromBlockChain(blockNumber)
                ?: throw java.lang.RuntimeException("Can't fetch  block $blockNumber")
            blockRepository.save(block)
        }
        return block
    }


    private fun fetchBlockEntityFromBlockChain(blockNumber: Long): BlockEntity? {
        val blockDto = gethBlockchainClient.getBlock(blockNumber)
        val transactionEntities = blockDto?.transactions?.map {
            val transactionReceipt = gethBlockchainClient.getTransactionReceipt(it.hash)
                ?: throw RuntimeException("Can't fetch transaction ${it.hash}")
            it.toEntity(transactionReceipt)
        }?.toSet()
        return blockDto?.toEntity(transactionEntities ?: setOf())
    }


    fun GethBlockDto.toEntity(transactionEntities: Set<TransactionEntity>) = BlockEntity(
        this.number.hexToLong(),
        this.miner,
        this.gasUsed.hexToLong(),
        this.timestamp.hexToLong(),
        this.baseFeePerGas.hexToBigDecimal(),
        transactionEntities
    )

    fun GethTransactionDto.toEntity(receipt: GethTransactionReceiptDto) = TransactionEntity(
        this.hash,
        this.blockNumber.hexToLong(),
        this.from,
        this.to,
        this.value.hexToBigDecimal(),
        this.gas.hexToLong(),
        this.gasPrice.hexToBigDecimal(),
        this.maxFeePerGas?.hexToBigDecimal(),
        this.maxPriorityFeePerGas?.hexToBigDecimal(),
        this.type.hexToInt(),
        receipt.cumulativeGasUsed.hexToLong(),
        receipt.gasUsed.hexToLong(),
        receipt.effectiveGasPrice.hexToBigDecimal(),
        receipt.status.hexToInt(),
        receipt.transactionIndex.hexToInt()
    )


    fun String.hexToInt(): Int {
        return this.substring(2).toInt(16)
    }

    fun String.hexToLong(): Long {
        return this.substring(2).toLong(16)
    }

    fun String.hexToBigDecimal(): BigDecimal {
        return BigInteger(this.substring(2), 16).toBigDecimal().divide(divider18)
    }

}