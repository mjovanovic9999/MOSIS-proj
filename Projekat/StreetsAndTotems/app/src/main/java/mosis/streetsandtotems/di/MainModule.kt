package mosis.streetsandtotems.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {
    @Provides
    @ViewModelScoped
    fun provideLocationBroadcastReceiver(): LocationBroadcastReceiver {
        return LocationBroadcastReceiver()
    }

}