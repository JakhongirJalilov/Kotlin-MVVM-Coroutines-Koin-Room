package com.example.registeruser.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.registeruser.models.UserData

/**
 * @author jakhongirjalilov
 * @data 4/30/21
 * @project RegisterUser
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userData: UserData)
}