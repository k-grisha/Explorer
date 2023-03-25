package com.gri.blockchain.explorer.entity


import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("eth_transaction")
class TransactionEntity(
    @Id
    @Column("transaction_hash")
    var hash: String,
    @Column("block_number")
    var blockNumber: Long,
    @Column("from_adr")
    var from: String,
    @Column("to_adr")
    var to: String?,
    @Column("value")
    var value: BigDecimal,
    @Column("gas")
    var gas: Long,
    @Column("gas_price")
    var gasPrice: BigDecimal,
    @Column("max_fee_per_gas")
    var maxFeePerGas: BigDecimal?,
    @Column("max_priority_fee_per_gas")
    var maxPriorityFeePerGas: BigDecimal?,
    @Column("type")
    var type: Int,

    @Column("cumulative_gas_used")
    var cumulativeGasUsed: Long,
    @Column("gas_used")
    var gasUsed: Long,
    @Column("effective_gas_price")
    var effectiveGasPrice: BigDecimal,
    @Column("status")
    var status: Int,
    @Column("transaction_index")
    var transactionIndex: Int,


    @Version
    var version: Int? = null

)