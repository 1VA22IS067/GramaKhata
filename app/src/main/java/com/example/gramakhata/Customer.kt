package com.example.gramakhata

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "customers")
data class Customer(

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    val userPhone: String,

    val name: String,

    val phone: String,

    val balance: Int,

    // NEW
    val notes: String = "",

    // NEW
    val dueDate: String = ""
)