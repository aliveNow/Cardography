package ru.fluffydreams.cardography.datasource.local.memorize.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "attempts",
    foreignKeys = [
        ForeignKey(
            entity = MemFactEntity::class,
            parentColumns = ["memId"],
            childColumns = ["memId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AttemptResultEntity::class,
            parentColumns = ["id"],
            childColumns = ["resultId"]
        )])
data class AttemptEntity(
    @PrimaryKey
    val id: Long,
    val memId: Long,
    val date: Date,
    val resultId: Long
)