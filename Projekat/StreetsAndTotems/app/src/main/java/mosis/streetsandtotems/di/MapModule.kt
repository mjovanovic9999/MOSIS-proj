package mosis.streetsandtotems.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource
import mosis.streetsandtotems.feature_map.data.repository.MapViewModelRepositoryImpl
import mosis.streetsandtotems.feature_map.domain.repository.MapViewModelRepository
import mosis.streetsandtotems.feature_map.domain.use_case.MapUseCases
import mosis.streetsandtotems.feature_map.domain.use_case.SearchResources
import mosis.streetsandtotems.feature_map.domain.use_case.SearchUsers

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
        preferenceDataSource: PreferencesDataStore, firebaseMapDataSource: FirebaseMapDataSource
    ): MapViewModelRepository = MapViewModelRepositoryImpl(
        preferenceDataSource = preferenceDataSource, firebaseMapDataSource = firebaseMapDataSource
    )

    @Provides
    @ViewModelScoped
    fun provideMapUseCases(mapViewModelRepository: MapViewModelRepository): MapUseCases =
        MapUseCases(
            searchUsers = SearchUsers(mapViewModelRepository),
            searchResources = SearchResources(mapViewModelRepository)
        )
}