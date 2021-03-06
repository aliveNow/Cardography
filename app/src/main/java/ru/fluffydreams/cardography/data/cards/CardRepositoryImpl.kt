package ru.fluffydreams.cardography.data.cards

import androidx.lifecycle.LiveData
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.domain.cards.CardRepository
import ru.fluffydreams.cardography.domain.cards.model.Card

class CardRepositoryImpl (
    private val localDataSource: CardLocalDataSource
) : CardRepository {

    override fun get(): Resource<LiveData<List<Card>>> = localDataSource.get()

    override fun save(card: Card): Resource<Card> = localDataSource.save(card)

    override fun delete(card: Card): Resource<Card> = localDataSource.delete(card)

}