package ru.fluffydreams.cardography.domain.memorize.model

import androidx.lifecycle.LiveData
import ru.fluffydreams.cardography.core.data.NonNullLiveData

interface Memorization<F> {
    
    val currentFact: LiveData<F>
    val isAnswerVisible: NonNullLiveData<Boolean>
    val hasNext: Boolean
    val isDone: NonNullLiveData<Boolean>

    fun showAnswer(): Boolean
    fun next(): F?
    fun setAside(): Boolean
    fun done(): Boolean

}