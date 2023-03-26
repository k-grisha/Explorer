package com.gri.blockchain.explorer.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("reward")
class RewardEntity(
    @Column("eth_block")
    var blockNumber: Long,
    @Column("miner_address")
    var minerAddress: String,
    @Column("builder_name")
    var builderName: String?,
    @Column("block_reward")
    var blockReward: BigDecimal,
    @Column("mev_reward")
    var mevReward: BigDecimal?,
    @Column("mev")
    var isMev: Boolean,
    )