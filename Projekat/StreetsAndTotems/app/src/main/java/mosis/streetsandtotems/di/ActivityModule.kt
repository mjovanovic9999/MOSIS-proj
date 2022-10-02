package mosis.streetsandtotems.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.di.util.StateFlowWrapper
import mosis.streetsandtotems.services.NetworkManager

@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {
    @Provides
    @ActivityRetainedScoped
    fun provideNetworkManager(app: Application): NetworkManager {
        return NetworkManager(app)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideSnackbarMutableFLow(): MutableStateFlow<SnackbarSettings?> {
        return MutableStateFlow(null)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideSnackbarFlow(snackbarMutableFlow: MutableStateFlow<SnackbarSettings?>): StateFlowWrapper<SnackbarSettings?> =
        StateFlowWrapper(snackbarMutableFlow)

    @Provides
    @ActivityRetainedScoped
    fun provideShowLoaderMutableFlow(): MutableStateFlow<Boolean> {
        return MutableStateFlow(false)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideShowLoaderFlow(showLoaderMutableFlow: MutableStateFlow<Boolean>): StateFlowWrapper<Boolean> =
        StateFlowWrapper(showLoaderMutableFlow)

}