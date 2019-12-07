package ru.fluffydreams.cardography.domain.cards

import ru.fluffydreams.cardography.core.data.Resource

interface CardRepository {

    fun get(): Resource<List<Card>>

}