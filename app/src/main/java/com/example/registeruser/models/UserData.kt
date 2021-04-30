package com.example.registeruser.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author jakhongirjalilov
 * @data 4/30/21
 * @project RegisterUser
 */

@Entity(
    tableName = "users"
)
data class UserData(
    @PrimaryKey
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val userStatus: Int
)