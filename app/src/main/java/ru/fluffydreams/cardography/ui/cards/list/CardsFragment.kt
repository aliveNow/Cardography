package ru.fluffydreams.cardography.ui.cards.list

import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_cards.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.extensions.startRefreshing
import ru.fluffydreams.cardography.core.extensions.stopRefreshing
import ru.fluffydreams.cardography.core.fragment.BaseFragment
import ru.fluffydreams.cardography.ui.cards.CardItem

class CardsFragment : BaseFragment() {

    companion object {
        fun newInstance() = CardsFragment()
    }

    private val viewModel: CardsViewModel by sharedViewModel()
    private val adapter = CardsAdapter()

    override val layoutId: Int
        get() = R.layout.fragment_cards

    override fun retry() {
        refresh()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.cards.observe(this, Observer { updateCards(it) })
        swipeRefreshLayout.setOnRefreshListener { refresh() }
    }

    private fun refresh() = viewModel.get()

    private fun updateCards(resource: Resource<List<CardItem>>?) {
        resource?.let {
            when(it) {
                is Resource.Success -> swipeRefreshLayout.stopRefreshing()
                is Resource.Loading -> swipeRefreshLayout.startRefreshing()
                is Resource.Error -> swipeRefreshLayout.stopRefreshing()
            }
            it.data?.let { list -> adapter.submitList(list) }
            it.failure?.let { snackBar.show() }
        }
    }

}
