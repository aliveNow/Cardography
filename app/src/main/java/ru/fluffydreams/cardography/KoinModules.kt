package ru.fluffydreams.cardography

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.fluffydreams.cardography.data.cards.CardLocalDataSource
import ru.fluffydreams.cardography.data.cards.CardRepositoryImpl
import ru.fluffydreams.cardography.datasource.local.AppDatabase
import ru.fluffydreams.cardography.datasource.local.cards.CardLocalDataSourceImpl
import ru.fluffydreams.cardography.datasource.local.cards.LocalCardMapper
import ru.fluffydreams.cardography.domain.cards.CardRepository
import ru.fluffydreams.cardography.domain.cards.interactor.AddCardUseCase
import ru.fluffydreams.cardography.domain.cards.interactor.GetCardsUseCase
import ru.fluffydreams.cardography.ui.cards.UICardMapper
import ru.fluffydreams.cardography.ui.cards.edit.AddCardViewModel
import ru.fluffydreams.cardography.ui.cards.list.CardsViewModel

val viewModelModule: Module = module {
    viewModel { CardsViewModel(getCardsUseCase = get(), mapper = get(named(UI_CARD_MAPPER))) }
    viewModel { AddCardViewModel(addCardUseCase = get(), mapper = get(named(UI_CARD_MAPPER))) }
}

val uiMapperModule: Module = module {
    single(named(UI_CARD_MAPPER)) { UICardMapper() }
}

val useCaseModule: Module = module {
    factory { GetCardsUseCase(cardRepository = get()) }
    factory { AddCardUseCase(cardRepository = get()) }
}

val repositoryModule: Module = module {
    single { CardRepositoryImpl(localDataSource = get()) as CardRepository }
}

val dataSourceModule: Module = module {
    single { CardLocalDataSourceImpl(cardDao = get(), mapper = get(named(LOCAL_CARD_MAPPER)))
            as CardLocalDataSource }
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
}

private const val UI_CARD_MAPPER = "UI_CARD_MAPPER"
private const val LOCAL_CARD_MAPPER = "LOCAL_CARD_MAPPER"
