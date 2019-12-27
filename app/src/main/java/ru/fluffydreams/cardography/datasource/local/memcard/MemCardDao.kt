package ru.fluffydreams.cardography.datasource.local.memcard

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import ru.fluffydreams.cardography.datasource.local.memcard.model.MemCardQueryResult

@Dao
interface MemCardDao {

    @RawQuery
    fun getList(query: SupportSQLiteQuery): List<MemCardQueryResult>

    @Query("SELECT cards.*, mem_facts.* FROM cards LEFT JOIN mem_facts ON cards.id == mem_facts.factId")
    fun getAll(): List<MemCardQueryResult>

}