package com.oxymium.realestatemanager.di

import com.oxymium.realestatemanager.database.AppDatabase
import com.oxymium.realestatemanager.database.agent.AgentRepository
import com.oxymium.realestatemanager.database.agent.AgentRoomImpl
import com.oxymium.realestatemanager.database.estate.EstateRepository
import com.oxymium.realestatemanager.database.estate.EstateRoomImpl
import com.oxymium.realestatemanager.database.picture.PictureRepository
import com.oxymium.realestatemanager.database.picture.PictureRoomImpl
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.viewmodel.DevViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.MapSelectedViewModel
import com.oxymium.realestatemanager.viewmodel.ToolsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    // Database
    single { AppDatabase.getDatabase(androidContext()) }

    // EstateDao
    single { get<AppDatabase>().estateDao() }

    // PictureDao
    single { get<AppDatabase>().pictureDao() }

    // AgentDao
    single { get<AppDatabase>().agentDao() }

}

val repositoryModules = module {
    single<EstateRepository> { EstateRoomImpl(estateDao = get()) }
    single<PictureRepository> { PictureRoomImpl(pictureDao = get()) }
    single<AgentRepository> { AgentRoomImpl(agentDao = get()) }
}

val viewModelModules = module {
    viewModel<EstateViewModel> { EstateViewModel(get(), get(), get()) }
    viewModel<CreateViewModel> { CreateViewModel(get(), get(), get()) }
    viewModel<ToolsViewModel> { ToolsViewModel() }
    viewModel<DevViewModel> { DevViewModel(get(), get()) }
    viewModel<MapSelectedViewModel> { MapSelectedViewModel(get()) }
}

