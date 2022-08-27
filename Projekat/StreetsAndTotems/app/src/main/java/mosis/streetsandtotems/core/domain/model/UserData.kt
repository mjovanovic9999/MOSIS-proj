package mosis.streetsandtotems.core.domain.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class UserData(
    val firstName: String,
    val lastName: String,
    val username: String,
    val phoneNumber: String,
    val squadId: String?,
    val email: String,
    val imageUri: Uri,
    @ColumnInfo(name = "last_change")
    val lastChange: LocalDateTime,
    @PrimaryKey(autoGenerate = false) val id: String
)
