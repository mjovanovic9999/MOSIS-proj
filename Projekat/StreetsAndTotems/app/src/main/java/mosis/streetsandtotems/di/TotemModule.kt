package mosis.streetsandtotems.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mosis.streetsandtotems.feature_totems.data.data_source.TotemDataSource
import mosis.streetsandtotems.feature_totems.data.repository.TotemRepositoryImpl
import mosis.streetsandtotems.feature_totems.domain.repository.TotemRepository
import mosis.streetsandtotems.feature_totems.domain.use_case.GetUsername
import mosis.streetsandtotems.feature_totems.domain.use_case.RegisterTotemCallbacks
import mosis.streetsandtotems.feature_totems.domain.use_case.RemoveTotemCallbacks
import mosis.streetsandtotems.feature_totems.domain.use_case.TotemUseCases

@Module
@InstallIn(ViewModelComponent::class)
object TotemModule {
    @Provides
    @ViewModelScoped
    fun provideTotemDataSource(db: FirebaseFirestore): TotemDataSource = TotemDataSource(db)

    @Provides
    @ViewModelScoped
    fun provideTotemRepository(totemDataSource: TotemDataSource): TotemRepository =
        TotemRepositoryImpl(totemDataSource)

    @Provides
    @ViewModelScoped
    fun provideTotemUseCases(totemRepository: TotemRepository): TotemUseCases = TotemUseCases(
        registerTotemCallbacks = RegisterTotemCallbacks(totemRepository),
        removeTotemCallbacks = RemoveTotemCallbacks(totemRepository),
        getUsername = GetUsername(totemRepository)
    )
}