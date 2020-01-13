package ru.fluffydreams.cardography.ui.cards.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cards.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.core.fragment.BaseFragment
import ru.fluffydreams.cardography.core.fragment.OperationState
import ru.fluffydreams.cardography.core.ui.startRefreshing
import ru.fluffydreams.cardography.core.ui.stopRefreshing
import ru.fluffydreams.cardography.ui.cards.CardItem
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper

class CardsFragment : BaseFragment() {

    private val viewModel: CardsViewModel by viewModel()
    private val adapter = CardsAdapter()

    override val layoutId: Int
        get() = R.layout.fragment_cards

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.cards.observe(this, Observer(::updateCards))
        viewModel.operationState.observe(this, Observer(::updateOperationState))
        fab.setOnClickListener {
            navigateTo(CardsFragmentDirections.actionFromCardsToEditCard())
        }
        cardsList.layoutManager = LinearLayoutManager(context)
        cardsList.adapter = adapter
        swipeRefreshLayout.setOnRefreshListener { refresh() }
        // ItemTouchHelper отслеживает событие "swipe to dismiss" в recyclerView
        val touchHelper = ItemTouchHelper(SimpleItemTouchHelperCallback(::removeItemAt))
        touchHelper.attachToRecyclerView(cardsList)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cards, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)

    private fun updateCards(list: List<CardItem>?) {
        adapter.submitList(list)
    }

    private fun updateOperationState(state: OperationState) {
        when(state) {
            is OperationState.InProgress -> {
                hideSnackBar()
                swipeRefreshLayout.startRefreshing()
            }
            is OperationState.Failed -> {
                swipeRefreshLayout.stopRefreshing()
                showSnackBar(
                    error = state.message,
                    length = Snackbar.LENGTH_INDEFINITE,
                    actionTextId = R.string.retry,
                    action = ::refresh)
            }
            is OperationState.Done -> {
                swipeRefreshLayout.stopRefreshing()
                showSnackBar(success = state.message)
            }
        }
    }

    private fun refresh() {
        viewModel.load()
    }

    private fun removeItemAt(position: Int) {
        viewModel.delete(adapter.removeItemAt(position))
    }

}

//region ItemTouchHelper for swipe-to-dismiss
//==============================================================================================
private class SimpleItemTouchHelperCallback(
    private val onItemDismissAction: (Int) -> Unit
) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean = false

    override fun isItemViewSwipeEnabled(): Boolean = true

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = 0
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onItemDismissAction(viewHolder.adapterPosition)
    }

}
//==============================================================================================
//endregion
