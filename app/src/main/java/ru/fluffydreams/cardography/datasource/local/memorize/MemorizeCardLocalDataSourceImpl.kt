package ru.fluffydreams.cardography.datasource.local.memorize

import androidx.sqlite.db.SupportSQLiteQuery
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.filter.Filter
import ru.fluffydreams.cardography.core.filter.FilterInterpreter
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
    private val attemptMapper: EntityMapper<MemorizeAttempt, AttemptEntity>,
    private val filterInterpreter: FilterInterpreter<SupportSQLiteQuery>
) : MemorizeCardLocalDataSource {

    override fun get(filter: Filter): Resource<List<Card>> {
        val list = cardDao.getList(filterInterpreter("cards", filter)) //fixme constant?
        return Resource.Success(cardMapper.mapReverse(list))
    }

    override fun save(attempts: List<MemorizeAttempt>) =
        memorizeCardDao.saveAttempts(attemptMapper.map(attempts))

}