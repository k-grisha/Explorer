package com.gri.blockchain.explorer.services.geth

import com.gri.blockchain.explorer.services.BlockchainClient
import com.gri.blockchain.explorer.services.geth.dto.GethBlockDto
import com.gri.blockchain.explorer.services.geth.dto.GethTransactionReceiptDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GethBlockchainClient(
    val restTemplate: RestTemplate
) : BlockchainClient {
    private final val headers = HttpHeaders()

    //    private final val url = "http://localhost:8545"
    private final val url = "https://mainnet.infura.io/v3/54628016bc2c4a06aac27c7f26f8cb45"

    init {
        headers.contentType = MediaType.APPLICATION_JSON
    }

    override fun getTransactionReceipt(transactionHash: String): GethTransactionReceiptDto? {
        val entity: HttpEntity<JsonRpcRequest> =
            HttpEntity(JsonRpcRequest.getTransactionReceiptByHash(transactionHash), headers)
        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            object : ParameterizedTypeReference<JsonRpcResponse<GethTransactionReceiptDto>>() {}).body?.result
    }

    override fun getBlock(blockNumber: Long): GethBlockDto? {
        val entity: HttpEntity<JsonRpcRequest> = HttpEntity(JsonRpcRequest.getBlockByNumber(blockNumber.hex()), headers)
        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            object : ParameterizedTypeReference<JsonRpcResponse<GethBlockDto>>() {}).body?.result
    }

    fun Long.hex() = "0x" + this.toString(16)

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