package ru.fluffydreams.cardography

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.fluffydreams.cardography.core.filter.FilterInterpreterBuilder
import ru.fluffydreams.cardography.core.sql.SQLFilterInterpreter
import ru.fluffydreams.cardography.data.cards.CardLocalDataSource
import ru.fluffydreams.cardography.data.cards.CardRepositoryImpl
import ru.fluffydreams.cardography.data.memorize.FactLocalDataSource
import ru.fluffydreams.cardography.data.memorize.MemorizeLocalDataSource
import ru.fluffydreams.cardography.data.memorize.MemorizeRepositoryImpl
import ru.fluffydreams.cardography.datasource.local.AppDatabase
import ru.fluffydreams.cardography.datasource.local.cards.CardLocalDataSourceImpl
import ru.fluffydreams.cardography.datasource.local.cards.mapper.LocalCardMapper
import ru.fluffydreams.cardography.datasource.local.memcard.MemCardLocalDataSource
import ru.fluffydreams.cardography.datasource.local.memcard.mapper.LocalMemCardMapper
import ru.fluffydreams.cardography.datasource.local.memorize.MemorizeLocalDataSourceImpl
import ru.fluffydreams.cardography.datasource.local.memorize.mapper.LocalAttemptMapper
import ru.fluffydreams.cardography.datasource.local.memorize.mapper.LocalAttemptResultMapper
import ru.fluffydreams.cardography.datasource.local.memorize.mapper.LocalMemFactMapper
import ru.fluffydreams.cardography.domain.cards.CardRepository
import ru.fluffydreams.cardography.domain.cards.interactor.DeleteCardUseCase
import ru.fluffydreams.cardography.domain.cards.interactor.SaveCardUseCase
import ru.fluffydreams.cardography.domain.cards.interactor.GetCardsUseCase
import ru.fluffydreams.cardography.domain.memcard.model.MemCard
import ru.fluffydreams.cardography.domain.memorize.MemorizeRepository
import ru.fluffydreams.cardography.domain.memorize.interactor.GetMemorizationUseCase
import ru.fluffydreams.cardography.domain.memorize.interactor.SaveMemorizationResultUseCase
import ru.fluffydreams.cardography.domain.memorize.model.MemFact
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult
import ru.fluffydreams.cardography.ui.cards.UICardMapper
import ru.fluffydreams.cardography.ui.cards.edit.EditCardViewModel
import ru.fluffydreams.cardography.ui.cards.list.CardsViewModel
import ru.fluffydreams.cardography.ui.memorize.MemorizeCardViewModel
import ru.fluffydreams.cardography.ui.memorize.UIMemCardMapper

val viewModelModule: Module = module {
    viewModel { CardsViewModel(
        getCardsUseCase = get(named(GET_CARDS_USE_CASE)),
        deleteCardUseCase = get(named(DELETE_CARD_USE_CASE)),
        mapper = get(named(UI_CARD_MAPPER))
    ) }
    viewModel { EditCardViewModel(
        saveCardUseCase = get(named(SAVE_CARD_USE_CASE)),
        mapper = get(named(UI_CARD_MAPPER))
    ) }
    viewModel { MemorizeCardViewModel(
        getMemorizationUseCase = get(named(GET_CARDS_MEMORIZATION_USE_CASE)),
        saveMemorizationUseCase = get(named(SAVE_MEMORIZATION_RESULT_USE_CASE)),
        mapper = get(named(UI_MEM_CARD_MAPPER))
    ) }
}

val uiMapperModule: Module = module {
    single(named(UI_CARD_MAPPER)) { UICardMapper() }
    single(named(UI_MEM_CARD_MAPPER)) { UIMemCardMapper() }
}

val useCaseModule: Module = module {
    factory(named(GET_CARDS_USE_CASE)) { GetCardsUseCase(cardRepository = get()) }
    factory(named(DELETE_CARD_USE_CASE)) { DeleteCardUseCase(cardRepository = get()) }
    factory(named(SAVE_CARD_USE_CASE)) { SaveCardUseCase(cardRepository = get()) }
    factory(named(GET_CARDS_MEMORIZATION_USE_CASE)) {
        GetMemorizationUseCase<MemFact>(memorizeRepository = get())
    }
    factory(named(SAVE_MEMORIZATION_RESULT_USE_CASE)) {
        SaveMemorizationResultUseCase<MemFact>(memorizeRepository = get())
    }
}

val repositoryModule: Module = module {
    single { CardRepositoryImpl(localDataSource = get()) as CardRepository }
    single { MemorizeRepositoryImpl<MemFact>(
        memLocalDataSource = get(),
        factLocalDataSource = get()
    ) as MemorizeRepository<MemFact> }
}

val dataSourceModule: Module = module {
    single { CardLocalDataSourceImpl(
        cardDao = get(),
        mapper = get(named(LOCAL_CARD_MAPPER))
    ) as CardLocalDataSource }

    single { MemorizeLocalDataSourceImpl(
        memorizeDao = get(),
        memFactMapper = get(named(LOCAL_MEM_FACT_MAPPER)),
        attemptMapper = get(named(LOCAL_ATTEMPT_MAPPER))
    ) as MemorizeLocalDataSource }

    single { MemCardLocalDataSource(
        memCardDao = get(),
        memCardMapper = get(named(LOCAL_MEM_CARD_MAPPER)),
        filterInterpreter = sqlFilterInterpreter
    ) as FactLocalDataSource<MemCard> }
}

val localMapperModule: Module = module {
    single(named(LOCAL_CARD_MAPPER)) { LocalCardMapper() }
    single(named(LOCAL_MEM_FACT_MAPPER)) { LocalMemFactMapper() }
    single(named(LOCAL_MEM_CARD_MAPPER)) { LocalMemCardMapper() }
    single(named(LOCAL_ATTEMPT_MAPPER)) { LocalAttemptMapper() }
    single(named(LOCAL_ATTEMPT_RESULT_MAPPER)) { LocalAttemptResultMapper() }
}

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            AppDatabase::class.java, "cardography_db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                populateDB(get(), get(named(LOCAL_ATTEMPT_RESULT_MAPPER)))
            }
        }).fallbackToDestructiveMigration() // fixme
            .build()
    }

    single { get<AppDatabase>().cardDao() }
    single { get<AppDatabase>().memorizeDao() }
    single { get<AppDatabase>().memCardDao() }
}

fun populateDB(db: AppDatabase, resultMapper: LocalAttemptResultMapper) {
    GlobalScope.launch(Dispatchers.IO) {
        val list = resultMapper.map(MemorizeAttemptResult.values().toList())
        db.memorizeDao().saveAttemptResults(list)
    }
}

//fixme make all as const?
val sqlFilterInterpreter =
    object : FilterInterpreterBuilder<SupportSQLiteQuery>() {
        override fun getInterpreter() =
            SQLFilterInterpreter(createFieldMapper(), createRelationMapper())
    }.apply {
        setEntity("Card", "cards")
        setEntity("MemorizeAttempt", "attempts")
        setField("attempts", "date", "date")
        setRelation("cards", "attempts", "memId", "memId")
    }.getInterpreter()

private const val SAVE_CARD_USE_CASE = "SAVE_CARD_USE_CASE"
private const val DELETE_CARD_USE_CASE = "DELETE_CARD_USE_CASE"
private const val GET_CARDS_USE_CASE = "GET_CARDS_USE_CASE"
private const val GET_CARDS_MEMORIZATION_USE_CASE = "GET_CARDS_MEMORIZATION_USE_CASE"
private const val SAVE_MEMORIZATION_RESULT_USE_CASE = "SAVE_MEMORIZATION_RESULT_USE_CASE"

private const val UI_CARD_MAPPER = "UI_CARD_MAPPER"
private const val UI_MEM_CARD_MAPPER = "UI_MEM_CARD_MAPPER"
private const val LOCAL_CARD_MAPPER = "LOCAL_CARD_MAPPER"
private const val LOCAL_MEM_FACT_MAPPER = "LOCAL_MEM_FACT_MAPPER"
private const val LOCAL_MEM_CARD_MAPPER = "LOCAL_MEM_CARD_MAPPER"
private const val LOCAL_ATTEMPT_MAPPER = "LOCAL_ATTEMPT_MAPPER"
private const val LOCAL_ATTEMPT_RESULT_MAPPER = "LOCAL_ATTEMPT_RESULT_MAPPER"
