package ru.fluffydreams.cardography.domain.memorize.memorization

import ru.fluffydreams.cardography.domain.memorize.model.MemFact
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttempt
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult
import java.util.*

class MemFactMemorization<F : MemFact>(list: List<F>) : BaseMemorization<MemFact, F>(list) {

    override fun next(): F? {
        val fact = currentFact.value
        val isAnswerShown = isAnswerVisible.value
        val result = super.next()
        if (fact != null) {
            val date = Date()
            val attemptResult = if (isAnswerShown) {
                MemorizeAttemptResult.RECALLED_WELL
            } else {
                MemorizeAttemptResult.RECALLED_FABULOUS
            }
            val changedFact = (changes.changed(fact.uniqueId) ?: fact).mutableCopy().apply {
                if (memId == 0L) {
                    memId = date.time
                    dateFirst = date
                }
                dateLast = date
                lastResult = attemptResult
                val attempt = MemorizeAttempt(
                    date.time,
                    memId,
                    date,
                    attemptResult
                )
                attempts.add(attempt)
            }
            changes.add(fact, changedFact)
        }
        return result
    }

}

private interface MutableMemFact : MemFact {
    override var memId: Long
    override var dateFirst: Date?
    override var dateLast: Date?
    override var lastResult: MemorizeAttemptResult?
    override val attempts: MutableList<MemorizeAttempt>
}

private data class MutableMemFactImpl(
    override val factId: Long,
    override var memId: Long = 0,
    override var dateFirst: Date? = null,
    override var dateLast: Date? = null,
    override var lastResult: MemorizeAttemptResult? = null,
    override val attempts: MutableList<MemorizeAttempt>,
    override val uniqueId: String = memId.toString()
) : MutableMemFact

private fun MemFact.mutableCopy(): MutableMemFact =
    MutableMemFactImpl(
        factId = factId,
        memId = memId,
        dateFirst = dateFirst,
        dateLast = dateLast,
        lastResult = lastResult,
        attempts = attempts?.toMutableList() ?: mutableListOf()
    )