package ru.fluffydreams.cardography.core.filter

interface FilterInterpreter<T> {
    operator fun invoke(entity: String, filter: Filter): T
}