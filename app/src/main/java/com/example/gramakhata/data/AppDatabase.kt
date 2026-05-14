package com.example.gramakhata.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gramakhata.Customer
import com.example.gramakhata.Transaction

@Database(
    entities = [
        User::class,
        Customer::class,
        Transaction::class
    ],

    version = 3
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun customerDao(): CustomerDao

    abstract fun transactionDao(): TransactionDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "grama_khata_db"
                )

                    .fallbackToDestructiveMigration()

                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}