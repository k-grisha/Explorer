package com.gri.blockchain.explorer.repositories

import com.gri.blockchain.explorer.entity.BlockEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface BlockRepository : CrudRepository<BlockEntity, Long> {

    @Query(
        "SELECT * FROM eth_block " +
                "WHERE block_number = (SELECT max(block_number) FROM eth_block )"
    )
    fun fetchLatestBlock(): BlockEntity

}