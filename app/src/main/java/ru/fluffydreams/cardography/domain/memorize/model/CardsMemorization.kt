package ru.fluffydreams.cardography.domain.memorize.model

import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult.RECALLED_FABULOUS
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult.RECALLED_WELL
import java.util.*

class CardsMemorization(cardsList: List<Card>) : BaseMemorization<Card>(cardsList) {

    private val _savedAttempts: MutableMap<Long, MemorizeAttempt> = mutableMapOf()
    private val _notSavedAttempts: MutableMap<Long, MemorizeAttempt> = mutableMapOf()

    val notSavedAttempts: List<MemorizeAttempt> = _notSavedAttempts.values.map { it.copy() }

    fun attemptsWereSaved(attempts: List<MemorizeAttempt>) {
        _notSavedAttempts -= attempts.map { it.cardId }
        _savedAttempts += attempts.associateBy { it.cardId }
    }

    override fun next(): Card? {
        val card = currentFact.value
        val isAnswerShown = isAnswerVisible.value
        val result = super.next()
        if (card != null) {
            val date = Date()
            val attemptResult = if (isAnswerShown) RECALLED_WELL else RECALLED_FABULOUS
            val attempt = MemorizeAttempt(date.time, card.id, date, attemptResult)
            _notSavedAttempts[attempt.cardId] = attempt
        }
        return result
    }

}