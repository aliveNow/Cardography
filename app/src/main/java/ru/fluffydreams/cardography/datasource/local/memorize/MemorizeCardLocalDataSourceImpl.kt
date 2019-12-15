package ru.fluffydreams.cardography.datasource.local.memorize

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.data.memorize.MemorizeCardLocalDataSource
import ru.fluffydreams.cardography.datasource.local.cards.CardEntity
import ru.fluffydreams.cardography.domain.cards.Card

class MemorizeCardLocalDataSourceImpl(
    private val memorizeCardDao: MemorizeCardDao,
    private val mapper: EntityMapper<Card, CardEntity>
) : MemorizeCardLocalDataSource {

    override fun get(): Resource<List<Card>> =
        Resource.Success(mapper.mapReverse(memorizeCardDao.getList()))

}