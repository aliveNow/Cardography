package ru.fluffydreams.cardography.domain.memorize.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.interactor.UseCase
import ru.fluffydreams.cardography.domain.memorize.MemorizeRepository
import ru.fluffydreams.cardography.domain.memorize.model.MemFact
import ru.fluffydreams.cardography.domain.memorize.memorization.MemFactMemorization
import java.util.*

class SaveMemorizationResultUseCase<F : MemFact> (
    private val memorizeRepository: MemorizeRepository<F>
): UseCase<Boolean, MemFactMemorization<F>>() {

    private val queriesQueue: Queue<List<MemFact>> = LinkedList()

    override fun beforePerform(params: MemFactMemorization<F>) {
        super.beforePerform(params)
        with(params.changes) {
            val query = changed()
            markSaving(query)
            queriesQueue.offer(query)
        }
    }

    @Synchronized
    override suspend fun perform(params: MemFactMemorization<F>): Resource<Boolean> {
        val query = queriesQueue.poll()
        query?.let {
            memorizeRepository.save(it)
            withContext(Dispatchers.Main) {
                params.changes.markSaved(it)
            }
        }
        return Resource.Success(true)
    }

}