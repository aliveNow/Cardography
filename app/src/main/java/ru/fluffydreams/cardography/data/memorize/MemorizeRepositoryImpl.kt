package ru.fluffydreams.cardography.data.memorize

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.filter.Filter
import ru.fluffydreams.cardography.domain.memorize.MemorizeRepository
import ru.fluffydreams.cardography.domain.memorize.model.MemFact

class MemorizeRepositoryImpl<F : MemFact> (
    private val memLocalDataSource: MemorizeLocalDataSource,
    private val factLocalDataSource: FactLocalDataSource<F>
) : MemorizeRepository<F> {

    override fun get(filter: Filter): Resource<List<F>> = factLocalDataSource.get(filter)

    override fun save(facts: List<MemFact>) =
        memLocalDataSource.save(facts)

}