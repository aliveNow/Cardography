package ru.fluffydreams.cardography.datasource.local.memcard

import androidx.sqlite.db.SupportSQLiteQuery
import ru.fluffydreams.cardography.core.data.Resource
import ru.fluffydreams.cardography.core.filter.Filter
import ru.fluffydreams.cardography.core.filter.FilterInterpreter
import ru.fluffydreams.cardography.data.memorize.FactLocalDataSource
import ru.fluffydreams.cardography.datasource.local.memcard.mapper.LocalMemCardMapper
import ru.fluffydreams.cardography.domain.memcard.model.MemCard

class MemCardLocalDataSource(
    private val memCardDao: MemCardDao,
    private val memCardMapper: LocalMemCardMapper,
    private val filterInterpreter: FilterInterpreter<SupportSQLiteQuery>
) : FactLocalDataSource<MemCard> {

    override fun get(filter: Filter): Resource<List<MemCard>> {
        val list = memCardDao.getAll()
        //val list = memCardDao.getList(filterInterpreter("cards", filter)) //fixme constant?
        return Resource.Success(memCardMapper.mapReverse(list))
    }

}