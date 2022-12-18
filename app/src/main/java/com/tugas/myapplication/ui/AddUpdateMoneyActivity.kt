package com.tugas.myapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tugas.myapplication.R
import com.tugas.myapplication.database.Money
import com.tugas.myapplication.databinding.ActivityAddUpdateMoneyBinding
import com.tugas.myapplication.helper.ViewModelFactory
import com.tugas.myapplication.preferences.SettingPreferences
import com.tugas.myapplication.preferences.UserPreference
import com.tugas.myapplication.viewmodel.MoneyAddUpdateViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AddUpdateMoneyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUpdateMoneyBinding
    private var isEdit = false
    private var money: Money? = null
    private val recipeAddUpdateViewModel by viewModels<MoneyAddUpdateViewModel> {
        ViewModelFactory(
            UserPreference.getInstance(dataStore),
            application,
            SettingPreferences.getInstance(dataStore)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        money = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, Money::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_DATA)
        }
        if (money != null) {
            isEdit = true
        } else {
            money = Money()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (money != null) {
                money?.let {
                    binding.apply {
                        edtTransaction.setText(it.transaction)
                        edtDescription.setText(it.description)
                        edtIncome.setText(it.income)
                        edtOutcome.setText(it.outcome)
                    }
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener {
            saveUpdateData()
        }
    }

    private fun saveUpdateData() {
        binding.apply {
            val transaction = edtTransaction.text.toString()
            val description = edtDescription.text.toString()
            val income = edtIncome.text.toString()
            val outcome = edtOutcome.text.toString()
            when {
                transaction.isEmpty() -> edtTransaction.error = getString(R.string.empty)
                description.isEmpty() -> edtDescription.error = getString(R.string.empty)
                income.isEmpty() -> edtIncome.error = getString(R.string.empty)
                outcome.isEmpty() -> edtOutcome.error = getString(R.string.empty)
                else -> {
                    money?.let {
                        it.transaction = transaction
                        it.description = description
                        it.income = income
                        it.outcome = outcome
                        if (isEdit) {
                            recipeAddUpdateViewModel.update(money as Money)
                            showToast(getString(R.string.update))
                        } else {
                            recipeAddUpdateViewModel.insert(money as Money)
                            showToast(getString(R.string.added))
                        }
                        finish()
                    }
                }
            }
        }
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.deleted)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    recipeAddUpdateViewModel.delete(money as Money)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) menuInflater.inflate(R.menu.menu_update, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(this@AddUpdateMoneyActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.navigateUpTo(upIntent)
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}