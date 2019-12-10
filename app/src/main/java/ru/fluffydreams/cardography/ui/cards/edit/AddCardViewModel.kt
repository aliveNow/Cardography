package ru.fluffydreams.cardography.ui.cards.edit

import androidx.lifecycle.viewModelScope
import ru.fluffydreams.cardography.core.fragment.BaseViewModel
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.cards.interactor.AddCardUseCase
import ru.fluffydreams.cardography.ui.cards.CardItem
import ru.fluffydreams.cardography.ui.cards.CardSideItem

class AddCardViewModel(
    private val addCardUseCase: AddCardUseCase,
    private val mapper: EntityMapper<Card, CardItem>
) : BaseViewModel() {

    fun save(cardFrontTitle: String, cardBackTitle: String) {
        val frontSide = CardSideItem(title = cardFrontTitle)
        val backSide = CardSideItem(title = cardBackTitle)
        val card = CardItem(frontSide = frontSide, backSide = backSide)
        beforeUseCase()
        //fixme нужно, чтобы тут был "GlobalScope", но что тогда делать с коллбеком?
        addCardUseCase(viewModelScope, mapper.mapReverse(card)) {
            afterUseCase(it)
        }
    }



}