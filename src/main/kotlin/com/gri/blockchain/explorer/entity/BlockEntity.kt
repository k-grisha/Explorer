package com.gri.blockchain.explorer.entity


import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
//import java.sql.Types.


@Table("eth_block")

class BlockEntity(

    @Id
    @Column("block_number")
    var number: Long,
    @Column("miner")
    var miner: String,
    @Column("timestamp")
    var timestamp: Long,
    @Column("base_fee_per_gas")
    var baseFeePerGas: BigDecimal,

    @MappedCollection(idColumn = "block_number")
    var transactions: Set<TransactionEntity>,

    @Version
    var version: Int? = null
)