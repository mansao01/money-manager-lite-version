package com.tugas.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tugas.myapplication.database.Money
import com.tugas.myapplication.preferences.UserPreference
import com.tugas.myapplication.repository.MoneyRepository
import com.tugas.myapplication.user.User
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference, application: Application) : ViewModel() {

    private val mMoneyRepository = MoneyRepository(application)

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun getAllData(): LiveData<List<Money>> {
        return mMoneyRepository.getAllData()
    }

    fun getIncome(): LiveData<Int> {
        return mMoneyRepository.getIncome()
    }

    fun getOutcome(): LiveData<Int> {
        return mMoneyRepository.getOutcome()
    }

    fun getTotalMoney(): LiveData<Int> {
        return mMoneyRepository.getTotalMoney()
    }
}