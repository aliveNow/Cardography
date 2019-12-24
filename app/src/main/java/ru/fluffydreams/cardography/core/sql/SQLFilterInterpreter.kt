package ru.fluffydreams.cardography.core.sql

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import ru.fluffydreams.cardography.core.filter.*


// FIXME: !!!!! Должен быть способен выполнять операции параллельно!
class SQLFilterInterpreter(
    private val fieldMapper: FieldMapper,
    private val relationMapper: RelationMapper,
    private val limit: Int = 0
) : FilterInterpreter<SupportSQLiteQuery> {

    private var mainEntity = ""
    private val tables = mutableSetOf<String>()

    override fun invoke(entity: String, filter: Filter): SupportSQLiteQuery {
        mainEntity = entity
        val where = buildWhere(filter)
        val whereBuilder = if (tables.isNotEmpty()) {
            StringBuilder().apply {
                for (table in tables) {
                    append("${buildWhereRelation(table)} AND ")
                }
                append("($where)")
            }
        } else {
            StringBuilder(where)
        }
        val queryBuilder = StringBuilder().apply {
            append("SELECT $mainEntity.* FROM $mainEntity")
            if (tables.isNotEmpty()) {
                append(", $tables")
            }
            if (whereBuilder.isNotEmpty()) {
                append(" WHERE $whereBuilder")
            }
            if (limit > 0) {
                append(" LIMIT $limit")
            }
        }
        tables.clear()
        return SimpleSQLiteQuery(queryBuilder.toString())
    }

    private fun buildWhere(filter: Filter): String =
        when(filter) {
            is LimitFilter<*> -> interpretFilter(filter)
            is ValuesFilter<*> -> interpretFilter(filter)
            is CompositeFilter -> {
                val left = buildWhere(filter.left)
                val right = buildWhere(filter.right)
                "($left ${filter.operator} $right)"
            }
            is Filter.None -> ""
        }

    private fun interpretFilter(filter: LimitFilter<*>): String {
        val operandName = getOperandName(filter)
        return "$operandName BETWEEN ${filter.min} AND ${filter.max}"
    }

    private fun interpretFilter(filter: ValuesFilter<*>): String {
        val operandName = getOperandName(filter)
        return "$operandName IN ${filter.values}"
    }

    private fun getOperandName(hasOperand: HasOperand<*>): String {
        val operand = fieldMapper(hasOperand.operand)
        return with(operand) {
            if (entity != mainEntity){
                tables.add(entity)
            }
            "$entity.$field"
        }
    }

    private fun buildWhereRelation(entity: String): String =
        with(relationMapper(mainEntity, entity)) {
            "$entity.$second = $mainEntity.$first"
        }

}