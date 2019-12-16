package ru.fluffydreams.cardography.datasource.local.memorize.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attempt_results")
data class AttemptResultEntity(
    @PrimaryKey
    val id: Long,
    val name: String
)