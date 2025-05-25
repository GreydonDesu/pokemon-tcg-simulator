package de.thro.packsimulator.backend.repository

import de.thro.packsimulator.backend.data.Account
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : MongoRepository<Account, String>
