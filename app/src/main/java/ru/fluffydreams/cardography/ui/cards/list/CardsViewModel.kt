package ru.fluffydreams.cardography.ui.cards.list

import androidx.lifecycle.*
import ru.fluffydreams.cardography.core.data.ItemsLiveData
import ru.fluffydreams.cardography.core.fragment.BaseViewModel
import ru.fluffydreams.cardography.domain.cards.interactor.GetCardsUseCase
import ru.fluffydreams.cardography.core.interactor.UseCase.None
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.ui.cards.CardItem

class CardsViewModel(
    private val getCardsUseCase: GetCardsUseCase,
    mapper: EntityMapper<Card, CardItem>
) : BaseViewModel() {

    init {
        get() //fixme
    }

    val cards: LiveData<List<CardItem>>
        get() = _cards

    private val _cards: ItemsLiveData<Card, CardItem> = ItemsLiveData(mapper)

    fun get() {
        beforeUseCase()
        getCardsUseCase(viewModelScope, None) {
            _cards.source = it.data
            afterUseCase(it)
        }
    }

}


