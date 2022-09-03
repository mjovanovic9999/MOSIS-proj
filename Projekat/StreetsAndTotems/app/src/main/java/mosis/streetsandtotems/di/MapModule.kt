package mosis.streetsandtotems.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource
import mosis.streetsandtotems.feature_map.data.repository.MapViewModelRepositoryImpl
import mosis.streetsandtotems.feature_map.domain.repository.MapViewModelRepository

@Module
@InstallIn(ViewModelComponent::class)
object MapModule {
    @Provides
    @ViewModelScoped
    fun provideFirebaseMapDataSource(db: FirebaseFirestore): FirebaseMapDataSource =
        FirebaseMapDataSource(db)

    @Provides
    @ViewModelScoped
    fun provideMapViewModelRepository(
        auth: FirebaseAuth,
        firebaseMapDataSource: FirebaseMapDataSource
    ): MapViewModelRepository =
        MapViewModelRepositoryImpl(auth = auth, firebaseMapDataSource = firebaseMapDataSource)
}