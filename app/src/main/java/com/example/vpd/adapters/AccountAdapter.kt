package com.example.vpd.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vpd.R
import com.example.vpd.models.Account

class AccountAdapter(private var accounts: MutableList<Account>) :
    RecyclerView.Adapter<AccountAdapter.AccountViewsHolder>() {

    class AccountViewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val accountName = itemView.findViewById<TextView>(R.id.accountName)
        val accountId = itemView.findViewById<TextView>(R.id.accountId)
        val accountType = itemView.findViewById<TextView>(R.id.accountType)
        val accountBalance = itemView.findViewById<TextView>(R.id.accountBalance)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewsHolder {
        return AccountViewsHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.account_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AccountViewsHolder, position: Int) {
        val account = accounts[position]
        holder.accountName.text = account.accountName
        holder.accountId.text = account.accountId.toString()
        holder.accountType.text = account.accountType
        holder.accountBalance.text = account.accountbalance.toString()

    }


    override fun getItemCount() = accounts.size


    fun updateAccountBalance(accountId: Int, newBalance: Double) {
        val index = accounts.indexOfFirst { it.accountId == accountId }
        if (index != -1) {
            accounts[index] = accounts[index].copy(accountbalance = newBalance)
            notifyItemChanged(index)
        }
    }


}