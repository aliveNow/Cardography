package ru.fluffydreams.cardography.ui.cards.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list_card.view.*
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.core.ui.inflate
import ru.fluffydreams.cardography.core.ui.setTextOrHide
import ru.fluffydreams.cardography.ui.cards.CardItem
import ru.fluffydreams.cardography.ui.cards.CardSideItem

class CardsAdapter(
    val clickListener: (CardItem) -> Unit
) : ListAdapter<CardItem, CardsAdapter.ViewHolder>(CardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_list_card))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = getItem(position)
        holder.bind(card.frontSide)
        holder.itemView.setOnClickListener { clickListener(card) }
    }

    fun removeItemAt(position: Int): CardItem =
        with(currentList.toMutableList()) {
            val removedItem = removeAt(position)
            submitList(this)
            removedItem
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cardSide: CardSideItem) =
            with(cardSide) {
                itemView.cardTitle.setTextOrHide(title)
                itemView.cardContent.setTextOrHide(content)
            }
    }
}

private class CardDiffCallback : DiffUtil.ItemCallback<CardItem>() {

    override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem): Boolean =
        oldItem == newItem
}