package ru.fluffydreams.cardography.ui.cards

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.fluffydreams.cardography.core.data.Identifiable
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult
import java.util.*

@Parcelize
data class CardItem(
    val id: Long = NEW_CARD_ID,
    val frontSide: CardSideItem,
    val backSide: CardSideItem,
    val memId: Long = 0,
    val factId: Long = 0,
    val dateFirst: Date? = null,
    val dateLast: Date? = null,
    val lastResult: MemorizeAttemptResult? = null,
    override val uniqueId: String = memId.toString()
) : Identifiable, Parcelable

@Parcelize
data class CardSideItem(
    val cardId: Long = NEW_CARD_ID,
    val title: String? = null,
    val content: String? = null
) : Parcelable

const val NEW_CARD_ID = 0L