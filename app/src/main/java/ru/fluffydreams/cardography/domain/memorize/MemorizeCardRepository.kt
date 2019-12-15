package ru.fluffydreams.cardography.domain.memorize

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.domain.cards.Card

interface MemorizeCardRepository {

    fun get(): Resource<List<Card>>

}