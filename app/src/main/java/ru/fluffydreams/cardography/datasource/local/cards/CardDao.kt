package ru.fluffydreams.cardography.datasource.local.cards

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface CardDao {

    @Query("SELECT * FROM cards")
    fun getAll(): LiveData<List<CardEntity>>

    @Query("SELECT * FROM cards")
    fun getList(): List<CardEntity>

    @RawQuery
    fun getList(query: SupportSQLiteQuery): List<CardEntity>

    @Query("SELECT * FROM cards WHERE id = :cardId")
    fun loadCard(cardId: Long): CardEntity

    @Insert
    fun insert(card: CardEntity): Long

}