package ru.fluffydreams.cardography.ui.memorize

import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.memcard.model.MemCard
import ru.fluffydreams.cardography.ui.cards.CardItem
import ru.fluffydreams.cardography.ui.cards.mapToUI

class UIMemCardMapper : EntityMapper<MemCard, CardItem>(
    { entity : MemCard -> entity.mapToUI() },
    { throw UnsupportedOperationException("Reverse transform not supported")}
)

fun MemCard.mapToUI() : CardItem =
    CardItem(
        id = memId,
        frontSide = frontSide.mapToUI(),
        backSide = backSide.mapToUI(),
        memId = memId,
        factId = factId,
        dateFirst = dateFirst,
        dateLast = dateLast,
        lastResult = lastResult
    )