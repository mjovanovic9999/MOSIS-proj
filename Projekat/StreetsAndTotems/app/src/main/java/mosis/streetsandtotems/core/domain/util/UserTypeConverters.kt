package mosis.streetsandtotems.core.domain.util

import android.net.Uri
import androidx.room.TypeConverter
import java.time.LocalDateTime

object UserTypeConverters {
    @TypeConverter
    @JvmStatic
    fun toDate(dateString: String): LocalDateTime {
        return LocalDateTime.parse(dateString)
    }

    @TypeConverter
    @JvmStatic
    fun fromDate(date: LocalDateTime): String {
        return date.toString();
    }

    @TypeConverter
    @JvmStatic
    fun toUri(uriString: String): Uri {
        return Uri.parse(uriString)
    }

    @TypeConverter
    @JvmStatic
    fun fromUri(uri: Uri): String {
        return uri.toString()
    }
}