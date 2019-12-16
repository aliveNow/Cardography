package ru.fluffydreams.cardography.domain.memorize.model

import java.util.*

data class MemorizeAttempt(
    val id: Long,
    val cardId: Long,
    val date: Date,
    val result: MemorizeAttemptResult
)