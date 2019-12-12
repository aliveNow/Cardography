package ru.fluffydreams.cardography.core.fragment

import androidx.lifecycle.ViewModel
import ru.fluffydreams.cardography.core.data.NonNullLiveData
import ru.fluffydreams.cardography.core.data.NonNullMutableLiveData
import ru.fluffydreams.cardography.core.data.Resource

open class BaseViewModel : ViewModel() {

    val operationState: NonNullLiveData<OperationState>
        get() = _operationState

    private val _operationState: NonNullMutableLiveData<OperationState> =
        NonNullMutableLiveData(OperationState.None())

    protected fun beforeUseCase() {
        _operationState.value = OperationState.InProgress()
    }

    protected fun afterUseCase(resource: Resource<*>) {
        _operationState.value = resource.failure?.let {
            OperationState.Failed(it.toString())
        } ?: OperationState.Done()
    }

}