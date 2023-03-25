package com.gri.blockchain.explorer.services

import com.gri.blockchain.explorer.entity.MevBuilderEntity
import com.gri.blockchain.explorer.repositories.MevBuilderRepository
import org.springframework.stereotype.Service

@Service
class MevBuilderService(
    val mevBuilderRepository: MevBuilderRepository
) {


    fun getById(id: Int) = mevBuilderRepository.findById(id)

    fun deleteById(id: Int) = mevBuilderRepository.deleteById(id)

    fun getAll() = mevBuilderRepository.findAll().toSet()

    fun save(entity: MevBuilderEntity) = mevBuilderRepository.save(entity)

}