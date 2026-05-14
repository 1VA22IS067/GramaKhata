package com.example.gramakhata.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gramakhata.Transaction

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(
        transaction: Transaction
    )

    @Query("SELECT * FROM transactions WHERE userPhone = :userPhone")
    suspend fun getAllTransactions(
        userPhone: String
    ): List<Transaction>
}