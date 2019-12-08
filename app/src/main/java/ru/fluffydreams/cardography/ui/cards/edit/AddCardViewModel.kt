package ru.fluffydreams.cardography.ui.cards.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.extensions.setResource
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.cards.interactor.AddCardUseCase
import ru.fluffydreams.cardography.ui.cards.CardItem
import ru.fluffydreams.cardography.ui.cards.CardSideItem

class AddCardViewModel(
    private val addCardUseCase: AddCardUseCase,
    private val mapper: EntityMapper<Card, CardItem>
) : ViewModel() {

    val card = MutableLiveData<Resource<CardItem>>(
        Resource.Success(CardItem(0, CardSideItem(0), CardSideItem(0))) //fixme
    )

    fun save() {
        card.value?.data?.let {
            //fixme нужно, чтобы тут был "GlobalScope", но что тогда делать с коллбеком?
            addCardUseCase(viewModelScope, mapper.mapReverse(it)) {
                resource ->  card.setResource(mapper.map(resource))
            }
        }
    }
}