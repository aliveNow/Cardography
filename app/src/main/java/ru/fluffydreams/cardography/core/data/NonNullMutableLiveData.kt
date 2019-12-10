package ru.fluffydreams.cardography.core.data

class NonNullMutableLiveData <T: Any>(initValue: T): NonNullLiveData<T>(initValue) {

    public override fun setValue(value: T) {
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        super.postValue(value)
    }

}