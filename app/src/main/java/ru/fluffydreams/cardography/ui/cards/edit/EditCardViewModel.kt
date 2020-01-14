package ru.fluffydreams.cardography.ui.cards.edit

import androidx.lifecycle.viewModelScope
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.core.fragment.BaseViewModel
import ru.fluffydreams.cardography.core.data.ValidatedField
import ru.fluffydreams.cardography.core.data.ValidationResult
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.cards.model.Card
import ru.fluffydreams.cardography.ui.cards.CardItem
import ru.fluffydreams.cardography.ui.cards.CardSideItem
import ru.fluffydreams.cardography.ui.cards.NEW_CARD_ID

class EditCardViewModel(
    private val saveCardUseCase: UseCase<Card, Card>,
    private val mapper: EntityMapper<Card, CardItem>
) : BaseViewModel() {

    var cardItem: CardItem? = null
        set(value) {
            if (field == null) value?.let {
                field = it
                cardFrontTitle.value = it.frontSide.title
                cardBackTitle.value = it.backSide.title
            }
        }

    val cardFrontTitle = ValidatedField(::validate)
    val cardBackTitle = ValidatedField(::validate)

    fun save() {
        if (validate()) {
            val card = prepareCardForSave()
            beforeUseCase()
            saveCardUseCase(viewModelScope, card) {
                afterUseCase(it)
            }
        }
    }

    private fun validate(): Boolean =
        cardFrontTitle.validate().success and cardBackTitle.validate().success

    private fun validate(value: String?): ValidationResult =
        if (value.isNullOrEmpty()) {
            ValidationResult(
                false,
                R.string.error_field_cannot_be_empty
            )
        }else {
            ValidationResult()
        }

    private fun prepareCardForSave(): Card {
        val cardId = cardItem?.id ?: NEW_CARD_ID
        val frontSide = CardSideItem(cardId = cardId, title = cardFrontTitle.value)
        val backSide = CardSideItem(cardId = cardId, title = cardBackTitle.value)
        val card = CardItem(id = cardId, frontSide = frontSide, backSide = backSide)
        return mapper.mapReverse(card)
    }

    override fun onCleared() {
        cardFrontTitle.clear()
        cardBackTitle.clear()
        super.onCleared()
    }

}

