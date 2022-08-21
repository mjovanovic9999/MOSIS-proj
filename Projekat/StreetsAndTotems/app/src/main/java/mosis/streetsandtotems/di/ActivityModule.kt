package mosis.streetsandtotems.di

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
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
    fun provideNotificationFlow(): MutableStateFlow<SnackbarSettings?> {
        return MutableStateFlow(value = null)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideShowLoaderFlow(): MutableStateFlow<Boolean> {
        return MutableStateFlow(value = false)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideIsUserAuthenticated(): State<Boolean> {
        return mutableStateOf(value = Firebase.auth.currentUser != null)
    }
}