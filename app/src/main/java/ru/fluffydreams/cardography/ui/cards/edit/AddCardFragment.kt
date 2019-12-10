package ru.fluffydreams.cardography.ui.cards.edit

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_add_card.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.core.fragment.BaseFragment

class AddCardFragment : BaseFragment() {

    private val viewModel: AddCardViewModel by sharedViewModel()

    override val layoutId: Int
        get() = R.layout.fragment_add_card

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        buttonSave.setOnClickListener { save() }
    }

    private fun save() {
        viewModel.save(
            cardFrontTitle.text?.toString() ?: "test",
            cardBackTitle.text?.toString() ?: "test2")
    }

}