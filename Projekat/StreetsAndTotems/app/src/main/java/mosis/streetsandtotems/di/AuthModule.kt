package mosis.streetsandtotems.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mosis.streetsandtotems.feature_auth.data.data_source.FirebaseAuthDataSource
import mosis.streetsandtotems.feature_auth.data.repository.AuthRepositoryImpl
import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository
import mosis.streetsandtotems.feature_auth.domain.use_case.*

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    @ViewModelScoped
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth


    @Provides
    @ViewModelScoped
    fun provideFirebaseDataSource(firebaseAuth: FirebaseAuth): FirebaseAuthDataSource =
        FirebaseAuthDataSource(firebaseAuth = firebaseAuth)


    @Provides
    @ViewModelScoped
    fun provideAuthRepository(authDataSource: FirebaseAuthDataSource): AuthRepository =
        AuthRepositoryImpl(authDataSource = authDataSource)

    @Provides
    @ViewModelScoped
    fun provideAuthUseCases(authRepository: AuthRepository) =
        AuthUseCases(
            emailAndPasswordSignIn = EmailAndPasswordSignIn(authRepository),
            signOut = SignOut(authRepository),
            isUserAuthenticated = IsUserAuthenticated(authRepository),
            emailAndPasswordSignUp = EmailAndPasswordSignUp(authRepository)
        )

}