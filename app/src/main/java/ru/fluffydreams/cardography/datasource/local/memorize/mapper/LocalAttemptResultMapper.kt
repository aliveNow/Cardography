package ru.fluffydreams.cardography.datasource.local.memorize.mapper

import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.datasource.local.memorize.model.AttemptResultEntity
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult

class LocalAttemptResultMapper : EntityMapper<MemorizeAttemptResult, AttemptResultEntity>(
    { entity : MemorizeAttemptResult -> entity.mapToLocal() },
    { entity : AttemptResultEntity -> entity.mapToDomain() }
)

fun MemorizeAttemptResult.mapToLocal(): AttemptResultEntity =
    AttemptResultEntity(id, name)

fun AttemptResultEntity.mapToDomain(): MemorizeAttemptResult =
    MemorizeAttemptResult.getById(id)