package com.gri.blockchain.explorer.services

import com.gri.blockchain.explorer.entity.BlockEntity
import com.gri.blockchain.explorer.entity.RewardEntity
import com.gri.blockchain.explorer.entity.TransactionEntity
import com.gri.blockchain.explorer.repositories.BlockRepository
import com.gri.blockchain.explorer.repositories.MevBuilderRepository
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
    val gethBlockchainClient: BlockchainClient,
    val mevBuilderRepository: MevBuilderRepository
) {

    private final val divider18 = BigDecimal("1000000000000000000")


    fun getLatestPersistedBlockEntity(): BlockEntity? {
        return blockRepository.fetchLatestBlock()
    }

    @Transactional
    fun fetchAndPersistBlock(blockNumber: Long): BlockEntity {
        var block = blockRepository.findById(blockNumber).getOrNull()
        if (block == null) {
            block = fetchBlockEntityFromBlockChain(blockNumber)
            blockRepository.save(block)
        }
        return block
    }


    private fun getReward(blockEntity: BlockEntity): RewardEntity {
        val mevRewardValue = getMevReward(blockEntity)
        val mevBuilderName = mevBuilderRepository.findByAddressIgnoreCase(blockEntity.miner)
        return RewardEntity(
            blockEntity.number,
            blockEntity.miner,
            mevBuilderName?.name,
            calcFee(blockEntity),
            mevRewardValue,
            mevRewardValue != null || mevBuilderName != null
        )
    }

    private fun getMevReward(block: BlockEntity): BigDecimal? {
        val lastTransaction = block.transactions.maxBy { it.transactionIndex }
        return if (block.miner != lastTransaction.from) null
        else
            lastTransaction.value

    }

    private fun calcFee(block: BlockEntity): BigDecimal {
        val feeSum = block.transactions.sumOf { it.effectiveGasPrice.multiply(BigDecimal(it.gasUsed)) }
        val burnedFee = block.baseFeePerGas.multiply(BigDecimal(block.gasUsed))
        return feeSum.minus(burnedFee)
    }

    private fun fetchBlockEntityFromBlockChain(blockNumber: Long): BlockEntity {
        val blockDto =
            gethBlockchainClient.getBlock(blockNumber) ?: throw RuntimeException("Can't fetch block $blockNumber")
        val transactionEntities = blockDto.transactions.map {
            val transactionReceipt = gethBlockchainClient.getTransactionReceipt(it.hash)
                ?: throw RuntimeException("Can't fetch transaction ${it.hash}")
            it.toEntity(transactionReceipt)
        }.toSet()
        val blockEntity = blockDto.toEntity(transactionEntities)
        blockEntity.rewardEntity = getReward(blockEntity)
        return blockEntity
    }


    fun GethBlockDto.toEntity(transactionEntities: Set<TransactionEntity>) = BlockEntity(
        this.number.hexToLong(),
        this.miner,
        this.gasUsed.hexToLong(),
        this.timestamp.hexToLong(),
        this.baseFeePerGas.hexToBigDecimal(),
        transactionEntities,
        null
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