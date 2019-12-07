package ru.fluffydreams.cardography.ui.cards

import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.data.map
import ru.fluffydreams.cardography.domain.cards.Card
import ru.fluffydreams.cardography.domain.cards.CardSide

fun Resource<List<Card>>.mapToUI() : Resource<List<CardItem>> = map { it.mapToUI() }

fun List<Card>.mapToUI() : List<CardItem> = map { it.mapToUI() }

fun Card.mapToUI() : CardItem =
    CardItem(id, frontSide.mapToUI(), backSide.mapToUI())

fun CardSide.mapToUI() : CardSideItem =
    CardSideItem(cardId, title, content)