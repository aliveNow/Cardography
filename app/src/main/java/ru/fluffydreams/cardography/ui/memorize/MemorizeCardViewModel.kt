package ru.fluffydreams.cardography.ui.memorize

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ru.fluffydreams.cardography.core.data.*
import ru.fluffydreams.cardography.core.filter.Filter
import ru.fluffydreams.cardography.core.fragment.BaseViewModel
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.memcard.model.MemCard
import ru.fluffydreams.cardography.domain.memorize.memorization.BaseMemorization
import ru.fluffydreams.cardography.domain.memorize.memorization.MemFactMemorization
import ru.fluffydreams.cardography.domain.memorize.memorization.Memorization
import ru.fluffydreams.cardography.ui.cards.CardItem
import ru.fluffydreams.cardography.ui.memorize.MemorizationState.*

typealias MemCardMemorization = MemFactMemorization<MemCard>
typealias CardItemMemorization = Memorization<Identifiable, CardItem>

class MemorizeCardViewModel(
    private val getMemorizationUseCase: UseCase<MemCardMemorization, Filter>,
    private val saveMemorizationUseCase: UseCase<Boolean, MemCardMemorization>,
    private val mapper: EntityMapper<MemCard, CardItem>
) : BaseViewModel() {

    private val _state = NonNullMutableLiveData(LOADING)
    private lateinit var _memorization: CardItemMemorizationImpl

    val state: NonNullLiveData<MemorizationState>
        get() = _state

    val memorization: CardItemMemorization
        get() = if (_state.value >= STARTED) _memorization else EmptyMemorization

    init {
        get()
    }

    private fun get() {
        beforeUseCase()
        _state.value = LOADING
        getMemorizationUseCase(viewModelScope, Filter.None) {
            it.data?.let { list ->
                _memorization = CardItemMemorizationImpl(list, mapper)
                _state.value = STARTED
            }
            afterUseCase(it)
        }
    }

    private fun save(params: MemCardMemorization) {
        saveMemorizationUseCase(viewModelScope, params) {
            //fixme show errors
        }
    }

    private inner class CardItemMemorizationImpl(
        val base: MemCardMemorization,
        mapper: EntityMapper<MemCard, CardItem>
    ) : CardItemMemorization {

        private val transform = mapper.transform

        private val _currentFact: MappedLiveData<MemCard, CardItem>
                = MappedLiveData(transform)

        init {
            _currentFact.source = base.currentFact
        }

        override val currentFact: LiveData<CardItem>
            get() = _currentFact

        override val isAnswerVisible: NonNullLiveData<Boolean>
            get() = base.isAnswerVisible

        override val hasNext: Boolean
            get() = base.hasNext

        override val isDone: NonNullLiveData<Boolean>
            get() = base.isDone

        override val changes: TrackingChangesCollection<Identifiable> = TrackingChangesCollection()

        override fun showAnswer(): Boolean = base.showAnswer()

        override fun next(): CardItem? =
            base.next()?.let {
                save(base)
                transform(it)
            }

        override fun setAside(): Boolean = base.setAside()

        override fun done(): Boolean =
            if (base.done()) {
                save(base)
                _state.value = DONE
                true
            } else {
                false
            }
    }

    object EmptyMemorization : BaseMemorization<Identifiable, CardItem>(emptyList())

}

enum class MemorizationState { LOADING, STARTED, DONE }