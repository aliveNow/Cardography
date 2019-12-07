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