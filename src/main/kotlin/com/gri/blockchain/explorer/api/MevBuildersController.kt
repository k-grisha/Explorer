package com.gri.blockchain.explorer.api

import com.gri.blockchain.explorer.entity.MevBuilderEntity
import com.gri.blockchain.explorer.services.MevBuilderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import kotlin.jvm.optionals.getOrNull


@RestController
class MevBuildersController(
    val mevBuilderService: MevBuilderService
) {

    @GetMapping("/api/v1/builders")
    fun getBuilders(): Set<MevBuilderEntity> {
        return mevBuilderService.getAll()
    }

    @GetMapping("/api/v1/builders/{id}")
    fun getBuilder(@PathVariable id: Int): MevBuilderEntity {
        return mevBuilderService.getById(id).getOrNull() ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Builder not found $id"
        )
    }

    @DeleteMapping("/api/v1/builders/{id}")
    fun deleteBuilder(@PathVariable id: Int) {
        mevBuilderService.deleteById(id)
    }

    @PostMapping("/api/v1/builders")
    fun createBuilder(@RequestBody builder: MevBuilderEntity): MevBuilderEntity {
        return mevBuilderService.save(builder)
    }

}