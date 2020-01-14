package ru.fluffydreams.cardography.core.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class ValidatedField(
    private val validation: (String?) -> ValidationResult
) : Observer<String> {

    private val _validationResult = NonNullMutableLiveData(ValidationResult())

    val field = MutableLiveData<String>()

    var value: String?
        get() = this.field.value
        set(value) {
            this.field.value = value
        }

    val validationResult: NonNullLiveData<ValidationResult>
        get() = _validationResult

    init {
        field.observeForever(this)
    }

    override fun onChanged(value: String?) {
        val result = validation(value)
        if (result != validationResult.value) _validationResult.value = result
    }

    fun validate(): ValidationResult =
        validation(field.value).also {
            if (it != validationResult.value) _validationResult.value = it
        }

    fun clear() {
        field.removeObserver(this)
    }

}

data class ValidationResult(
    val success: Boolean = true,
    val messageRes: Int? = null,
    val message: String? = null
)