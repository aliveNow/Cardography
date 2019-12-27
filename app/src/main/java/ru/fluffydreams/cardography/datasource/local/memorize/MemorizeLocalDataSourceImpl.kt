package ru.fluffydreams.cardography.datasource.local.memorize

import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.data.memorize.MemorizeLocalDataSource
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptEntity
import ru.fluffydreams.cardography.datasource.local.memorize.model.MemFactEntity
import ru.fluffydreams.cardography.domain.memorize.model.MemFact
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttempt

class MemorizeLocalDataSourceImpl(
    private val memorizeDao: MemorizeDao,
    private val memFactMapper: EntityMapper<MemFact, MemFactEntity>,
    private val attemptMapper: EntityMapper<MemorizeAttempt, AttemptEntity>
) : MemorizeLocalDataSource {

    override fun save(facts: List<MemFact>) {
        facts.forEach {
            memorizeDao.save(memFactMapper.map(it),
                it.attempts?.let { attempts -> attemptMapper.map(attempts) } )
        }
    }

}