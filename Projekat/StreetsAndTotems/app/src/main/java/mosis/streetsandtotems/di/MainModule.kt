package mosis.streetsandtotems.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import mosis.streetsandtotems.core.domain.repository.UserOnlineStatusRepository
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import mosis.streetsandtotems.feature_main.data.repository.MainRepositoryImpl
import mosis.streetsandtotems.feature_main.domain.repository.MainRepository
import mosis.streetsandtotems.feature_main.domain.use_case.LeaveSquad
import mosis.streetsandtotems.feature_main.domain.use_case.MainUseCases

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
    ): MainRepository = MainRepositoryImpl()

    @Provides
    @ViewModelScoped
    fun provideMainUseCases(userOnlineStatusRepository: UserOnlineStatusRepository): MainUseCases =
        MainUseCases(
            leaveSquad = LeaveSquad()
        )
}