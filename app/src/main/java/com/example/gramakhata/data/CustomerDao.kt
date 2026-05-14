package com.example.gramakhata.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gramakhata.Customer

@Dao
interface CustomerDao {

    @Insert
    suspend fun insertCustomer(
        customer: Customer
    )

    @Query("SELECT * FROM customers WHERE userPhone = :userPhone")
    suspend fun getAllCustomers(
        userPhone: String
    ): List<Customer>

    @Update
    suspend fun updateCustomer(
        customer: Customer
    )

    @Delete
    suspend fun deleteCustomer(
        customer: Customer
    )
}