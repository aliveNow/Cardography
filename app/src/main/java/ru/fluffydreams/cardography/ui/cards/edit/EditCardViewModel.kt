package ru.fluffydreams.cardography.ui.cards.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import ru.fluffydreams.cardography.core.data.NonNullLiveData
import ru.fluffydreams.cardography.core.data.NonNullMutableLiveData
import ru.fluffydreams.cardography.core.fragment.BaseViewModel
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

    /* FIXME Можно создать наследника TextInputLayout, который будет сам валидировать своё
    содержимое. Передавать ему условия валидации и текст. Сразу куча кода уйдёт
    */
    private val _errorOnFrontTitleIsVisible = NonNullMutableLiveData(false)
    private val _errorOnBackTitleIsVisible = NonNullMutableLiveData(false)
    private val cardTitleObserver = Observer<String>{ validate() }

    var cardItem: CardItem? = null
        set(value) {
            if (field == null) value?.let {
                field = it
                cardFrontTitle.value = it.frontSide.title
                cardBackTitle.value = it.backSide.title
            }
        }

    val cardFrontTitle = MutableLiveData<String>()
    val cardBackTitle = MutableLiveData<String>()

    val errorOnFrontTitleIsVisible: NonNullLiveData<Boolean>
        get() = _errorOnFrontTitleIsVisible

    val errorOnBackTitleIsVisible: NonNullLiveData<Boolean>
        get() = _errorOnBackTitleIsVisible

    init {
        cardFrontTitle.observeForever(cardTitleObserver)
        cardBackTitle.observeForever(cardTitleObserver)
    }

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
        validate(cardFrontTitle, _errorOnFrontTitleIsVisible) and
                validate(cardBackTitle, _errorOnBackTitleIsVisible)

    private fun validate(
        field: LiveData<String>,
        errorIsVisible: NonNullMutableLiveData<Boolean>
    ): Boolean =
        (!field.value.isNullOrEmpty()).also {
            errorIsVisible.value = !it
        }

    private fun prepareCardForSave(): Card {
        val cardId = cardItem?.id ?: NEW_CARD_ID
        val frontSide = CardSideItem(cardId = cardId, title = cardFrontTitle.value)
        val backSide = CardSideItem(cardId = cardId, title = cardBackTitle.value)
        val card = CardItem(id = cardId, frontSide = frontSide, backSide = backSide)
        return mapper.mapReverse(card)
    }

    override fun onCleared() {
        cardFrontTitle.removeObserver(cardTitleObserver)
        cardBackTitle.removeObserver(cardTitleObserver)
        super.onCleared()
    }

}