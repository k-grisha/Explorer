package com.gri.blockchain.explorer.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany


@Entity
class EthBlock(

    @Id var id: Long,
    var miner: String,
    var number: String,
    var timestamp: String,
//    @OneToMany
//    var transactions: List<EthTransaction>
)