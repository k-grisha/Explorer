package com.gri.blockchain.explorer

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.gri.blockchain.explorer.services.geth.GethBlockchainClient
import com.gri.blockchain.explorer.services.geth.dto.GethBlockDto
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

//@SpringBootTest
class ExplorerApplicationTests {

    @Test
    fun contextLoads() {
        val json = this::class.java.getResource("/16772480.json")?.readText()
        print(json)
        val mapper = ObjectMapper()
        val result = mapper.readValue(json, object : TypeReference<GethBlockchainClient.JsonRpcResponse<GethBlockDto>>(){})
        print(result)
    }

}
