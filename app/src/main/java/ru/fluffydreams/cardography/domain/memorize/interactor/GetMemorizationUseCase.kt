package ru.fluffydreams.cardography.domain.memorize.interactor

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.data.map
import ru.fluffydreams.cardography.core.filter.Filter
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.domain.memorize.MemorizeRepository
import ru.fluffydreams.cardography.domain.memorize.model.FactMemorization
import ru.fluffydreams.cardography.domain.memorize.model.MemFact
import ru.fluffydreams.cardography.domain.memorize.memorization.MemFactMemorization

class GetMemorizationUseCase<F : MemFact> (
    private val memorizeRepository: MemorizeRepository<F>
): UseCase<FactMemorization<F>, Filter>() {

    override suspend fun perform(params: Filter): Resource<FactMemorization<F>> =
        memorizeRepository.get(params).map {
            MemFactMemorization(it)
        }

}