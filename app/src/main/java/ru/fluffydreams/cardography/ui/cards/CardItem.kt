package ru.fluffydreams.cardography.ui.cards

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardItem(
    val id: Long,
    val frontSide: CardSideItem,
    val backSide: CardSideItem
) : Parcelable

@Parcelize
data class CardSideItem(
    val cardId: Long,
    val title: String?,
    val content: String?
) : Parcelable

