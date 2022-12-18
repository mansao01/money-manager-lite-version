package com.tugas.myapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugas.myapplication.R
import com.tugas.myapplication.adapter.ListMoneyAdapter
import com.tugas.myapplication.databinding.ActivityMainBinding
import com.tugas.myapplication.helper.ViewModelFactory
import com.tugas.myapplication.preferences.SettingPreferences
import com.tugas.myapplication.preferences.UserPreference
import com.tugas.myapplication.viewmodel.MainViewModel
import com.tugas.myapplication.viewmodel.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListMoneyAdapter

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory(
            UserPreference.getInstance(dataStore),
            this.application,
            SettingPreferences.getInstance(
                (dataStore)
            )
        )
    }

    private val settingsViewModel by viewModels<SettingsViewModel> {
        ViewModelFactory(
            UserPreference.getInstance(dataStore),
            this.application,
            SettingPreferences.getInstance(dataStore)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsViewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        mainViewModel.getUser().observe(this) {
            if (!it.isLogin) {
                val intentToLogin = Intent(this, LoginActivity::class.java)
                startActivity(intentToLogin)
            }
        }

        adapter = ListMoneyAdapter()
        showRecyclerViewListData()
        showTransactionData()

        binding.fabAdd.setOnClickListener {
            val intentToAddData = Intent(this@MainActivity, AddUpdateMoneyActivity::class.java)
            startActivity(intentToAddData)
        }
    }

    private fun showRecyclerViewListData() {
        binding.apply {
            rvMoney.adapter = adapter
            rvMoney.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMoney.setHasFixedSize(true)
        }
        mainViewModel.getAllData().observe(this) {
            if (it.isEmpty()) {
                binding.tvNull.visibility = View.VISIBLE
                binding.rvMoney.visibility = View.GONE
            } else {
                adapter.setListMoney(it)
                binding.rvMoney.visibility = View.VISIBLE
                binding.tvNull.visibility = View.GONE
            }
        }
    }

    private fun showTransactionData() {
        mainViewModel.apply {
            binding.apply {
                getIncome().observe(this@MainActivity) {
                    tvIncome.text = it.toString()
                }
                getOutcome().observe(this@MainActivity) {
                    tvOutcome.text = it.toString()
                }
                getTotalMoney().observe(this@MainActivity) {
                    tvTotal.text = it.toString()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> mainViewModel.logout()
            R.id.action_profile -> {
                val intentToProfile = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(intentToProfile)
            }
            R.id.action_setting -> {
                val intentToSettings = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intentToSettings)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}