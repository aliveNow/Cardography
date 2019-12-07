package ru.fluffydreams.cardography.datasource.local.cards

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val uid: Long,
    val title1: String?,
    val title2: String?,
    val content1: String?,
    val content2: String?
)