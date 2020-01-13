package ru.fluffydreams.cardography.datasource.local.memorize

import androidx.room.*
import ru.fluffydreams.cardography.core.sql.insertOrUpdateList
import ru.fluffydreams.cardography.core.sql.insertOrUpdateObject
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptEntity
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptResultEntity
import ru.fluffydreams.cardography.datasource.local.memorize.model.MemFactEntity

@Dao
interface MemorizeDao {

    @Transaction
    fun save(fact: MemFactEntity, attempts: List<AttemptEntity>?) {
        saveFact(fact)
        attempts?.let { saveAttempts(it) }
    }

    @Transaction
    fun saveFact(fact: MemFactEntity) {
        insertOrUpdateObject(fact, ::insertFact, ::updateFact)
    }

    @Transaction
    fun saveAttempts(attempts: List<AttemptEntity>) {
        insertOrUpdateList(attempts, ::insertAttempts, ::updateAttempts)
    }

    @Transaction
    fun saveAttemptResults(results: List<AttemptResultEntity>) {
        insertOrUpdateList(results, ::insertAttemptResults, ::updateAttemptResults)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFact(fact: MemFactEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAttempts(attempts: List<AttemptEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAttemptResults(results: List<AttemptResultEntity>): List<Long>

    @Update
    fun updateFact(fact: MemFactEntity)

    @Update
    fun updateAttempts(attempts: List<AttemptEntity>)

    @Update
    fun updateAttemptResults(results: List<AttemptResultEntity>)

}