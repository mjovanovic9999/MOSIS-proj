package mosis.streetsandtotems.feature_auth.data.data_source

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import javax.inject.Inject


class FirebaseAuthDataSource @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    fun emailAndPasswordSignUp(email: String, password: String): Task<AuthResult> =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun emailAndPasswordSignIn(email: String, password: String): Task<AuthResult> =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    fun signOut() = firebaseAuth.signOut()

    fun signInWithCredential(credentials: AuthCredential): Task<AuthResult> =
        firebaseAuth.signInWithCredential(credentials)

    fun updateProfile(imageUri: Uri?, displayName: String): Task<Void>? =
        getCurrentUser()?.updateProfile(
            UserProfileChangeRequest.Builder().setDisplayName(displayName)
                .setPhotoUri(imageUri).build()
        )

    fun updateEmail(email: String): Task<Void>? = getCurrentUser()?.verifyBeforeUpdateEmail(email)

}