package mosis.streetsandtotems.feature_auth.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

}