package ru.fluffydreams.cardography.core.mapper

import ru.fluffydreams.cardography.core.data.Resource

open class EntityMapper<E, R>(
    private val mapper: (E) -> R,
    private val reverseMapper: (R) -> E
) {

    fun map(entity: E): R = mapper(entity)

    fun mapReverse(entity: R): E = reverseMapper(entity)

    fun mapReverse(list: List<R>): List<E> = list.map {reverseMapper(it)}

    fun map(resource: Resource<E>): Resource<R> =
        resource.map(mapper)

    fun mapList(resource: Resource<List<E>>): Resource<List<R>> =
        resource.mapList(mapper)

}

inline fun <E, R> Resource<E>.map(mapper: (E) -> R): Resource<R> = when(this) {
    is Resource.Success -> Resource.Success(mapper(data))
    is Resource.Loading -> Resource.Loading()
    is Resource.Error -> Resource.Error(failure)
}

inline fun <E, R> Resource<List<E>>.mapList(mapper: (E) -> R): Resource<List<R>> = when(this) {
    is Resource.Success -> Resource.Success(data.map { mapper(it) })
    is Resource.Loading -> Resource.Loading()
    is Resource.Error -> Resource.Error(failure)
}
