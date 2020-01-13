package ru.fluffydreams.cardography.ui.cards.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ru.fluffydreams.cardography.core.data.MappedListLiveData
import ru.fluffydreams.cardography.core.fragment.BaseViewModel
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.core.interactor.UseCase.None
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.cards.model.Card
import ru.fluffydreams.cardography.ui.cards.CardItem

class CardsViewModel(
    private val getCardsUseCase: UseCase<None, LiveData<List<Card>>>,
    private val deleteCardUseCase: UseCase<Card, Card>,
    private val mapper: EntityMapper<Card, CardItem>
) : BaseViewModel() {

    private val _cards = MappedListLiveData(mapper)

    val cards: LiveData<List<CardItem>>
        get() = _cards

    init {
        load()
    }

    fun load() {
        beforeUseCase()
        getCardsUseCase(viewModelScope, None) {
            _cards.source = it.data
            afterUseCase(it)
        }
    }

    fun delete(cardItem: CardItem) {
        deleteCardUseCase(viewModelScope, mapper.mapReverse(cardItem)) {
            // FIXME: показать, что удалено успешно
        }
    }

}


