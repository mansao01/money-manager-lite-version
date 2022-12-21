package com.tugas.myapplication.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tugas.myapplication.R
import com.tugas.myapplication.databinding.ActivityProfileBinding
import com.tugas.myapplication.helper.ViewModelFactory
import com.tugas.myapplication.preferences.SettingPreferences
import com.tugas.myapplication.preferences.UserPreference
import com.tugas.myapplication.viewmodel.ProfileViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory(
            UserPreference.getInstance(dataStore),
            application,
            SettingPreferences.getInstance(dataStore)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.profile)
        }

        setData()
    }

    private fun setData() {
        profileViewModel.getUser().observe(this) {
            if (it.isLogin) {
                binding.apply {
                    tvEmail.text = it.email
                    tvUsername.text = it.username
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}