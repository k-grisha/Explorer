package com.gri.blockchain.explorer.services.geth.dto

data class GethTransactionDto(
    val blockHash: String,
    val blockNumber: String,
    val from: String,
    val gas: String,
    val gasPrice: String,
    val maxFeePerGas: String?,
    val maxPriorityFeePerGas: String?,
    val hash: String,
    val to: String,
    val transactionIndex: String?,
    val value: String,
    val type: String,
    val chainId: String?
)