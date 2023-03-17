package com.gri.blockchain.explorer.services.geth

import com.fasterxml.jackson.databind.ObjectMapper
import com.gri.blockchain.explorer.entity.BlockEntity
import com.gri.blockchain.explorer.entity.TransactionEntity
import com.gri.blockchain.explorer.services.geth.dto.GethBlockDto
import com.gri.blockchain.explorer.services.geth.dto.GethTransactionDto
import com.gri.blockchain.explorer.services.geth.dto.GethTransactionReceiptDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.math.BigInteger

@Service
class GethBlockchainService(
    val restTemplate: RestTemplate
) {
    private final val headers = HttpHeaders()
    private final val url = "http://localhost:8545"
    private final val divider18 = BigDecimal("1000000000000000000")

    init {
        headers.contentType = MediaType.APPLICATION_JSON
    }

    val mapper = ObjectMapper()

    private fun getFee(transaction: TransactionEntity, baseFeePerGas: BigDecimal): BigDecimal {
        if (transaction.type <= 1) {
            return transaction.gasPrice.multiply(BigDecimal(transaction.gas))
        }
        val basePlusPriority = baseFeePerGas.plus(transaction.maxPriorityFeePerGas!!)
        return if (basePlusPriority > transaction.maxFeePerGas) {
            transaction.maxFeePerGas!!.minus(baseFeePerGas).multiply(BigDecimal(transaction.gas))
        } else {
            basePlusPriority.multiply(BigDecimal(transaction.gas))
        }

    }

    // todo Experimental
    fun calcFee(blockNumber: Long): BigDecimal {
//        val json = File("/Users/ryhorkiladze/IdeaProjects/Explorer/src/main/resources/16794963.json").readText()
        val block = fetchBlock(blockNumber)?.toEntity()
        val feeSum = block?.transactions?.map { getFee(it, block.baseFeePerGas) }?.reduce { acc, n -> acc.plus(n) }
        val gasSum = block?.transactions?.sumOf { it.gas }
        val mapOfFees = block?.transactions?.map { getFee(it, block.baseFeePerGas) to it }
        print(feeSum)
        print("gasSum= $gasSum")
        return feeSum ?: BigDecimal.ZERO
    }


    fun fetchTransactionReceipt(transactionHash: String): GethTransactionReceiptDto? {
        val entity: HttpEntity<JsonRpcRequest> = HttpEntity(JsonRpcRequest.getTransactionReceiptByHash(transactionHash), headers)
        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            object : ParameterizedTypeReference<JsonRpcResponse<GethTransactionReceiptDto>>() {}).body?.result
    }

    fun fetchBlockEntity(blockNumber: Long): BlockEntity? {
        return fetchBlock(blockNumber)?.toEntity()
    }

    fun fetchBlock(blockNumber: Long): GethBlockDto? {
        val entity: HttpEntity<JsonRpcRequest> = HttpEntity(JsonRpcRequest.getBlockByNumber(blockNumber.hex()), headers)
        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            object : ParameterizedTypeReference<JsonRpcResponse<GethBlockDto>>() {}).body?.result
    }


    fun GethBlockDto.toEntity() = BlockEntity(
        this.number.hexToLong(),
        this.miner,
        this.timestamp.hexToLong(),
        this.baseFeePerGas.hexToBigDecimal(),
        this.transactions.toEntity()
    )

    fun List<GethTransactionDto>.toEntity() = this.map { it.toEntity() }

    fun GethTransactionDto.toEntity() = TransactionEntity(
        this.hash,
        this.blockNumber.hexToLong(),
        this.from,
        this.to,
        this.value.hexToBigDecimal(),
        this.gas.hexToLong(),
        this.gasPrice.hexToBigDecimal(),
        this.maxFeePerGas?.hexToBigDecimal(),
        this.maxPriorityFeePerGas?.hexToBigDecimal(),
        this.type.hexToInt()
    )

    fun Long.hex() = "0x" + this.toString(16)

    fun String.hexToInt(): Int {
        return this.substring(2).toInt(16)
    }

    fun String.hexToLong(): Long {
        return this.substring(2).toLong(16)
    }

    fun String.hexToBigDecimal(): BigDecimal {
        return BigInteger(this.substring(2), 16).toBigDecimal().divide(divider18)
    }

    data class JsonRpcResponse<T>(
        val jsonrpc: String,
        val id: Int,
        val result: T
    )

    data class JsonRpcRequest(
        val jsonrpc: String = "2.0",
        val method: String,
        val params: List<Any>,
        val id: Int = 1
    ) {
        companion object {
            fun getBlockByNumber(blockNumber: String) =
                JsonRpcRequest(method = "eth_getBlockByNumber", params = listOf(blockNumber, true))

            fun getTransactionReceiptByHash(hash: String) =
                JsonRpcRequest(method = "eth_getTransactionReceipt", params = listOf(hash))
        }
    }
}