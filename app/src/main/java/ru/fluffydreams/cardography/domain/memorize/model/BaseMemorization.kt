package ru.fluffydreams.cardography.domain.memorize.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.fluffydreams.cardography.core.data.NonNullLiveData
import ru.fluffydreams.cardography.core.data.NonNullMutableLiveData
import java.util.*

open class BaseMemorization<F>(factsList: List<F>) :
    Memorization<F> {

    private val facts: Deque<F> = LinkedList(factsList)
    private val processedFacts: MutableList<F> = mutableListOf()
    private val _currentFact = MutableLiveData(facts.peekFirst())
    private val _isAnswerVisible = NonNullMutableLiveData(false)
    private val _isDone = NonNullMutableLiveData(false)

    override val currentFact: LiveData<F>
        get() = _currentFact

    override val isAnswerVisible: NonNullLiveData<Boolean>
        get() = _isAnswerVisible

    override val hasNext: Boolean
        get() = facts.size > 1

    override val isDone: NonNullLiveData<Boolean>
        get() = _isDone

    override fun showAnswer(): Boolean {
        _isAnswerVisible.value = true
        return isAnswerVisible.value
    }

    override fun next(): F? {
        checkIfDone()
        check(hasNext) { EXCEPTION_FACTS_LIST_IS_EMPTY }
        processedFacts.add(facts.first)
        removeFirstFact()
        return currentFact.value
    }

    override fun setAside(): Boolean {
        checkIfDone()
        return if (facts.size > 1) {
            facts.addLast(facts.first)
            removeFirstFact()
            true
        } else {
            false
        }
    }

    override fun done(): Boolean {
        if (facts.size <= 1) {
            _isDone.value = true
        }
        return isDone.value
    }

    private fun checkIfDone() = check(!isDone.value) { EXCEPTION_MEMORIZATION_IS_DONE }

    private fun removeFirstFact(): F? =
        if (facts.isNotEmpty()) {
            val removed = facts.removeFirst()
            _currentFact.value = facts.peekFirst()
            removed
        } else {
            null
        }

}

private const val EXCEPTION_MEMORIZATION_IS_DONE = "Memorization is done"
private const val EXCEPTION_FACTS_LIST_IS_EMPTY = "Facts list is empty"