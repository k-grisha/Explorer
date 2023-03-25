package com.gri.blockchain.explorer.repositories

import com.gri.blockchain.explorer.entity.MevBuilderEntity
import org.springframework.data.repository.CrudRepository

interface MevBuilderRepository : CrudRepository<MevBuilderEntity, Int> {
}