package mosis.streetsandtotems.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
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
    fun provideMainUseCases(): MainUseCases = MainUseCases(leaveSquad = LeaveSquad())
}