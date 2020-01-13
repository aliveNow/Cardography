package ru.fluffydreams.cardography.datasource.local.memorize.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "mem_facts",
    foreignKeys = [
        /* memorize задумывался как независимый от card модуль, поэтому здесь нет внешнего ключа
        к CardEntity. При удалении карточки MemFactEntity остаётся для будущей статистики.
         */
        ForeignKey(
            entity = AttemptResultEntity::class,
            parentColumns = ["id"],
            childColumns = ["lastResultId"]
        )])
data class MemFactEntity(
    @PrimaryKey
    val memId: Long,
    val factId: Long,
    val dateFirst: Date?,
    val dateLast: Date?,
    val lastResultId: Long?
)