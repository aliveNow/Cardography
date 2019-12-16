package ru.fluffydreams.cardography.datasource.local.memorize

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.data.memorize.MemorizeCardLocalDataSource
import ru.fluffydreams.cardography.datasource.local.cards.CardDao
import ru.fluffydreams.cardography.datasource.local.cards.CardEntity
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptEntity
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttempt

class MemorizeCardLocalDataSourceImpl(
    private val cardDao: CardDao,
    private val memorizeCardDao: MemorizeCardDao,
    private val cardMapper: EntityMapper<Card, CardEntity>,
    private val attemptMapper: EntityMapper<MemorizeAttempt, AttemptEntity>
) : MemorizeCardLocalDataSource {

    override fun get(): Resource<List<Card>> =
        Resource.Success(cardMapper.mapReverse(cardDao.getList()))

    override fun save(attempts: List<MemorizeAttempt>) =
        memorizeCardDao.saveAttempts(attemptMapper.map(attempts))

}