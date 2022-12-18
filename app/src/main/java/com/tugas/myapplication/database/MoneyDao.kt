package com.tugas.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoneyDao {

    @Insert
    fun insert(money: Money)

    @Update
    fun update(money: Money)

    @Delete
    fun delete(money: Money)

    @Query("SELECT * FROM money ORDER BY id ASC")
    fun getAllData(): LiveData<List<Money>>

    @Query("SELECT SUM(income) FROM money")
    fun getIncome(): LiveData<Int>

    @Query("SELECT SUM(outcome) FROM money")
    fun getOutcome(): LiveData<Int>

    @Query("SELECT SUM(income) - SUM(outcome) from money")
    fun getTotalMoney(): LiveData<Int>
}