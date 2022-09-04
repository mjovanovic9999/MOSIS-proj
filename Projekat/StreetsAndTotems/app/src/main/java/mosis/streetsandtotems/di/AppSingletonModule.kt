package mosis.streetsandtotems.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.core.data.repository.PreferenceRepositoryImpl
import mosis.streetsandtotems.core.domain.repository.PreferenceRepository
import mosis.streetsandtotems.core.domain.use_case.*
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.di.util.SharedFlowWrapper
import mosis.streetsandtotems.services.LocationServiceControlEvents
import mosis.streetsandtotems.services.LocationServiceEvents
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppSingletonModule {
    @Provides
    @Singleton
    fun provideNotificationService(app: Application): NotificationProvider {
        return NotificationProvider(app)
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(app: Application): PreferencesDataStore {
        return PreferencesDataStore(app)
    }

    @Provides
    @Singleton
    fun providePreferenceRepository(preferencesDataStore: PreferencesDataStore): PreferenceRepository {
        return PreferenceRepositoryImpl(preferencesDataStore)
    }

    @Provides
    @Singleton
    fun providePreferenceUseCases(preferenceRepository: PreferenceRepository): PreferenceUseCases {
        return PreferenceUseCases(
            GetUserSettings(preferenceRepository),
            UpdateUserSettings(preferenceRepository),
            GetAuthProvider(preferenceRepository),
            UpdateAuthProvider(preferenceRepository),
            GetUserSettingsFlow(preferenceRepository),
            SetSquadId(preferenceRepository),
            SetUserId(preferenceRepository),
            GetUserId(preferenceRepository),
            GetSquadId(preferenceRepository),
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideLocationServiceEventsMutableFlow(): MutableSharedFlow<LocationServiceEvents> =
        MutableSharedFlow()

    @Provides
    @Singleton
    fun provideLocationServiceControlEventsMutableFlow(): MutableSharedFlow<LocationServiceControlEvents> =
        MutableSharedFlow()

    @Provides
    @Singleton
    fun provideLocationServiceEventFlow(locationServiceEventsMutableFlow: MutableSharedFlow<LocationServiceEvents>): SharedFlowWrapper<LocationServiceEvents> =
        SharedFlowWrapper(locationServiceEventsMutableFlow)

    @Provides
    @Singleton
    fun provideLocationServiceControlEventsFlow(locationServiceControlEventsMutableFlow: MutableSharedFlow<LocationServiceControlEvents>): SharedFlowWrapper<LocationServiceControlEvents> =
        SharedFlowWrapper(locationServiceControlEventsMutableFlow)

    @Provides
    @Singleton
    fun provideLocationStateMutableFlow(): MutableSharedFlow<Boolean> =
        MutableSharedFlow(replay = 1)

    @Provides
    @Singleton
    fun provideLocationStateFlow(locationStateMutableFlow: MutableSharedFlow<Boolean>): SharedFlowWrapper<Boolean> =
        SharedFlowWrapper(locationStateMutableFlow)
}