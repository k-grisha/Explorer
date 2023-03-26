package com.gri.blockchain.explorer.entity


import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal


@Table("eth_block")
class BlockEntity(

    @Id
    @Column("block_number")
    var number: Long,
    @Column("miner")
    var miner: String,
    @Column("gas_used")
    var gasUsed: Long,
    @Column("timestamp")
    var timestamp: Long,
    @Column("base_fee_per_gas")
    var baseFeePerGas: BigDecimal,

    @MappedCollection(idColumn = "block_number")
    var transactions: Set<TransactionEntity>,

    var rewardEntity: RewardEntity?,

    @Version
    var version: Int? = null
)