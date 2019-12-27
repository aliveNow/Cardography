package ru.fluffydreams.cardography.domain.memorize

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.filter.Filter
import ru.fluffydreams.cardography.domain.memorize.model.MemFact

interface MemorizeRepository<F : MemFact> {

    fun get(filter: Filter): Resource<List<F>>

    fun save(facts: List<MemFact>)

}