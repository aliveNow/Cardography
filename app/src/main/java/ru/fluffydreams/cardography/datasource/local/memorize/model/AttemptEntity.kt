package ru.fluffydreams.cardography.datasource.local.memorize.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.fluffydreams.cardography.datasource.local.cards.CardEntity
import java.util.*

@Entity(tableName = "attempts",
    foreignKeys = [
        ForeignKey(
            entity = CardEntity::class,
            parentColumns = ["id"],
            childColumns = ["cardId"],
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
    val cardId: Long,
    val date: Date,
    val resultId: Long
)