package mosis.streetsandtotems.core.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mosis.streetsandtotems.core.domain.model.UserData
import mosis.streetsandtotems.core.domain.util.UserTypeConverters

@Database(
    entities = [UserData::class],
    version = 1
)
@TypeConverters(UserTypeConverters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract val userDao: UserDataDao
}


