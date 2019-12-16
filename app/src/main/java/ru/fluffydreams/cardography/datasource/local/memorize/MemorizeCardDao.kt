package ru.fluffydreams.cardography.datasource.local.memorize

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptEntity
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptResultEntity

@Dao
interface MemorizeCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAttempts(attempts: List<AttemptEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAttemptResults(results: List<AttemptResultEntity>)

}