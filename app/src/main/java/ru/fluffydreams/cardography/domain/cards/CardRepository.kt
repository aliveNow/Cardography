package ru.fluffydreams.cardography.domain.cards

import androidx.lifecycle.LiveData
import ru.fluffydreams.cardography.core.data.Resource

interface CardRepository {

    fun get(): Resource<LiveData<List<Card>>>

    fun add(card: Card): Resource<Card>

}