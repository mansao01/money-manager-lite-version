package com.tugas.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Money::class], version = 1)
abstract class MoneyRoomDatabase : RoomDatabase() {
    abstract fun moneyDao(): MoneyDao

    companion object {
        @Volatile
        private var INSTANCE: MoneyRoomDatabase? = null


        @JvmStatic
        fun getDatabase(context: Context): MoneyRoomDatabase {
            if (INSTANCE == null) {
                synchronized(MoneyRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MoneyRoomDatabase::class.java, "money_database"
                    ).build()
                }
            }
            return INSTANCE as MoneyRoomDatabase
        }
    }
}