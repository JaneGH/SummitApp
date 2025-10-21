package com.example.summitapp.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.summitapp.model.data.User


@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE emailId = :email LIMIT 1")
    fun getUserByEmail(email: String): User?
}