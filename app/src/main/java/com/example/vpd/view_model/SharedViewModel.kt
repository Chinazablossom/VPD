package com.example.vpd.view_model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vpd.data.DataSource
import com.example.vpd.database.AppDatabase
import com.example.vpd.models.Account
import com.example.vpd.models.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val dataSource = DataSource()

    private val transactionDao = AppDatabase.getDatabase(application).transactionDao()

    private val _accounts = MutableLiveData<List<Account>>().apply { value = dataSource.loadAccounts() }
    val accounts: LiveData<List<Account>> get() = _accounts

    val transactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()

    fun makeTransfer(
        fromAccountType: String,
        toReceiverAccountType: String,
        amount: Double,
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            val accountsList = accounts.value?.toMutableList() ?: run {
                Log.e("Sharedviewmodel", "accounts list is null")
                return@launch
            }

            val fromAccountIndex = accountsList.indexOfFirst { it.accountType == fromAccountType }
            val toAccountIndex =
                accountsList.indexOfFirst { it.accountType == toReceiverAccountType }

            if (fromAccountIndex == -1 || toAccountIndex == -1) return@launch

            val fromAccount = accountsList[fromAccountIndex]
            val toAccount = accountsList[toAccountIndex]
            val currentBalance = fromAccount.accountbalance
            val newFromBalance = currentBalance - amount
            val newToBalance = toAccount.accountbalance + amount


            accountsList[fromAccountIndex] = fromAccount.copy(accountbalance = newFromBalance)
            accountsList[toAccountIndex] = toAccount.copy(accountbalance = newToBalance)
             _accounts.value = accountsList


            val newTransaction = Transaction(
                id = dataSource.getRandomTransactionId(),
                receiver = toReceiverAccountType,
                account = fromAccountType,
                amount = amount
            )

            addTransaction(newTransaction)

        }
    }

    private fun addTransaction(newTransaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionDao.insertTransaction(newTransaction)
        }
    }

}

