package com.example.vpd.data

import com.example.vpd.models.Account
import com.example.vpd.models.Transaction
import kotlin.random.Random


class DataSource() {


    fun loadAccounts(): MutableList<Account> {
        return mutableListOf(
            Account(2238493, "Chinaza Blossom", "NGN", 6800.00),
            Account(7586474, "Chinaza Blossom", "USD", 7500.00),
            Account(8349443, "Chinaza Blossom", "GBP", 8000.00),
        )


    }

    private val receivers = listOf(
        "Lucy",
        "NewPost Delivery",
        "GameLoft",
        "James",
        "Alexa",
        "E-Lax",
        "Coffee-Shop",
        "D-Mart",
        "Supermarket",
        "Bank"
    )
    private val accounts = listOf("USD", "GBP", "NGN")
    fun getRandomTransactionId(): Int {
        return Random.nextInt(100000, 999999)
    }

    private fun getRandomReceiver(): String {
        return receivers.random()
    }

    private fun getRandomAccount(): String {
        return accounts.random()
    }

    private fun getRandomTransactionAmount(): Double {
        return Random.nextInt(20, 1000).toDouble() //.toString().format("%.2d")
    }

    fun loadTransactions(): ArrayList<Transaction> {
        var list = ArrayList<Transaction>()

        for (i in 1..5) {
            list.add(
                Transaction(
                    getRandomTransactionId(),
                    getRandomReceiver(),
                    getRandomAccount(),
                    getRandomTransactionAmount(),
                )
            )
        }
        return list
    }


}