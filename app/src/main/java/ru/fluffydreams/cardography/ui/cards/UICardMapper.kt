package ru.fluffydreams.cardography.ui.cards

import ru.fluffydreams.cardography.core.mapper.EntityMapper
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.cards.CardSide

class UICardMapper : EntityMapper<Card, CardItem>(
    { entity : Card -> entity.mapToUI() },
    { entity : CardItem -> entity.mapToDomain() }
)

fun Card.mapToUI() : CardItem =
    CardItem(id, frontSide.mapToUI(), backSide.mapToUI())

fun CardSide.mapToUI() : CardSideItem =
    CardSideItem(cardId, title, content)

fun CardItem.mapToDomain() : Card =
    Card(
        id,
        frontSide.mapToDomain(),
        backSide.mapToDomain()
    )

fun CardSideItem.mapToDomain() : CardSide =
    CardSide(cardId, title, content)