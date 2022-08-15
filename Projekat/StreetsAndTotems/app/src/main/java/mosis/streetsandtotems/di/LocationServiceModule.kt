package mosis.streetsandtotems.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import mosis.streetsandtotems.services.NetworkManager

@Module
@InstallIn(ServiceComponent::class)
object LocationServiceModule {
    //Network manager
    @Provides
    @ServiceScoped
    fun provideNetworkManager(app: Application): NetworkManager {
        return NetworkManager(app)
    }
}