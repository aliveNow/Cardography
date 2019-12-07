package ru.fluffydreams.cardography.core.extensions

import androidx.lifecycle.MutableLiveData
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.exception.Failure

fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T) =
    postValue(Resource.Success(data))

fun <T> MutableLiveData<Resource<T>>.setLoading() =
    postValue(Resource.Loading(value?.data))

fun <T> MutableLiveData<Resource<T>>.setError(failure: Failure) =
    postValue(Resource.Error(failure, value?.data))

fun <T> MutableLiveData<Resource<T>>.setResource(resource: Resource<T>) = when (resource) {
    is Resource.Success -> setSuccess(resource.data)
    is Resource.Loading -> setLoading()
    is Resource.Error -> setError(resource.failure)
}
