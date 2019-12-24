package ru.fluffydreams.cardography.ui.memorize

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ru.fluffydreams.cardography.core.data.MappedLiveData
import ru.fluffydreams.cardography.core.data.NonNullLiveData
import ru.fluffydreams.cardography.core.data.NonNullMutableLiveData
import ru.fluffydreams.cardography.core.filter.Filter
import ru.fluffydreams.cardography.core.fragment.BaseViewModel
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.memorize.model.BaseMemorization
import ru.fluffydreams.cardography.domain.memorize.model.Memorization
import ru.fluffydreams.cardography.ui.cards.CardItem
import ru.fluffydreams.cardography.ui.memorize.MemorizationState.*

class MemorizeCardViewModel(
    private val getMemorizationUseCase: UseCase<Memorization<Card>, Filter>,
    private val saveMemorizationUseCase: UseCase<Boolean, Memorization<Card>>,
    private val mapper: EntityMapper<Card, CardItem>
) : BaseViewModel() {

    private val _state = NonNullMutableLiveData(LOADING)
    private lateinit var _memorization: CardItemMemorization

    val state: NonNullLiveData<MemorizationState>
        get() = _state

    val memorization: Memorization<CardItem>
        get() = if (_state.value >= STARTED) _memorization else EmptyMemorization

    init {
        get()
    }

    private fun get() {
        beforeUseCase()
        _state.value = LOADING
        getMemorizationUseCase(viewModelScope, Filter.None) {
            it.data?.let { list ->
                _memorization = CardItemMemorization(list, mapper)
                _state.value = STARTED
            }
            afterUseCase(it)
        }
    }

    private fun save(params: Memorization<Card>) {
        saveMemorizationUseCase(viewModelScope, params) {
            //fixme show errors
        }
    }

    private inner class CardItemMemorization(
        val base: Memorization<Card>,
        mapper: EntityMapper<Card, CardItem>
    ) : Memorization<CardItem> {

        private val transform = mapper.transform

        private val _currentFact: MappedLiveData<Card, CardItem>
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

    object EmptyMemorization : BaseMemorization<CardItem>(emptyList())

}

enum class MemorizationState { LOADING, STARTED, DONE }