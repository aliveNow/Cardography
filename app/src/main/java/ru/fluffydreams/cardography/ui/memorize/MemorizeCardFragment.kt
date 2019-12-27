package ru.fluffydreams.cardography.ui.memorize

import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_memorize_card.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.core.fragment.BaseFragment
import ru.fluffydreams.cardography.core.ui.setVisible
import ru.fluffydreams.cardography.ui.cards.CardItem
import ru.fluffydreams.cardography.ui.memorize.MemorizeCardViewModel.EmptyMemorization

class MemorizeCardFragment : BaseFragment() {

    private val viewModel: MemorizeCardViewModel by viewModel()
    private var memorization: CardItemMemorization = EmptyMemorization
        set(value) {
            val oldValue = field
            field = value
            updateMemorization(value, oldValue)
        }

    override val layoutId: Int
        get() = R.layout.fragment_memorize_card

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(this, Observer(::updateState))
        buttonShowAnswer.setOnClickListener { memorization.showAnswer() }
        buttonSetAside.setOnClickListener { memorization.setAside() }
        buttonNext.setOnClickListener { memorization.next() }
        buttonDone.setOnClickListener { memorization.done() }
    }

    private fun updateState(state: MemorizationState) {
        if (state == MemorizationState.DONE) {
            finish()
        } else {
            memorization = viewModel.memorization
        }
    }

    private fun updateMemorization(memorization: CardItemMemorization,
                                   oldValue: CardItemMemorization? = null) {
        if (memorization != oldValue && memorization != EmptyMemorization) {
            oldValue?.currentFact?.removeObservers(this)
            oldValue?.isAnswerVisible?.removeObservers(this)
            memorization.currentFact
                .observe(this, Observer(::updateCard))
            memorization.isAnswerVisible
                .observe(this, Observer(::updateAnswer))
        }
    }

    private fun updateCard(card: CardItem) {
        cardFrontTitle.text = card.frontSide.title
        cardBackTitle.text = card.backSide.title
        updateButtons()
    }

    private fun updateButtons() {
        buttonShowAnswer.setVisible(!memorization.isAnswerVisible.value)
        buttonSetAside.setVisible(memorization.hasNext)
        buttonNext.setVisible(memorization.hasNext)
        buttonDone.setVisible(!memorization.hasNext)
    }

    private fun updateAnswer(isVisible: Boolean) {
        cardBackTitle.setVisible(isVisible)
        updateButtons()
    }

}