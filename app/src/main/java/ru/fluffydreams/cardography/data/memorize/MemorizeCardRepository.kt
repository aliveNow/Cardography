package ru.fluffydreams.cardography.data.memorize

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.memorize.MemorizeCardRepository

class MemorizeCardRepositoryImpl (
    private val localDataSource: MemorizeCardLocalDataSource
) : MemorizeCardRepository {

    override fun get(): Resource<List<Card>> = localDataSource.get()

}