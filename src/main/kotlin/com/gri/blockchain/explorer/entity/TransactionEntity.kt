package com.gri.blockchain.explorer.entity


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("traansaction")
class TransactionEntity(
    @Id
    @Column("number")
    var hash: String,
    @Column("number")
    var blockNumber: Long,
    @Column("from")
    var from: String,
    @Column("to")
    var to: String,
    @Column("value")
    var value: BigDecimal,
    @Column("gas")
    var gas: Long,
    @Column("gasPrice")
    val gasPrice: BigDecimal,
    @Column("maxFeePerGas")
    val maxFeePerGas: BigDecimal?,
    @Column("maxFeePerGas")
    val maxPriorityFeePerGas: BigDecimal?,
    @Column("type")
    val type: Int
)