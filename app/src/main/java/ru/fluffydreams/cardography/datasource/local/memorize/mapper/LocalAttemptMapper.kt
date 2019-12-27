package ru.fluffydreams.cardography.datasource.local.memorize.mapper

import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptEntity
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttempt
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult

class LocalAttemptMapper : EntityMapper<MemorizeAttempt, AttemptEntity>(
    { entity : MemorizeAttempt -> entity.mapToLocal() },
    { entity : AttemptEntity -> entity.mapToDomain() }
)

fun MemorizeAttempt.mapToLocal(): AttemptEntity =
    AttemptEntity(if (id != 0L) id else date.time, memId, date, result.id)

fun AttemptEntity.mapToDomain(): MemorizeAttempt =
    MemorizeAttempt(id, memId, date, MemorizeAttemptResult.getById(resultId))