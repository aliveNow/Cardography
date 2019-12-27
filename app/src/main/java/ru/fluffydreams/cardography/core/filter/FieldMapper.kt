package ru.fluffydreams.cardography.core.filter

interface FieldMapper {
    operator fun invoke(operand: Operand<*>): Operand<*>
}

class SimpleFieldMapper(
    private val entitiesMap: Map<String, String>,
    private val fieldsMap: Map<String, Map<String, String>>
) : FieldMapper {

    override fun invoke(operand: Operand<*>): Operand<*> =
        entitiesMap[operand.entity]?.let { entity ->
            fieldsMap[entity]?.get(operand.field)?.let {
                Operand(entity, it, operand.type)
            } ?: throw IllegalArgumentException("Field ${operand.field} in $entity not exists")
        } ?: throw IllegalArgumentException("Entity ${operand.entity} not exists")

}