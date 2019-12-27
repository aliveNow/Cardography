package ru.fluffydreams.cardography.datasource.local.memcard.model

import java.util.*

data class MemCardQueryResult(
    val id: Long,
    val title1: String?,
    val title2: String?,
    val content1: String?,
    val content2: String?,
    val memId: Long,
    val factId: Long,
    val dateFirst: Date?,
    val dateLast: Date?,
    val lastResultId: Long?
)