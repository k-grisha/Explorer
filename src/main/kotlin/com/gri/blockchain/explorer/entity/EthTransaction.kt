package com.gri.blockchain.explorer.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class EthTransaction(
    @Id var hash: String,
    var blockNumber: String,
    var from: String,
    var to: String,
    var value: String
)