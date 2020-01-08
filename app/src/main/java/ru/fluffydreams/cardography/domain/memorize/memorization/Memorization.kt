package ru.fluffydreams.cardography.domain.memorize.memorization

import androidx.lifecycle.LiveData
import ru.fluffydreams.cardography.core.data.Identifiable
import ru.fluffydreams.cardography.core.data.NonNullLiveData
import ru.fluffydreams.cardography.core.data.TrackingChangesCollection

interface Memorization<C : Identifiable, F : C> {
    
    val currentFact: LiveData<F>
    val isAnswerVisible: NonNullLiveData<Boolean>
    val hasNext: Boolean
    val isDone: NonNullLiveData<Boolean>
    val changes: TrackingChangesCollection<C>

    fun showAnswer()
    fun next(): F
    fun setAside(): F
    fun done()

}