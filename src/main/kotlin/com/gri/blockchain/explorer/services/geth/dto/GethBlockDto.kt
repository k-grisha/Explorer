package com.gri.blockchain.explorer.services.geth.dto

data class GethBlockDto(
    val hash: String,
    val miner: String,
    val number: String,
    val parentHash: String,
    val receiptsRoot: String,
    val sha3Uncles: String,
    val size: String,
    val stateRoot: String,
    val timestamp: String,
    val transactions: List<GethTransactionDto>

)