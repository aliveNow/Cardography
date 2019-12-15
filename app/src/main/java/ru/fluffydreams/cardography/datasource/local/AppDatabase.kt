package ru.fluffydreams.cardography.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.fluffydreams.cardography.datasource.local.cards.CardDao
import ru.fluffydreams.cardography.datasource.local.cards.CardEntity
import ru.fluffydreams.cardography.datasource.local.memorize.MemorizeCardDao

@Database(entities = [CardEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    abstract fun memorizeCardDao(): MemorizeCardDao

}