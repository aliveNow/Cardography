package ru.fluffydreams.cardography.data.cards

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.cards.CardRepository

class CardRepositoryImpl (
    private val localDataSource: CardLocalDataSource
) : CardRepository {

    override fun get(): Resource<List<Card>> = localDataSource.get()

    override fun add(card: Card): Resource<Card> = localDataSource.add(card)

}