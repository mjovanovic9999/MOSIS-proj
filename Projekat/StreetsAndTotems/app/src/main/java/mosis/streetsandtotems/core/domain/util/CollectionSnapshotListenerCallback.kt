package mosis.streetsandtotems.core.domain.util

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject

inline fun <reified T> collectionSnapshotListenerCallback(
    e: FirebaseFirestoreException?,
    snapshots: QuerySnapshot?,
    noinline documentAddedCallback: ((document: T) -> Unit)?,
    noinline documentModifiedCallback: ((document: T) -> Unit)?,
    noinline documentRemovedCallback: ((document: T) -> Unit)?,
    customConversion: (document: QueryDocumentSnapshot) -> T? = { it.toObject<T>() }
) {
    if (e != null) {
        Log.w("tag", "listen:error", e)
        return
    }

    for (dc in snapshots!!.documentChanges) {
        val convertedSnapshot = customConversion(dc.document)
        if (convertedSnapshot != null) when (dc.type) {
            DocumentChange.Type.ADDED -> documentAddedCallback?.invoke(convertedSnapshot)
            DocumentChange.Type.MODIFIED -> documentModifiedCallback?.invoke(convertedSnapshot)
            DocumentChange.Type.REMOVED -> documentRemovedCallback?.invoke(convertedSnapshot)
        }
    }
}