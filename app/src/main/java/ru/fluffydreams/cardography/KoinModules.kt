package ru.fluffydreams.cardography

import androidx.room.Room
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
import ru.fluffydreams.cardography.domain.cards.CardRepository
import ru.fluffydreams.cardography.domain.cards.interactor.EditCardUseCase
import ru.fluffydreams.cardography.domain.cards.interactor.GetCardsUseCase
import ru.fluffydreams.cardography.domain.memorize.MemorizeCardRepository
import ru.fluffydreams.cardography.domain.memorize.interactor.GetCardsMemorizationUseCase
import ru.fluffydreams.cardography.ui.cards.UICardMapper
import ru.fluffydreams.cardography.ui.cards.edit.EditCardViewModel
import ru.fluffydreams.cardography.ui.cards.list.CardsViewModel
import ru.fluffydreams.cardography.ui.memorize.MemorizeCardViewModel

val viewModelModule: Module = module {
    viewModel { CardsViewModel(getCardsUseCase = get(), mapper = get(named(UI_CARD_MAPPER))) }
    viewModel { EditCardViewModel(editCardUseCase = get(), mapper = get(named(UI_CARD_MAPPER))) }
    viewModel { MemorizeCardViewModel(
        getMemorizationUseCase = get(),
        mapper = get(named(UI_CARD_MAPPER))
    ) }
}

val uiMapperModule: Module = module {
    single(named(UI_CARD_MAPPER)) { UICardMapper() }
}

val useCaseModule: Module = module {
    factory { GetCardsUseCase(cardRepository = get()) }
    factory { EditCardUseCase(cardRepository = get()) }
    factory { GetCardsMemorizationUseCase(memorizeCardRepository = get()) }
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
        memorizeCardDao = get(),
        mapper = get(named(LOCAL_CARD_MAPPER))
    ) as MemorizeCardLocalDataSource}
}

val localMapperModule: Module = module {
    single(named(LOCAL_CARD_MAPPER)) { LocalCardMapper() }
}

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            AppDatabase::class.java, "cardography_db"
        ).build()
    }

    single { get<AppDatabase>().cardDao() }
    single { get<AppDatabase>().memorizeCardDao() }
}

private const val UI_CARD_MAPPER = "UI_CARD_MAPPER"
private const val LOCAL_CARD_MAPPER = "LOCAL_CARD_MAPPER"
