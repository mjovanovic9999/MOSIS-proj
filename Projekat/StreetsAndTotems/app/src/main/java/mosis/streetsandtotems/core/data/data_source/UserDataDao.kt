package mosis.streetsandtotems.core.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mosis.streetsandtotems.core.domain.model.UserData

@Dao
interface UserDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserData)

    @Query("SELECT * FROM userdata WHERE id = :id")
    fun getUser(id: String): UserData?

    @Query("DELETE FROM userdata WHERE id = :id")
    fun deleteUser(id: String)
}