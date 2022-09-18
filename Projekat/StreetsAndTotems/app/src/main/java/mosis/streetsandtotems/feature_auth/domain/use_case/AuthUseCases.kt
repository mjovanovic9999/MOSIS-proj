package mosis.streetsandtotems.feature_auth.domain.use_case

data class AuthUseCases(
    val emailAndPasswordSignIn: EmailAndPasswordSignIn,
    val signOut: SignOut,
    val isUserAuthenticated: IsUserAuthenticated,
    val emailAndPasswordSignUp: EmailAndPasswordSignUp,
    val oneTapSignInWithGoogle: OneTapSignInWithGoogle,
    val signInWithGoogle: SignInWithGoogle,
    val sendVerificationEmail: SendVerificationEmail,
    val sendRecoveryEmail: SendRecoveryEmail
)