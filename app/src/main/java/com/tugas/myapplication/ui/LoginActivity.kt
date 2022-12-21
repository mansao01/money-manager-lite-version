package com.tugas.myapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tugas.myapplication.databinding.ActivityLoginBinding
import com.tugas.myapplication.helper.ViewModelFactory
import com.tugas.myapplication.preferences.SettingPreferences
import com.tugas.myapplication.preferences.UserPreference
import com.tugas.myapplication.user.User
import com.tugas.myapplication.viewmodel.LoginViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: User
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory(
            UserPreference.getInstance(dataStore),
            application,
            SettingPreferences.getInstance(dataStore)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        toRegister()
        loginViewModel.getUser().observe(this) {
            this.user = it
        }
        binding.btnLogin.setOnClickListener {
            loginSetup()
        }
    }

    private fun loginSetup() {
        binding.apply {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            when {
                username.isEmpty() -> edtUsername.error = "required username"
                password.isEmpty() -> edtPassword.error = "required password"
                username != user.username -> edtUsername.error = "username tidak cocok"
                password != user.password -> edtPassword.error = "password tidak cocok"
                else -> {
                    loginViewModel.login()
                    AlertDialog.Builder(this@LoginActivity).apply {
                        setTitle("Login berhasil!")
                        setMessage("Lanjut?")
                        setPositiveButton("Ya") { _, _ ->
                            val intent = Intent(context, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }

    private fun toRegister() {
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}