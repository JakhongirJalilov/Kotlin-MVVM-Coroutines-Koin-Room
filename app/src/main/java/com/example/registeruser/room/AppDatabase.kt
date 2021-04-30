package com.example.registeruser.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.registeruser.models.UserData

/**
 * @author jakhongirjalilov
 * @data 4/30/21
 * @project RegisterUser
 */
@Database(
    entities = [
        UserData::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        var database: AppDatabase? = null
        fun init(context: Context) {
            database = Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "app_db"
            ).build()
        }
    }
}