package ru.fluffydreams.cardography.datasource.local.cards

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.data.cards.CardLocalDataSource
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.cards.copyWithId

class CardLocalDataSourceImpl(
    private val cardDao: CardDao,
    private val mapper: EntityMapper<Card, CardEntity>
) : CardLocalDataSource {

    // FIXME: what if it's empty?....
    override fun get(): Resource<List<Card>> =
        Resource.Success(mapper.mapReverse(cardDao.getAll()))

    override fun add(card: Card): Resource<Card> {
        val cardId = cardDao.insert(mapper.map(card))
        return Resource.Success(card.copyWithId(cardId))
    }
}

