package ru.fluffydreams.cardography.data.cards

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.domain.cards.Card

interface CardLocalDataSource {

    fun get(): Resource<List<Card>>

    fun add(card: Card): Resource<Card>

}