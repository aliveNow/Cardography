package ru.fluffydreams.cardography.datasource.local.cards

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.fluffydreams.cardography.core.sql.insertOrUpdateObject
import ru.fluffydreams.cardography.datasource.local.cards.model.CardEntity

@Dao
interface CardDao {

    @Query("SELECT * FROM cards")
    fun getAll(): LiveData<List<CardEntity>>

    @Delete
    fun delete(card: CardEntity)

    @Transaction
    fun save(card: CardEntity): Long = insertOrUpdateObject(card, ::insert, ::update)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(card: CardEntity): Long

    @Update
    fun update(card: CardEntity)

}