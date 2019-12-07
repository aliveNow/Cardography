package ru.fluffydreams.cardography.domain.cards.interactor

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.core.interactor.UseCase.None
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.cards.CardRepository

class GetCardsUseCase (
    private val cardRepository: CardRepository
): UseCase<List<Card>, None>() {

    override suspend fun perform(params: None): Resource<List<Card>> =
        cardRepository.get()

}