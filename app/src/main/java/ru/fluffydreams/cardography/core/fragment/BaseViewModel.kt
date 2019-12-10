package ru.fluffydreams.cardography.core.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.fluffydreams.cardography.core.data.NonNullLiveData
import ru.fluffydreams.cardography.core.data.NonNullMutableLiveData
import ru.fluffydreams.cardography.core.data.Resource

open class BaseViewModel : ViewModel() {

    val isInProgress: NonNullLiveData<Boolean>
        get() = _isInProgress

    val error: LiveData<String>
        get() = _error

    private val _isInProgress: NonNullMutableLiveData<Boolean> = NonNullMutableLiveData(false)
    private val _error: MutableLiveData<String> = MutableLiveData()

    protected fun beforeUseCase() {
        _isInProgress.value = true
    }

    protected fun afterUseCase(resource: Resource<*>) {
        _isInProgress.value = false
        _error.value = resource.failure?.toString()
    }

}