package mosis.streetsandtotems.feature_totems.data.repository

import android.util.Log
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FireStoreConstants
import mosis.streetsandtotems.feature_map.domain.model.TotemData
import mosis.streetsandtotems.feature_totems.data.data_source.TotemDataSource
import mosis.streetsandtotems.feature_totems.domain.repository.TotemRepository

class TotemRepositoryImpl(private val totemDataSource: TotemDataSource) : TotemRepository {
    private var totemCallbacksRegistrationListener: ListenerRegistration? = null

    override fun registerCallbacksOnTotemDataUpdate(
        totemAdded: (TotemData) -> Unit,
        totemModified: (TotemData) -> Unit,
        totemDeleted: (TotemData) -> Unit
    ) {
        totemCallbacksRegistrationListener = totemDataSource.registerCallbacksOnTotemDataUpdate(
            totemAdded, totemModified, totemDeleted
        )
    }

    override fun removeCallbacksOnTotemDataUpdate() {
        totemCallbacksRegistrationListener?.remove()
        totemCallbacksRegistrationListener = null
    }

    override suspend fun getUsername(id: String): String {
        return try {
            totemDataSource.getUsername(id).await().firstOrNull()
                ?.getString(FireStoreConstants.USER_NAME_FIELD) ?: ""
        } catch (e: Exception) {
            Log.d("tag", "Username error")
            ""
        }
    }
}