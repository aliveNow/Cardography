package ru.fluffydreams.cardography.core.sql

inline fun <O> insertOrUpdateObject(obj: O,
                                    insertAction: (O) -> Long,
                                    updateAction: (O) -> Unit
): Long = insertAction(obj).also {
    if (it == FAILED_INSERT_ID) updateAction(obj)
}


inline fun <O> insertOrUpdateList(list: List<O>,
                                  insertAction: (List<O>) -> List<Long>,
                                  updateAction: (List<O>) -> Unit
) {
    val insertResult = insertAction(list)
    with(list.filterIndexed { index, _ -> insertResult[index] == FAILED_INSERT_ID }) {
        if (isNotEmpty()) updateAction(this)
    }
}

const val FAILED_INSERT_ID = -1L