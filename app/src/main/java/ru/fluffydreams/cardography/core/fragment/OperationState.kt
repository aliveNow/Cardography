package ru.fluffydreams.cardography.core.fragment

sealed class OperationState(val name: String? = null, val message: String? = null) {

    class None(name: String? = null, message: String? = null) : OperationState(name, message)

    class InProgress(name: String? = null, message: String? = null) : OperationState(name, message)

    class Failed(name: String? = null, message: String? = null) : OperationState(name, message)

    class Done(name: String? = null, message: String? = null) : OperationState(name, message)

}