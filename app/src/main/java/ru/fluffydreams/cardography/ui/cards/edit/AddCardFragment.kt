package ru.fluffydreams.cardography.ui.cards.edit

import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_add_card.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.fragment.BaseFragment
import ru.fluffydreams.cardography.ui.cards.CardItem

class AddCardFragment : BaseFragment() {

    companion object {
        fun newInstance() = AddCardFragment()
    }

    private val viewModel: AddCardViewModel by sharedViewModel()

    override val layoutId: Int
        get() = R.layout.fragment_add_card

    override fun retry() {
        save()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.card.observe(this, Observer { afterSave(it) })
        buttonSave.setOnClickListener { save() }
    }

    //fixme нужно показать какой-нибудь прогресс при сохранении
    private fun save() = viewModel.save()

    private fun afterSave(resource: Resource<CardItem>?) {
        resource?.let {
            it.data?.let { card -> snackBar.show() }
            //it.failure?.let { snackBar.show() }
        }
    }

}