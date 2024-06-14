package com.example.vpd.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey
    var id: Int,
    var receiver: String,
    var account: String,
    var amount: Double,
)