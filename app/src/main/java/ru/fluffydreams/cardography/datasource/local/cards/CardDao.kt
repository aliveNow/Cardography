package ru.fluffydreams.cardography.datasource.local.cards

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.fluffydreams.cardography.datasource.local.cards.model.CardEntity

@Dao
interface CardDao {

    @Query("SELECT * FROM cards")
    fun getAll(): LiveData<List<CardEntity>>

    @Insert
    fun insert(card: CardEntity): Long

}