package com.gri.blockchain.explorer.repositories

import com.gri.blockchain.explorer.entity.BlockEntity
import org.springframework.data.repository.CrudRepository

interface BlockRepository : CrudRepository<BlockEntity, Long> {
}