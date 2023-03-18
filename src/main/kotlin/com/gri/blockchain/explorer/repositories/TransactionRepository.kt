package com.gri.blockchain.explorer.repositories

import com.gri.blockchain.explorer.entity.TransactionEntity
import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<TransactionEntity, String> {
}