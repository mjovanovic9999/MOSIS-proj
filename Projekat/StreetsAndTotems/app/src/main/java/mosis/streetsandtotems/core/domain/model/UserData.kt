package mosis.streetsandtotems.core.domain.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val squadId: String?,
    val email: String,
    val imageUri: Uri,
    @PrimaryKey(autoGenerate = false) val id: String
)
