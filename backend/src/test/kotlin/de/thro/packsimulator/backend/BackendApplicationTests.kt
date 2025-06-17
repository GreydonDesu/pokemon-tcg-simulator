package de.thro.packsimulator.backend

import de.thro.packsimulator.backend.repository.AccountRepository
import de.thro.packsimulator.backend.repository.SetRepository
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import kotlin.test.Test
import kotlin.test.assertNotNull

@SpringBootTest
@EnableAutoConfiguration(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
class BackendApplicationTests {

    @MockitoBean
    private lateinit var accountRepository: AccountRepository

    @MockitoBean
    private lateinit var setRepository: SetRepository

    @Test
    fun contextLoads() {
        assertNotNull(accountRepository)
        assertNotNull(setRepository)
    }
}
