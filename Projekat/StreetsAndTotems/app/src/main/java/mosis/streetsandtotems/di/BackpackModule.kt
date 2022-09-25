package mosis.streetsandtotems.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.feature_backpack.data.data_source.FirebaseBackpackDataSource
import mosis.streetsandtotems.feature_backpack.data.repository.BackpackRepositoryImpl
import mosis.streetsandtotems.feature_backpack.domain.repository.BackpackRepository


@Module
@InstallIn(ViewModelComponent::class)
object BackpackModule {
    @Provides
    @ViewModelScoped
    fun provideFirebaseBackpackDataSource(db: FirebaseFirestore): FirebaseBackpackDataSource =
        FirebaseBackpackDataSource(db)

    @Provides
    @ViewModelScoped
    fun provideBackpackViewModelRepository(
        preferenceDataSource: PreferencesDataStore,
        firebaseBackpackDataSource: FirebaseBackpackDataSource
    ): BackpackRepository = BackpackRepositoryImpl(
        preferenceDataSource = preferenceDataSource,
        firebaseBackpackDataSource = firebaseBackpackDataSource
    )
}