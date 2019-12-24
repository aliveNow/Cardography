package ru.fluffydreams.cardography.data.memorize

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.filter.Filter
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttempt

interface MemorizeCardLocalDataSource {

    fun get(filter: Filter): Resource<List<Card>>

    fun save(attempts: List<MemorizeAttempt>)

}