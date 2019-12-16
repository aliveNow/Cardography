package ru.fluffydreams.cardography.domain.memorize.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.memorize.MemorizeCardRepository
import ru.fluffydreams.cardography.domain.memorize.model.CardsMemorization
import ru.fluffydreams.cardography.domain.memorize.model.Memorization
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttempt
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class SaveMemorizationResultUseCase (
    private val memorizeCardRepository: MemorizeCardRepository
): UseCase<Boolean, Memorization<Card>>() {

    private val queriesQueue: Queue<List<MemorizeAttempt>> = ConcurrentLinkedQueue()

    override fun beforePerform(params: Memorization<Card>) {
        super.beforePerform(params)
        val query = (params as? CardsMemorization)?.notSavedAttempts ?: emptyList()
        queriesQueue.offer(query)
    }

    @Synchronized
    override suspend fun perform(params: Memorization<Card>): Resource<Boolean> {
        val attempts = queriesQueue.poll()
        attempts?.let {
            memorizeCardRepository.save(it)
            if (params is CardsMemorization) {
                withContext(Dispatchers.Main) {
                    params.attemptsWereSaved(it)
                }
            }
        }
        return Resource.Success(true)
    }

}