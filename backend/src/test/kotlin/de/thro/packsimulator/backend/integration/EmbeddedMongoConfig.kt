import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

@TestConfiguration
class EmbeddedMongoConfig {

    @Bean
    fun mongoTemplate(): MongoTemplate {
        val connectionString = "mongodb://localhost:27017/testdb"
        return MongoTemplate(SimpleMongoClientDatabaseFactory(connectionString))
    }
}
