package ru.fluffydreams.cardography.ui.cards.edit

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_edit_card.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.core.fragment.BaseFragment
import ru.fluffydreams.cardography.core.fragment.OperationState
import ru.fluffydreams.cardography.core.ui.morphDone
import ru.fluffydreams.cardography.core.ui.startIndeterminateProgress
import ru.fluffydreams.cardography.databinding.FragmentEditCardBinding

class EditCardFragment : BaseFragment(useDataBinding = true) {

    private val viewModel: EditCardViewModel by viewModel()

    override val layoutId: Int
        get() = R.layout.fragment_edit_card

    override fun prepareDataBinding(binding: ViewDataBinding) {
        (binding as FragmentEditCardBinding).viewmodel = viewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.operationState.observe(viewLifecycleOwner, Observer(::updateOperationState))
        viewModel.errorOnFrontTitleIsVisible.observe(viewLifecycleOwner,
            Observer{ updateFieldError(cardFrontTitleLayout, it) })
        viewModel.errorOnBackTitleIsVisible.observe(viewLifecycleOwner,
            Observer{ updateFieldError(cardBackTitleLayout, it) })
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

    private fun updateFieldError(til: TextInputLayout, visible: Boolean) {
        til.error = if (visible) getString(R.string.error_field_cannot_be_empty) else null
    }

}