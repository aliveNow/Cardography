package ru.fluffydreams.cardography.ui.cards.list

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cards.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.core.ui.startRefreshing
import ru.fluffydreams.cardography.core.ui.stopRefreshing
import ru.fluffydreams.cardography.core.fragment.BaseFragment
import ru.fluffydreams.cardography.ui.cards.CardItem

class CardsFragment : BaseFragment() {

    private val viewModel: CardsViewModel by sharedViewModel()
    private val adapter = CardsAdapter()

    override val layoutId: Int
        get() = R.layout.fragment_cards

    //fixme!!!
    private val snackBar by lazy {
        Snackbar.make(view!!.rootView, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry)) { refresh() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.cards.observe(this, Observer(::updateCards))
        viewModel.isInProgress.observe(this, Observer(::updateProgress))
        viewModel.error.observe(this,
            Observer { updateSnackBar(error = it, actionTextId = R.string.retry) })
        fab.setOnClickListener {
            navigateTo(CardsFragmentDirections.actionFromCardsToAddCard())
        }
        cardsList.layoutManager = LinearLayoutManager(context)
        cardsList.adapter = adapter
        swipeRefreshLayout.setOnRefreshListener { refresh() }
    }

    private fun updateCards(list: List<CardItem>?) {
        adapter.submitList(list)
    }

    private fun updateProgress(isInProgress: Boolean) {
        with(swipeRefreshLayout) {
            if (isInProgress and !isRefreshing) {
                startRefreshing()
            } else {
                stopRefreshing()
            }
        }
    }

    private fun updateSnackBar(success: String? = null,
                               error: String? = null,
                               actionTextId: Int? = null,
                               action:() -> Unit = ::refresh) {
        hideSnackBar()
        val text = success ?: error ?: ""
        if (!text.isBlank() and !viewModel.isInProgress.value) {
            snackBar.apply {
                setText(text)
                actionTextId?.let { setAction(it) { action() } }
            }.show()
        }
    }

    private fun hideSnackBar() {
        if (snackBar.isShown) {
            snackBar.dismiss()
        }
    }

    private fun refresh() = viewModel.get()

    private fun navigateTo(action: NavDirections) {
        findNavController().navigate(action)
    }

}
