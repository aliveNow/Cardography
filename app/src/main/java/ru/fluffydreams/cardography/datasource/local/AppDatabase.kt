package ru.fluffydreams.cardography.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.fluffydreams.cardography.datasource.local.cards.CardDao
import ru.fluffydreams.cardography.datasource.local.cards.model.CardEntity
import ru.fluffydreams.cardography.datasource.local.memcard.MemCardDao
import ru.fluffydreams.cardography.datasource.local.memorize.MemorizeDao
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptEntity
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptResultEntity
import ru.fluffydreams.cardography.datasource.local.memorize.model.MemFactEntity

@Database(entities = [
    CardEntity::class,
    MemFactEntity::class,
    AttemptEntity::class,
    AttemptResultEntity::class
], version = 3)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    abstract fun memorizeDao(): MemorizeDao

    abstract fun memCardDao(): MemCardDao

}