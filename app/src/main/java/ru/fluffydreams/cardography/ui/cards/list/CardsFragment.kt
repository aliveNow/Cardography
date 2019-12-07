package ru.fluffydreams.cardography.ui.cards.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cards.*
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.extensions.startRefreshing
import ru.fluffydreams.cardography.core.extensions.stopRefreshing
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.fluffydreams.cardography.ui.cards.CardItem

class CardsFragment : Fragment() {

    companion object {
        fun newInstance() = CardsFragment()
    }

    private val viewModel: CardsViewModel by sharedViewModel()
    private val adapter = CardsAdapter()
    private val snackBar by lazy {
        Snackbar.make(swipeRefreshLayout, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry)) { refresh() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cards, container, false)
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
