package com.example.gramakhata.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query(
        "SELECT * FROM users WHERE phone = :phone AND password = :password"
    )
    suspend fun login(
        phone: String,
        password: String
    ): User?

    @Query(
        "SELECT * FROM users WHERE phone = :phone"
    )
    suspend fun getUserByPhone(
        phone: String
    ): User?
}