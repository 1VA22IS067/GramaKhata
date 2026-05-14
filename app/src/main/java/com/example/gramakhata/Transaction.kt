package com.example.gramakhata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userPhone: String,

    val customerId: String,

    val amount: Int,

    val type: String,

    // NEW FIELD
    val dateTime: String
)