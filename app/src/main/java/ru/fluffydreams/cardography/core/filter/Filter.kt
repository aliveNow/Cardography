package ru.fluffydreams.cardography.core.filter

import kotlin.reflect.KClass

sealed class Filter {
    object None: Filter()

    companion object
}

interface HasOperand<T : Any> {
    val operand: Operand<T>
}

data class Operand<T : Any>(val entity: String, val field: String, val type: KClass<T>)

data class LimitFilter<T : Any>(
    override val operand: Operand<T>,
    val min: T,
    val max: T
) : Filter(), HasOperand<T>

data class ValuesFilter<T : Any>(
    override val operand: Operand<T>,
    val values: Iterable<T>
) : Filter(), HasOperand<T>

data class CompositeFilter(
    val left: Filter,
    val right: Filter,
    val operator: Operator
) : Filter()

enum class Operator { AND }
