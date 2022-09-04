package mosis.streetsandtotems.di

import android.app.Application
import androidx.room.Room
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
import mosis.streetsandtotems.core.DatabaseConstants
import mosis.streetsandtotems.core.data.data_source.LocalDatabase
import mosis.streetsandtotems.core.data.repository.UserRepositoryImpl
import mosis.streetsandtotems.core.domain.repository.UserRepository
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.di.util.SharedFlowWrapper
import mosis.streetsandtotems.feature_settings_persistence.PreferencesDataStore
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
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideLocalDatabase(app: Application): LocalDatabase =
        Room.databaseBuilder(
            app,
            LocalDatabase::class.java,
            DatabaseConstants.DATABASE_NAME
        )
            .build()

    @Provides
    @Singleton
    fun provideUserRepository(db: LocalDatabase): UserRepository =
        UserRepositoryImpl(db.userDao)

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