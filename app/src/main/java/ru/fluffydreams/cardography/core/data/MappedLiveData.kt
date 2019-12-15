package ru.fluffydreams.cardography.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class MappedLiveData<D, U>(
    val transform: (D) -> U
): LiveData<U>(), Observer<D> {

    var source: LiveData<D>? = null
        set(value) {
            stopObserve()
            field = value
            if (hasActiveObservers()) {
                startObserve()
            }
            value?.let { onChanged(it.value) }
        }

    public override fun setValue(value: U) = super.setValue(value)

    override fun onActive() = startObserve()

    override fun onInactive() = stopObserve()

    private fun startObserve() {
        source?.observeForever(this)
    }

    private fun stopObserve() {
        source?.removeObserver(this)
    }

    override fun onChanged(t: D?) {
        t?.let { postValue(transform(it)) }
    }

}