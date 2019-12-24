package ru.fluffydreams.cardography.core.filter

abstract class FilterInterpreterBuilder<T> {
    private val entitiesMap = mutableMapOf<String, String>()
    private val fieldsMap = mutableMapOf<String, MutableMap<String, String>>()
    private val relationsMap = mutableMapOf<Pair<String, String>, Pair<String, String>>()
    private var fieldMapper: FieldMapper? = null
    private var relationMapper: RelationMapper? = null

    companion object

    fun setEntity(entity: String, mappedEntity: String) =
        apply { entitiesMap[entity] = mappedEntity }

    fun setField(entity: String, field: String, mappedField: String) =
        apply {
            fieldsMap.getOrPut(entity, { mutableMapOf() })[field] = mappedField
        }

    fun setRelation(entity1: String, entity2: String, field1: String, field2: String) =
        apply { relationsMap[entity1 to entity2] = field1 to field2 }

    abstract fun getInterpreter(): FilterInterpreter<T>

    protected fun createFieldMapper(): FieldMapper =
        fieldMapper?.let {
            it
        } ?: SimpleFieldMapper(entitiesMap.toMap(), fieldsMap.mapValues { it.value.toMap() })


    protected fun createRelationMapper(): RelationMapper =
        relationMapper?.let {
            it
        } ?: SimpleRelationMapper(relationsMap)

}



