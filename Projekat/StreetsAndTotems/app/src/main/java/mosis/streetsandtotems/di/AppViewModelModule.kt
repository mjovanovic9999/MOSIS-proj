package mosis.streetsandtotems.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideLocationBroadcastReceiver(): LocationBroadcastReceiver {
        return LocationBroadcastReceiver()
    }

    @Provides
    @ViewModelScoped
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth


    @Provides
    @ViewModelScoped
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

}