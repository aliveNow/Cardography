package ru.fluffydreams.cardography.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ru.fluffydreams.cardography.core.mapper.EntityMapper

class MappedListLiveData<D, U>(
    private val mapper: EntityMapper<D, U>
): LiveData<List<U>>(), Observer<List<D>> {

    var source: LiveData<List<D>>? = null
        set(value) {
            stopObserve()
            field = value
            if (hasActiveObservers()) {
                startObserve()
            }
            value?.let { onChanged(it.value) }
        }

    override fun onActive() = startObserve()

    override fun onInactive() = stopObserve()

    private fun startObserve() {
        source?.observeForever(this)
    }

    private fun stopObserve() {
        source?.removeObserver(this)
    }

    override fun onChanged(t: List<D>?) {
        t?.let { postValue(mapper.map(it)) }
    }
}