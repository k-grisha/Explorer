package com.gri.blockchain.explorer.repositories

import com.gri.blockchain.explorer.entity.EthBlock
import org.springframework.data.repository.CrudRepository

interface BlockRepository: CrudRepository<EthBlock, String> {
}