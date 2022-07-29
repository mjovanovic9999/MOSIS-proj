package mosis.streetsandtotems.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mosis.streetsandtotems.feature_auth.data.data_source.FirebaseAuthDataSource
import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository
import mosis.streetsandtotems.feature_auth.domain.use_case.AuthUseCases
import mosis.streetsandtotems.feature_auth.domain.use_case.EmailAndPasswordSignIn
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    //Repositories

    //Data Sources
    @Provides
    @Singleton
    fun provideFirebaseDataSource(firebaseAuth: FirebaseAuth): FirebaseAuthDataSource =
        FirebaseAuthDataSource(firebaseAuth)

    //Use Cases
    @Provides
    @Singleton
    fun proviceAuthUseCases(authRepository: AuthRepository) = AuthUseCases(emailAndPasswoedSignIn = EmailAndPasswordSignIn(authRepository))
}