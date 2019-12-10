package ru.fluffydreams.cardography.ui.cards

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardItem(
    val id: Long = NEW_CARD_ID,
    val frontSide: CardSideItem,
    val backSide: CardSideItem
) : Parcelable

@Parcelize
data class CardSideItem(
    val cardId: Long = NEW_CARD_ID,
    val title: String? = null,
    val content: String? = null
) : Parcelable

const val NEW_CARD_ID = 0L