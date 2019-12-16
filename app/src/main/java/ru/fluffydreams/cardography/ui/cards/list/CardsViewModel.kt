package ru.fluffydreams.cardography.ui.cards.list

import androidx.lifecycle.*
import ru.fluffydreams.cardography.core.data.MappedListLiveData
import ru.fluffydreams.cardography.core.fragment.BaseViewModel
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.core.interactor.UseCase.None
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.ui.cards.CardItem

class CardsViewModel(
    private val getCardsUseCase: UseCase<LiveData<List<Card>>, None>,
    mapper: EntityMapper<Card, CardItem>
) : BaseViewModel() {

    private val _cards = MappedListLiveData(mapper)

    val cards: LiveData<List<CardItem>>
        get() = _cards

    init {
        get() //fixme
    }

    fun get() {
        beforeUseCase()
        getCardsUseCase(viewModelScope, None) {
            _cards.source = it.data
            afterUseCase(it)
        }
    }

}


