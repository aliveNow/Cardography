package ru.fluffydreams.cardography.data.memorize

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.filter.Filter
import ru.fluffydreams.cardography.domain.memorize.model.MemFact

interface MemorizeLocalDataSource {

    fun save(facts: List<MemFact>)

}

interface FactLocalDataSource<F : MemFact> {

    fun get(filter: Filter): Resource<List<F>>

}