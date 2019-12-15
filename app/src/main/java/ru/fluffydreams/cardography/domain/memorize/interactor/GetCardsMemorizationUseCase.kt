package ru.fluffydreams.cardography.domain.memorize.interactor

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.data.map
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.memorize.model.CardsMemorization
import ru.fluffydreams.cardography.domain.memorize.model.Memorization
import ru.fluffydreams.cardography.domain.memorize.MemorizeCardRepository

class GetCardsMemorizationUseCase (
    private val memorizeCardRepository: MemorizeCardRepository
): UseCase<Memorization<Card>, UseCase.None>() {

    override suspend fun perform(params: None): Resource<Memorization<Card>> =
        memorizeCardRepository.get().map {
            CardsMemorization(
                it
            )
        }

}