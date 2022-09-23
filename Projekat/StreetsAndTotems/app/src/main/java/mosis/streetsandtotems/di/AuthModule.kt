package mosis.streetsandtotems.di

import android.app.Application
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.DINameConstants
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.core.data.data_source.UserOnlineStatusDataSource
import mosis.streetsandtotems.feature_auth.data.data_source.FirebaseAuthDataSource
import mosis.streetsandtotems.feature_auth.data.data_source.FirebaseStorageDataSource
import mosis.streetsandtotems.feature_auth.data.data_source.FirestoreAuthDataSource
import mosis.streetsandtotems.feature_auth.data.data_source.OneTapGoogleDataSource
import mosis.streetsandtotems.feature_auth.data.repository.AuthRepositoryImpl
import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository
import mosis.streetsandtotems.feature_auth.domain.use_case.*
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {
    @Provides
    @ViewModelScoped
    fun provideFirestoreAuthDataSource(db: FirebaseFirestore): FirestoreAuthDataSource =
        FirestoreAuthDataSource(db)

    @Provides
    @ViewModelScoped
    fun provideFirebaseDataSource(firebaseAuth: FirebaseAuth): FirebaseAuthDataSource =
        FirebaseAuthDataSource(firebaseAuth = firebaseAuth)

    @Provides
    @ViewModelScoped
    fun provideOneTapGoogleDataSource(
        oneTapClient: SignInClient,
        @Named(DINameConstants.SIGN_IN_REQUEST) signInRequest: BeginSignInRequest,
        @Named(DINameConstants.SIGN_UP_REQUEST) signUpRequest: BeginSignInRequest
    ): OneTapGoogleDataSource = OneTapGoogleDataSource(
        oneTapClient = oneTapClient, signInRequest = signInRequest, signUpRequest = signUpRequest
    )


    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        authDataSource: FirebaseAuthDataSource,
        oneTapGoogleDataSource: OneTapGoogleDataSource,
        preferencesDataStore: PreferencesDataStore,
        firestoreAuthDataSource: FirestoreAuthDataSource,
        userOnlineStatusDataSource: UserOnlineStatusDataSource,
        firebaseStorageDataSource: FirebaseStorageDataSource
    ): AuthRepository = AuthRepositoryImpl(
        authDataSource = authDataSource,
        oneTapGoogleDataSource = oneTapGoogleDataSource,
        preferencesDataStore = preferencesDataStore,
        firestoreAuthDataSource = firestoreAuthDataSource,
        userOnlineStatusDataSource,
        firebaseStorageDataSource = firebaseStorageDataSource
    )

    @Provides
    @ViewModelScoped
    fun provideAuthUseCases(authRepository: AuthRepository): AuthUseCases = AuthUseCases(
        emailAndPasswordSignIn = EmailAndPasswordSignIn(repository = authRepository),
        signOut = SignOut(repository = authRepository),
        isUserAuthenticated = IsUserAuthenticated(repository = authRepository),
        emailAndPasswordSignUp = EmailAndPasswordSignUp(repository = authRepository),
        oneTapSignInWithGoogle = OneTapSignInWithGoogle(repository = authRepository),
        signInWithGoogle = SignInWithGoogle(repository = authRepository),
        sendVerificationEmail = SendVerificationEmail(repository = authRepository),
        sendRecoveryEmail = SendRecoveryEmail(repository = authRepository),
        isUserAlreadyLoggedIn = IsUserAlreadyLoggedIn(repository = authRepository),
        isUserEmailVerified = IsUserEmailVerified(repository = authRepository),
        oneTapSignUpWithGoogle = OneTapSignUpWithGoogle(repository = authRepository),
        updatePassword = UpdatePassword(repository = authRepository),
        updateUserProfile = UpdateUserProfile(repository = authRepository)
    )

    @Provides
    @ViewModelScoped
    fun provideOneTapClient(context: Application): SignInClient =
        Identity.getSignInClient(context.applicationContext)

    @Provides
    @ViewModelScoped
    @Named(DINameConstants.SIGN_IN_REQUEST)
    fun provideSignInRequest(app: Application): BeginSignInRequest =
        BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(true).build()
        ).setAutoSelectEnabled(true).build()

    @Provides
    @ViewModelScoped
    @Named(DINameConstants.SIGN_UP_REQUEST)
    fun provideSignUpRequest(app: Application): BeginSignInRequest =
        BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false).build()
        ).build()

    @Provides
    @ViewModelScoped
    fun provideGoogleSignInOptions(app: Application): GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(app.getString(R.string.web_client_id)).requestEmail().build()

    @Provides
    @ViewModelScoped
    fun provideGoogleSignInClient(
        app: Application, options: GoogleSignInOptions
    ): GoogleSignInClient = GoogleSignIn.getClient(app, options)

    @Provides
    @ViewModelScoped
    fun provideFirebaseStorageDataSource(storage: FirebaseStorage): FirebaseStorageDataSource =
        FirebaseStorageDataSource(storage)
}