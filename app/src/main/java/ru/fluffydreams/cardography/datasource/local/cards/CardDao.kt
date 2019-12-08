package ru.fluffydreams.cardography.datasource.local.cards

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardDao {

    @Query("SELECT * FROM cards")
    fun getAll(): List<CardEntity>

    @Insert
    fun insert(card: CardEntity): Long

}