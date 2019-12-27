package ru.fluffydreams.cardography.domain.memorize.model

import ru.fluffydreams.cardography.core.data.Identifiable
import ru.fluffydreams.cardography.domain.memorize.memorization.Memorization
import java.util.*

typealias FactMemorization<F> = Memorization<MemFact, F>

interface MemFact : Identifiable {
    val memId: Long
    val factId: Long
    val dateFirst: Date?
    val dateLast: Date?
    val lastResult: MemorizeAttemptResult?
    val attempts: List<MemorizeAttempt>?
}