package com.gri.blockchain.explorer.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("reward")
class RewardEntity(

    @Id
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
    @Column("mev_recipient")
    var mevRecipient: String?,
    @Column("mev")
    var isMev: Boolean,
    )