package ru.fluffydreams.cardography.domain.memorize.memorization

import ru.fluffydreams.cardography.domain.memorize.model.MemFact
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttempt
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult.*
import java.util.*

class MemFactMemorization<F : MemFact>(list: List<F>) : BaseMemorization<MemFact, F>(list) {

    override fun next(): F {
        saveCurrentAttempt(if (isAnswerVisible.value) RECALLED_WELL else RECALLED_FABULOUS)
        return super.next()
    }

    override fun setAside(): F {
        saveCurrentAttempt(if (isAnswerVisible.value) FORGOTTEN else RECALLED_BADLY)
        return super.setAside()
    }

    override fun done() {
        saveCurrentAttempt(if (isAnswerVisible.value) RECALLED_WELL else RECALLED_FABULOUS)
        super.done()
    }

    private fun saveCurrentAttempt(attemptResult: MemorizeAttemptResult) =
        currentFact.value?.let { saveAttempt(it, attemptResult) }

    private fun saveAttempt(fact: F, attemptResult: MemorizeAttemptResult) {
        val date = Date()
        val changedFact = (changes.changed(fact.uniqueId) ?: fact).mutableCopy().apply {
            if (memId == 0L) {
                memId = date.time
                dateFirst = date
            }
            dateLast = date
            lastResult = attemptResult
            val attempt = MemorizeAttempt(
                id = date.time,
                memId = memId,
                date = date,
                result = attemptResult
            )
            attempts.add(attempt)
        }
        changes.add(fact, changedFact)
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