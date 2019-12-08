package ru.fluffydreams.cardography.datasource.local.cards

import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.cards.CardSide

class LocalCardMapper : EntityMapper<Card, CardEntity>(
    { entity : Card -> entity.mapToLocal() },
    { entity : CardEntity -> entity.mapToDomain() }
)

fun Card.mapToLocal(): CardEntity =
    CardEntity(id, frontSide.title, backSide.title, frontSide.content, backSide.content)

fun CardEntity.mapToDomain(): Card = Card(id, frontSide(), backSide())

fun CardEntity.frontSide(): CardSide = CardSide(id, title1, content1)

fun CardEntity.backSide(): CardSide = CardSide(id, title2, content2)