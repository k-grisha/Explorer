package com.gri.blockchain.explorer.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal


@Table("block")
class BlockEntity(

    @Id
    @Column("number")
    var number: Long,
    @Column("miner")
    var miner: String,
    @Column("timestamp")
    var timestamp: Long,
    @Column("baseFeePerGas")
    var baseFeePerGas: BigDecimal,

    var transactions: List<TransactionEntity>
)