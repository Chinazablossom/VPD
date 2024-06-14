package com.example.vpd.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vpd.databinding.TransactionItemBinding
import com.example.vpd.models.Transaction

class TransactionHistoryAdapter(
    private var transactionHistoryList: List<Transaction>
) : RecyclerView.Adapter<TransactionHistoryAdapter.TransactionViewHolder>() {


    inner class TransactionViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.transactionId.text = transaction.id.toString()
            binding.receiverTrnsactionTv.text = transaction.receiver
            binding.accountTypeTv.text = transaction.account
            binding.amountTv.text = transaction.amount.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionViewHolder {
        val binding =
            TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactionHistoryList[position])
    }

    override fun getItemCount() = transactionHistoryList.size

}