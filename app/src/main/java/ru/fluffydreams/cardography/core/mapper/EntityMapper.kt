package ru.fluffydreams.cardography.core.mapper

open class EntityMapper<D, R>(
    val transform: (D) -> R,
    val reverseTransform: (R) -> D
) {

    fun map(entity: D): R = transform(entity)

    fun mapReverse(entity: R): D = reverseTransform(entity)

    fun map(list: List<D>): List<R> = list.map { transform(it) }

    fun mapReverse(list: List<R>): List<D> = list.map { reverseTransform(it) }

}
