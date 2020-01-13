package ru.fluffydreams.cardography.domain.cards.interactor

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.domain.cards.CardRepository
import ru.fluffydreams.cardography.domain.cards.model.Card

class DeleteCardUseCase (
    private val cardRepository: CardRepository
): UseCase<Card, Card>() {

    override suspend fun perform(params: Card): Resource<Card> =
        cardRepository.delete(params)

}