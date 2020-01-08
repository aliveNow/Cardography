package ru.fluffydreams.cardography.domain.memorize.memorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.fluffydreams.cardography.core.data.Identifiable
import ru.fluffydreams.cardography.core.data.NonNullLiveData
import ru.fluffydreams.cardography.core.data.NonNullMutableLiveData
import ru.fluffydreams.cardography.core.data.TrackingChangesCollection
import java.util.*

open class BaseMemorization<C : Identifiable, F : C>(
    factsList: List<F>,
    override val changes: TrackingChangesCollection<C> = TrackingChangesCollection()
) : Memorization<C, F> {

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

    override fun showAnswer() {
        _isAnswerVisible.value = true
    }

    override fun next(): F =
        moveToNextFact { processedFacts.add(facts.first) }

    override fun setAside(): F =
        moveToNextFact { facts.addLast(facts.first) }


    override fun done() {
        if (facts.size <= 1) {
            _isDone.value = true
        }
    }

    private inline fun moveToNextFact(beforeNext: () -> Unit): F {
        checkIfDone()
        beforeNext()
        hideAnswer()
        facts.removeFirst()
        _currentFact.value = facts.peekFirst()
        return currentFact.value!!
    }

    private fun checkIfDone() = check(!isDone.value) { EXCEPTION_MEMORIZATION_IS_DONE }

    private fun hideAnswer() {
        _isAnswerVisible.value = false
    }

}

private const val EXCEPTION_MEMORIZATION_IS_DONE = "Memorization is done"