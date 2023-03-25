package com.gri.blockchain.explorer.services.geth.dto

data class GethTransactionReceiptDto(
    val blockHash: String,
    val blockNumber: String,
    val contractAddress: String?,
    val cumulativeGasUsed: String,
    val effectiveGasPrice: String,
    val from: String,
    val gasUsed: String,
    val status: String,
    val to: String?,
    val transactionHash: String,
    val transactionIndex: String,
    val type: String
)