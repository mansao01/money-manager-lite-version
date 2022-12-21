package com.tugas.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tugas.myapplication.preferences.UserPreference
import com.tugas.myapplication.user.User

class ProfileViewModel(private val pref: UserPreference): ViewModel() {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}