package mosis.streetsandtotems.feature_auth.data.data_source

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import mosis.streetsandtotems.core.FirebaseStorageConstants

class FirebaseStorageDataSource(private val storage: FirebaseStorage) {
    fun storeProfileImage(userId: String, bytes: ByteArray): UploadTask {
        val userImageRef = getUserImageRef(userId)
        return userImageRef.putBytes(bytes)
    }

    fun getDownloadUrl(userId: String): Task<Uri> {
        val userImageRef = getUserImageRef(userId)
        return userImageRef.downloadUrl
    }

    fun removeProfileImage(userId: String){
        val userImageRef = getUserImageRef(userId)

    }

    private fun getUserImageRef(userId: String) =
        storage.reference.child("${FirebaseStorageConstants.IMAGES}/$userId")
}