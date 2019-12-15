package ru.fluffydreams.cardography.data.memorize

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.domain.cards.Card

interface MemorizeCardLocalDataSource {

    fun get(): Resource<List<Card>>

}