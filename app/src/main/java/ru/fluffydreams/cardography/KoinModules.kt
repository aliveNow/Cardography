package ru.fluffydreams.cardography

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.fluffydreams.cardography.data.cards.CardLocalDataSource
import ru.fluffydreams.cardography.data.cards.CardRepositoryImpl
import ru.fluffydreams.cardography.data.memorize.MemorizeCardLocalDataSource
import ru.fluffydreams.cardography.data.memorize.MemorizeCardRepositoryImpl
import ru.fluffydreams.cardography.datasource.local.AppDatabase
import ru.fluffydreams.cardography.datasource.local.cards.CardLocalDataSourceImpl
import ru.fluffydreams.cardography.datasource.local.cards.LocalCardMapper
import ru.fluffydreams.cardography.datasource.local.memorize.MemorizeCardLocalDataSourceImpl
import ru.fluffydreams.cardography.datasource.local.memorize.mapper.LocalAttemptMapper
import ru.fluffydreams.cardography.datasource.local.memorize.mapper.LocalAttemptResultMapper
import ru.fluffydreams.cardography.domain.cards.CardRepository
import ru.fluffydreams.cardography.domain.cards.interactor.EditCardUseCase
import ru.fluffydreams.cardography.domain.cards.interactor.GetCardsUseCase
import ru.fluffydreams.cardography.domain.memorize.MemorizeCardRepository
import ru.fluffydreams.cardography.domain.memorize.interactor.GetCardsMemorizationUseCase
import ru.fluffydreams.cardography.domain.memorize.interactor.SaveMemorizationResultUseCase
import ru.fluffydreams.cardography.domain.memorize.model.MemorizeAttemptResult
import ru.fluffydreams.cardography.ui.cards.UICardMapper
import ru.fluffydreams.cardography.ui.cards.edit.EditCardViewModel
import ru.fluffydreams.cardography.ui.cards.list.CardsViewModel
import ru.fluffydreams.cardography.ui.memorize.MemorizeCardViewModel

val viewModelModule: Module = module {
    viewModel { CardsViewModel(
        getCardsUseCase = get(named(GET_CARDS_USE_CASE)),
        mapper = get(named(UI_CARD_MAPPER))
    ) }
    viewModel { EditCardViewModel(
        editCardUseCase = get(named(EDIT_CARD_USE_CASE)),
        mapper = get(named(UI_CARD_MAPPER))
    ) }
    viewModel { MemorizeCardViewModel(
        getMemorizationUseCase = get(named(GET_CARDS_MEMORIZATION_USE_CASE)),
        saveMemorizationUseCase = get(named(SAVE_MEMORIZATION_RESULT_USE_CASE)),
        mapper = get(named(UI_CARD_MAPPER))
    ) }
}

val uiMapperModule: Module = module {
    single(named(UI_CARD_MAPPER)) { UICardMapper() }
}

val useCaseModule: Module = module {
    factory(named(GET_CARDS_USE_CASE)) { GetCardsUseCase(cardRepository = get()) }
    factory(named(EDIT_CARD_USE_CASE)) { EditCardUseCase(cardRepository = get()) }
    factory(named(GET_CARDS_MEMORIZATION_USE_CASE)) {
        GetCardsMemorizationUseCase(memorizeCardRepository = get())
    }
    factory(named(SAVE_MEMORIZATION_RESULT_USE_CASE)) {
        SaveMemorizationResultUseCase(memorizeCardRepository = get())
    }
}

val repositoryModule: Module = module {
    single { CardRepositoryImpl(localDataSource = get()) as CardRepository }
    single { MemorizeCardRepositoryImpl(localDataSource = get()) as MemorizeCardRepository }
}

val dataSourceModule: Module = module {
    single { CardLocalDataSourceImpl(
        cardDao = get(),
        mapper = get(named(LOCAL_CARD_MAPPER))
    ) as CardLocalDataSource }

    single { MemorizeCardLocalDataSourceImpl(
        cardDao = get(),
        memorizeCardDao = get(),
        cardMapper = get(named(LOCAL_CARD_MAPPER)),
        attemptMapper = get(named(LOCAL_ATTEMPT_MAPPER))
    ) as MemorizeCardLocalDataSource}
}

val localMapperModule: Module = module {
    single(named(LOCAL_CARD_MAPPER)) { LocalCardMapper() }
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
        }).build()
    }

    single { get<AppDatabase>().cardDao() }
    single { get<AppDatabase>().memorizeCardDao() }
}

fun populateDB(db: AppDatabase, resultMapper: LocalAttemptResultMapper) {
    GlobalScope.launch(Dispatchers.IO) {
        val list = resultMapper.map(MemorizeAttemptResult.values().toList())
        db.memorizeCardDao().saveAttemptResults(list)
    }
}

private const val EDIT_CARD_USE_CASE = "EDIT_CARD_USE_CASE"
private const val GET_CARDS_USE_CASE = "GET_CARDS_USE_CASE"
private const val GET_CARDS_MEMORIZATION_USE_CASE = "GET_CARDS_MEMORIZATION_USE_CASE"
private const val SAVE_MEMORIZATION_RESULT_USE_CASE = "SAVE_MEMORIZATION_RESULT_USE_CASE"

private const val UI_CARD_MAPPER = "UI_CARD_MAPPER"
private const val LOCAL_CARD_MAPPER = "LOCAL_CARD_MAPPER"
private const val LOCAL_ATTEMPT_MAPPER = "LOCAL_ATTEMPT_MAPPER"
private const val LOCAL_ATTEMPT_RESULT_MAPPER = "LOCAL_ATTEMPT_RESULT_MAPPER"
