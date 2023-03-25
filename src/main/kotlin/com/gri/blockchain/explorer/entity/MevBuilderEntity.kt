package com.gri.blockchain.explorer.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("mev_builders")
class MevBuilderEntity(

    @Id
    @Column("id")
    var id: Int? = null,
    @Column("address")
    var address: String,
    @Column("name")
    var name: String
)