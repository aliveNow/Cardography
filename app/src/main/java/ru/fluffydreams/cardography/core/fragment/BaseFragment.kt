package ru.fluffydreams.cardography.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.fluffydreams.cardography.R

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    private var snackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutId, container, false)
    }

    protected fun showSnackBar(success: String? = null,
                               error: String? = null,
                               length: Int = Snackbar.LENGTH_SHORT,
                               actionTextId: Int? = null,
                               action:() -> Unit = {}) {
        hideSnackBar()
        val text = success ?: error ?: ""
        val snackParent = view?.findViewById<ViewGroup>(R.id.rootLayout)
        if (!text.isBlank() && snackParent != null) {
            snackBar = Snackbar.make(snackParent, text, length)
            snackBar?.apply {
                actionTextId?.let { setAction(it) { action() } }
            }?.show()
        }
    }

    protected fun hideSnackBar() {
        snackBar?.dismiss()
    }

    protected fun navigateTo(action: NavDirections) {
        findNavController().navigate(action)
    }

    protected fun finish() {
        findNavController().popBackStack()
    }

    protected fun finishWithDelay(delay: Long = 500) {
        lifecycleScope.launch {
            kotlinx.coroutines.delay(delay)
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                finish()
            }
        }
    }

}
