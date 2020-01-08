package ru.fluffydreams.cardography.domain.memorize.model

enum class MemorizeAttemptResult(val id: Long) {
    FORGOTTEN(100),
    RECALLED_BADLY(400),
    RECALLED_WELL(500),
    RECALLED_FABULOUS(600);

    companion object {
        fun getById(id: Long): MemorizeAttemptResult = values().first { it.id == id }
    }
}