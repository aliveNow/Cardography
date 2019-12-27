package ru.fluffydreams.cardography.domain.memcard.model

import ru.fluffydreams.cardography.domain.cards.model.CardSide
import ru.fluffydreams.cardography.domain.memorize.model.MemFact
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttempt
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult
import java.util.*

data class MemCard(
    val frontSide: CardSide,
    val backSide: CardSide,
    override val memId: Long,
    override val factId: Long,
    override val dateFirst: Date? = null,
    override val dateLast: Date? = null,
    override val lastResult: MemorizeAttemptResult? = null,
    override val attempts: List<MemorizeAttempt>? = null,
    override val uniqueId: String = memId.toString()
) : MemFact