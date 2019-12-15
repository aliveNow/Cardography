package ru.fluffydreams.cardography.core.data

import ru.fluffydreams.cardography.core.exception.Failure

// from https://developer.android.com/jetpack/docs/guide#addendum
sealed class Resource<out T>(
    private val _data: T? = null, //FIXME: is there a way to make "override of data" more simple?
    private val _failure: Failure? = null
) {
    open val data: T?
        get() = _data

    open val failure: Failure?
        get() = _failure

    class Success<T>(data: T) : Resource<T>(data) {
        override val data: T
            get() = super.data!!
    }

    class Loading<T>(data: T? = null) : Resource<T>(data)

    class Error<T>(failure: Failure, data: T? = null) : Resource<T>(data, failure) {
        override val failure: Failure
            get() = super.failure!!
    }
}

fun <F, T> Resource<F>.map(transform: (F) -> T): Resource<T> {
    val transformedData = data?.let { transform(it) }
    return when(this) {
        is Resource.Success -> Resource.Success(transformedData!!)
        is Resource.Loading -> Resource.Loading(transformedData)
        is Resource.Error -> Resource.Error(failure, transformedData)
    }
}