package ru.fluffydreams.cardography.domain.memorize.interactor

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.data.map
import ru.fluffydreams.cardography.core.filter.Filter
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.memorize.model.CardsMemorization
import ru.fluffydreams.cardography.domain.memorize.model.Memorization
import ru.fluffydreams.cardography.domain.memorize.MemorizeCardRepository

class GetCardsMemorizationUseCase (
    private val memorizeCardRepository: MemorizeCardRepository
): UseCase<Memorization<Card>, Filter>() {

    override suspend fun perform(params: Filter): Resource<Memorization<Card>> =
        memorizeCardRepository.get(params).map {
            CardsMemorization(
                it
            )
        }

}