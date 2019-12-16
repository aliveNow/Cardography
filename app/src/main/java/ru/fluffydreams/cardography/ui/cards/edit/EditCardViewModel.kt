package ru.fluffydreams.cardography.ui.cards.edit

import androidx.lifecycle.viewModelScope
import ru.fluffydreams.cardography.core.fragment.BaseViewModel
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.ui.cards.CardItem
import ru.fluffydreams.cardography.ui.cards.CardSideItem

class EditCardViewModel(
    private val editCardUseCase: UseCase<Card, Card>,
    private val mapper: EntityMapper<Card, CardItem>
) : BaseViewModel() {

    fun save(cardFrontTitle: String, cardBackTitle: String) {
        val frontSide = CardSideItem(title = cardFrontTitle)
        val backSide = CardSideItem(title = cardBackTitle)
        val card = CardItem(frontSide = frontSide, backSide = backSide)
        beforeUseCase()
        //fixme нужно, чтобы тут был "GlobalScope", но что тогда делать с коллбеком?
        editCardUseCase(viewModelScope, mapper.mapReverse(card)) {
            afterUseCase(it)
        }
    }

}