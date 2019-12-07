package ru.fluffydreams.cardography.datasource.local.cards

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.data.cards.CardLocalDataSource
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.cards.CardSide

class CardLocalDataSourceImpl(
    private val cardDao: CardDao
) : CardLocalDataSource {

    // FIXME: what if it's empty?....
    override fun get(): Resource<List<Card>> =
        Resource.Success(cardDao.getAll().mapToDomain())

}

fun Iterable<CardEntity>.mapToDomain(): List<Card> = map {it.mapToDomain()}

fun CardEntity.mapToDomain(): Card = Card(uid, frontSide(), backSide())

fun CardEntity.frontSide(): CardSide = CardSide(uid, title1, content1)

fun CardEntity.backSide(): CardSide = CardSide(uid, title2, content2)