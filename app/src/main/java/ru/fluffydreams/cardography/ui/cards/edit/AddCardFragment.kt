package ru.fluffydreams.cardography.ui.cards.edit

import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_add_card.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.core.fragment.BaseFragment
import ru.fluffydreams.cardography.core.fragment.OperationState
import ru.fluffydreams.cardography.core.ui.morphDone
import ru.fluffydreams.cardography.core.ui.startIndeterminateProgress

class AddCardFragment : BaseFragment() {

    private val viewModel: AddCardViewModel by viewModel()

    override val layoutId: Int
        get() = R.layout.fragment_add_card

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.operationState.observe(this, Observer(::updateOperationState))
        buttonSave.setOnClickListener { save() }
    }

    private fun updateOperationState(state: OperationState) {
        when(state) {
            is OperationState.InProgress -> {
                hideSnackBar()
                buttonSave.startIndeterminateProgress()
            }
            is OperationState.Failed -> showSnackBar(error = state.message)
            is OperationState.Done -> {
                context?.let { buttonSave.morphDone(it) }
                finishWithDelay()
            }
        }
    }

    private fun save() {
        viewModel.save(
            cardFrontTitle.text?.toString() ?: "test",
            cardBackTitle.text?.toString() ?: "test2")
    }

}