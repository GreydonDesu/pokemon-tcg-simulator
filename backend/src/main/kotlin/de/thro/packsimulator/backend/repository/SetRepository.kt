package de.thro.packsimulator.backend.repository

import de.thro.packsimulator.backend.data.Set
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SetRepository : MongoRepository<Set, String>
