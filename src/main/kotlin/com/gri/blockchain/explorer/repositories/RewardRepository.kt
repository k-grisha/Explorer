package com.gri.blockchain.explorer.repositories

import com.gri.blockchain.explorer.entity.RewardEntity
import com.gri.blockchain.explorer.entity.TransactionEntity
import org.springframework.data.repository.CrudRepository

interface RewardRepository : CrudRepository<RewardEntity, Long> {
}