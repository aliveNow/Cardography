package ru.fluffydreams.cardography.domain.cards

import androidx.lifecycle.LiveData
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.domain.cards.model.Card

interface CardRepository {

    fun get(): Resource<LiveData<List<Card>>>

    fun add(card: Card): Resource<Card>

    fun delete(card: Card): Resource<Card>

}