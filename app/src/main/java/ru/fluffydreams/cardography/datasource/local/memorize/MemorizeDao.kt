package ru.fluffydreams.cardography.datasource.local.memorize

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFact(fact: MemFactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAttempts(attempts: List<AttemptEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAttemptResults(results: List<AttemptResultEntity>)

}