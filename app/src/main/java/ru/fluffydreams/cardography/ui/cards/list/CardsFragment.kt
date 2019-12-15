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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cards, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())
                || super.onOptionsItemSelected(item)
    }

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

    private fun refresh() = viewModel.get()

}
