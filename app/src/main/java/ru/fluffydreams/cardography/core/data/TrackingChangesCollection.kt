package ru.fluffydreams.cardography.core.data

// Not Thread Save!
class TrackingChangesCollection<T : Identifiable> {

    private val changesMap: MutableMap<String, MutableChanges<T>> = mutableMapOf()

    fun add(initial: T, changed: T) {
        changesMap.getOrPut(initial.uniqueId, {MutableChanges(initial)}).changed = changed
    }

    fun changed(): List<T> =
        changesMap.filterValues { it.changed != null }.map { it.value.changed!! }

    fun changed(id: String) = changesMap[id]?.changed

    fun markSaving(savingList: List<T>) {
        savingList.forEach { changesMap[it.uniqueId]?.markSaving(it) }
    }

    fun markSaved(savedList: List<T>) {
        savedList.forEach { changesMap[it.uniqueId]?.markSaved(it) }
    }

    private open class Changes<T : Identifiable>(
        val initial: T,
        open val changed: T? = null,
        open val saving: Iterable<T> = emptyList(),
        open val saved: T? = null
    ) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Changes<*>

            if (changed != other.changed) return false
            if (saved != other.saved) return false
            if (saving != other.saving) return false

            return true
        }

        override fun hashCode(): Int {
            var result = changed?.hashCode() ?: 0
            result = 31 * result + (saved?.hashCode() ?: 0)
            result = 31 * result + saving.hashCode()
            return result
        }
    }

   private class MutableChanges<T : Identifiable>(
       initial: T,
       override var changed: T? = null,
       override val saving: MutableSet<T> = mutableSetOf(),
       override var saved: T? = null
   ) : Changes<T>(initial, changed, saving, saved) {

       fun markSaving(savingItem: T) {
           saving.add(savingItem)
           if (savingItem == changed) {
               changed = null
           }
       }

       fun markSaved(savedItem : T) {
           saving.remove(savedItem)
           saved = savedItem
       }

   }
}