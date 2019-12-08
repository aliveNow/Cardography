package ru.fluffydreams.cardography.ui.cards.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.extensions.setResource
import ru.fluffydreams.cardography.domain.cards.interactor.GetCardsUseCase
import ru.fluffydreams.cardography.core.interactor.UseCase.None
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.ui.cards.CardItem

class CardsViewModel(
    private val getCardsUseCase: GetCardsUseCase,
    private val mapper: EntityMapper<Card, CardItem>
) : ViewModel() {

    val cards = MutableLiveData<Resource<List<CardItem>>>()

    fun get() {
        getCardsUseCase(viewModelScope, None) {cards.setResource(mapper.mapList(it))}
    }
}
