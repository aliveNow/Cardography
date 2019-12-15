package ru.fluffydreams.cardography.datasource.local.memorize

import androidx.room.Dao
import androidx.room.Query
import ru.fluffydreams.cardography.datasource.local.cards.CardEntity

@Dao
interface MemorizeCardDao {

    @Query("SELECT * FROM cards")
    fun getList(): List<CardEntity>

}