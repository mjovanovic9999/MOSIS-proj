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
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
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
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore
}