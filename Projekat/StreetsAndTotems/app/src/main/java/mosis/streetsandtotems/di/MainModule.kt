package mosis.streetsandtotems.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.core.domain.repository.UserOnlineStatusRepository
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import mosis.streetsandtotems.feature_main.data.repository.MainRepositoryImpl
import mosis.streetsandtotems.feature_main.domain.repository.MainRepository
import mosis.streetsandtotems.feature_main.domain.use_case.LeaveSquad
import mosis.streetsandtotems.feature_main.domain.use_case.MainUseCases
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {
    @Provides
    @ViewModelScoped
    fun provideLocationBroadcastReceiver(locationStateMutableFlow: MutableSharedFlow<Boolean>): LocationBroadcastReceiver {
        return LocationBroadcastReceiver(locationStateMutableFlow)
    }

    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        preferencesDataStore: PreferencesDataStore, firebaseMapDataSource: FirebaseMapDataSource
    ): MainRepository = MainRepositoryImpl(firebaseMapDataSource, preferencesDataStore)

    @Provides
    @ViewModelScoped
    fun provideMainUseCases(mainRepository: MainRepository): MainUseCases = MainUseCases(
        leaveSquad = LeaveSquad(mainRepository)
    )
}