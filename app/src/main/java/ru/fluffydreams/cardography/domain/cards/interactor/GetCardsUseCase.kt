package ru.fluffydreams.cardography.domain.cards.interactor

import androidx.lifecycle.LiveData
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.core.interactor.UseCase.None
import ru.fluffydreams.cardography.domain.cards.CardRepository
import ru.fluffydreams.cardography.domain.cards.model.Card

class GetCardsUseCase (
    private val cardRepository: CardRepository
): UseCase<LiveData<List<Card>>, None>() {

    override suspend fun perform(params: None): Resource<LiveData<List<Card>>> =
        cardRepository.get()

}