package ru.fluffydreams.cardography.core.filter

interface RelationMapper {
    operator fun invoke(entity1: String, entity2: String): Pair<String, String>
    operator fun invoke(entities: Pair<String, String>): Pair<String, String>
}

class SimpleRelationMapper(
    private val relationMap: Map<Pair<String, String>, Pair<String, String>>
) : RelationMapper {

    override fun invoke(entity1: String, entity2: String): Pair<String, String> =
        invoke(entity1 to entity2)

    override fun invoke(entities: Pair<String, String>): Pair<String, String> =
        relationMap[entities]?.let {
            return it
        } ?: relationMap[entities.second to entities.first]?.let {
            return it
        } ?: throw IllegalArgumentException("$entities - no such relation between entities exists")

}