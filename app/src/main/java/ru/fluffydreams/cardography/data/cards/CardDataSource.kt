package ru.fluffydreams.cardography.data.cards

import androidx.lifecycle.LiveData
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.domain.cards.model.Card

interface CardLocalDataSource {

    fun get(): Resource<LiveData<List<Card>>>

    fun save(card: Card): Resource<Card>

    fun delete(card: Card): Resource<Card>

}