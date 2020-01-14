package ru.fluffydreams.cardography.datasource.local.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.core.sql.FAILED_INSERT_ID
import ru.fluffydreams.cardography.data.cards.CardLocalDataSource
import ru.fluffydreams.cardography.datasource.local.cards.model.CardEntity
import ru.fluffydreams.cardography.domain.cards.model.Card
import ru.fluffydreams.cardography.domain.cards.model.copyWithId

class CardLocalDataSourceImpl(
    private val cardDao: CardDao,
    private val mapper: EntityMapper<Card, CardEntity>
) : CardLocalDataSource {

    override fun get(): Resource<LiveData<List<Card>>> =
        Resource.Success(Transformations.map(cardDao.getAll(), mapper::mapReverse))

    override fun save(card: Card): Resource<Card> {
        val cardId = cardDao.save(mapper.map(card))
        val result = if (cardId != FAILED_INSERT_ID) card.copyWithId(cardId) else card
        return Resource.Success(result)
    }

    override fun delete(card: Card): Resource<Card> {
        cardDao.delete(mapper.map(card))
        return Resource.Success(card)
    }
}

