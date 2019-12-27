package ru.fluffydreams.cardography.datasource.local.memorize.mapper

import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.datasource.local.memorize.model.MemFactEntity
import ru.fluffydreams.cardography.domain.memorize.model.MemFact

class LocalMemFactMapper(
    reverseTransform: (MemFactEntity) -> MemFact =
        { throw UnsupportedOperationException("Reverse transform not supported")}
) : EntityMapper<MemFact, MemFactEntity>(
    { entity : MemFact -> entity.mapToLocal() },
    reverseTransform
)

fun MemFact.mapToLocal(): MemFactEntity =
    MemFactEntity(memId, factId, dateFirst, dateLast, lastResult?.id)