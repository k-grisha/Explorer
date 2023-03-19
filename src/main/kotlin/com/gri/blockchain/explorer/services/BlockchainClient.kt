package com.gri.blockchain.explorer.services

import com.gri.blockchain.explorer.services.geth.dto.GethBlockDto
import com.gri.blockchain.explorer.services.geth.dto.GethTransactionReceiptDto

interface BlockchainClient {

    fun getBlock(blockNumber: Long): GethBlockDto?
    fun getTransactionReceipt(transactionHash: String): GethTransactionReceiptDto?
}