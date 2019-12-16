package ru.fluffydreams.cardography.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.fluffydreams.cardography.datasource.local.cards.CardDao
import ru.fluffydreams.cardography.datasource.local.cards.CardEntity
import ru.fluffydreams.cardography.datasource.local.memorize.MemorizeCardDao
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptEntity
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptResultEntity

@Database(entities = [
    CardEntity::class,
    AttemptEntity::class,
    AttemptResultEntity::class
], version = 2)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    abstract fun memorizeCardDao(): MemorizeCardDao

}