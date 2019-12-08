package ru.fluffydreams.cardography.domain.cards

data class Card(
    val id: Long,
    val frontSide: CardSide,
    val backSide: CardSide
)

data class CardSide(
    val cardId: Long,
    val title: String?,
    val content: String?
)

fun Card.copyWithId(id: Long): Card =
    copy(
        id = id,
        frontSide = frontSide.copy(cardId = id),
        backSide = backSide.copy(cardId = id)
    )