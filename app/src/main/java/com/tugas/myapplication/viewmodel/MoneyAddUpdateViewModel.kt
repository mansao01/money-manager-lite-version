package com.tugas.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.tugas.myapplication.database.Money
import com.tugas.myapplication.repository.MoneyRepository

class MoneyAddUpdateViewModel(application: Application) : ViewModel() {
    private val moneyRepository = MoneyRepository(application)

    fun insert(money: Money) {
        moneyRepository.insert(money)
    }

    fun update(money: Money) {
        moneyRepository.update(money)
    }

    fun delete(money: Money) {
        moneyRepository.delete(money)
    }
}