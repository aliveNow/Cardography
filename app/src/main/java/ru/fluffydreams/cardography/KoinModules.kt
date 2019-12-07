package ru.fluffydreams.cardography

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.fluffydreams.cardography.data.cards.CardLocalDataSource
import ru.fluffydreams.cardography.data.cards.CardRepositoryImpl
import ru.fluffydreams.cardography.datasource.local.AppDatabase
import ru.fluffydreams.cardography.datasource.local.cards.CardLocalDataSourceImpl
import ru.fluffydreams.cardography.domain.cards.CardRepository
import ru.fluffydreams.cardography.domain.cards.interactor.GetCardsUseCase
import ru.fluffydreams.cardography.ui.cards.list.CardsViewModel

val viewModelModule: Module = module {
    viewModel { CardsViewModel(getCardsUseCase = get()) }
}

val useCaseModule: Module = module {
    factory { GetCardsUseCase(cardRepository = get()) }
}

val repositoryModule: Module = module {
    single { CardRepositoryImpl(localDataSource = get()) as CardRepository }
}

val dataSourceModule: Module = module {
    single { CardLocalDataSourceImpl(cardDao = get()) as CardLocalDataSource }
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

