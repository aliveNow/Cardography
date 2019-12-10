package ru.fluffydreams.cardography.core.mapper

open class EntityMapper<E, R>(
    val mapper: (E) -> R,
    val reverseMapper: (R) -> E
) {

    fun map(entity: E): R = mapper(entity)

    fun mapReverse(entity: R): E = reverseMapper(entity)

    fun map(list: List<E>): List<R> = list.map {mapper(it)}

    fun mapReverse(list: List<R>): List<E> = list.map {reverseMapper(it)}

}
