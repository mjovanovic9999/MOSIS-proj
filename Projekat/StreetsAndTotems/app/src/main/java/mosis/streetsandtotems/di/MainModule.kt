package mosis.streetsandtotems.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {
    @Provides
    @ViewModelScoped
    fun provideLocationBroadcastReceiver(locationStateMutableFlow: MutableSharedFlow<Boolean>): LocationBroadcastReceiver {
        return LocationBroadcastReceiver(locationStateMutableFlow)
    }
}