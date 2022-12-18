package com.tugas.myapplication.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.tugas.myapplication.database.Money
import com.tugas.myapplication.database.MoneyDao
import com.tugas.myapplication.database.MoneyRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MoneyRepository(application: Application) {
    private val moneyDao: MoneyDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MoneyRoomDatabase.getDatabase(application)
        moneyDao = db.moneyDao()
    }


    fun insert(money: Money) {
        executorService.execute {
            moneyDao.insert(money)
        }
    }

    fun delete(money: Money) {
        executorService.execute {
            moneyDao.delete(money)
        }
    }

    fun update(money: Money) {
        executorService.execute {
            moneyDao.update(money)
        }
    }

    fun getAllData(): LiveData<List<Money>> = moneyDao.getAllData()
    fun getIncome(): LiveData<Int> = moneyDao.getIncome()
    fun getOutcome(): LiveData<Int> = moneyDao.getOutcome()
    fun getTotalMoney(): LiveData<Int> = moneyDao.getTotalMoney()
}