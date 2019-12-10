package ru.fluffydreams.cardography.core.data

import androidx.lifecycle.LiveData

open class NonNullLiveData<T: Any>(initValue: T): LiveData<T>(initValue) {

    override fun getValue(): T {
        return super.getValue()!!
    }

}