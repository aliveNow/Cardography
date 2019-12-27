package ru.fluffydreams.cardography.datasource.local.memcard.mapper

import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.datasource.local.memcard.model.MemCardQueryResult
import ru.fluffydreams.cardography.domain.cards.model.CardSide
import ru.fluffydreams.cardography.domain.memcard.model.MemCard
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult

class LocalMemCardMapper: EntityMapper<MemCard, MemCardQueryResult>(
    { entity : MemCard -> entity.mapToLocal() },
    { entity : MemCardQueryResult -> entity.mapToDomain() }
)

fun MemCard.mapToLocal(): MemCardQueryResult =
    MemCardQueryResult(
        id = factId,
        title1 = frontSide.title,
        title2 = backSide.title,
        content1 = frontSide.content,
        content2 = backSide.content,
        memId = memId,
        factId = factId,
        dateFirst = dateFirst,
        dateLast = dateLast,
        lastResultId = lastResult?.id)

fun MemCardQueryResult.mapToDomain(): MemCard =
    MemCard(
        frontSide = CardSide(id, title1, content1),
        backSide = CardSide(id, title2, content2),
        memId = memId,
        factId = id,
        dateFirst = dateFirst,
        dateLast = dateLast,
        lastResult = lastResultId?.let{ MemorizeAttemptResult.getById(lastResultId) })