package com.example.vpd.views

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vpd.R
import com.example.vpd.databinding.FragmentTransferBinding
import com.example.vpd.view_model.SharedViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class TransferFragment : Fragment() {
    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()
    lateinit var sAccount: TextView
    lateinit var desAccount: TextView
    lateinit var finalAmount: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTransferBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val sourceAccounts = resources.getStringArray(R.array.source_accunts)
            val arrayAdapterSourceAccounts =
                ArrayAdapter(requireContext(), R.layout.source_account_list, sourceAccounts)
            sourceAccountAC.setAdapter(arrayAdapterSourceAccounts)
            destinationAccountAC.setAdapter(arrayAdapterSourceAccounts)



            viewModel.accounts.observe(viewLifecycleOwner) { accounts ->

                transferButton.setOnClickListener {
                    progressBar.visibility = View.VISIBLE
                    ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
                        ?.hideSoftInputFromWindow(requireView().windowToken, 0)

                    val sourceAccount = sourceAccountAC.text?.toString()
                    val destinationAccount = destinationAccountAC.text?.toString()
                    val amount = transferAmountET.text?.toString()
                    val source = accounts.find { it.accountType == sourceAccount }?.accountbalance


                    if (sourceAccount.isNullOrEmpty() || destinationAccount.isNullOrBlank() || amount.isNullOrBlank()) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Please fill all the fields",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    val amountNum = amount.toDoubleOrNull()
                    if (amountNum == null) {
                        Toast.makeText(
                            requireContext(),
                            "Please enter a valid amount",
                            Toast.LENGTH_SHORT
                        ).show()
                        progressBar.visibility = View.GONE
                        return@setOnClickListener
                    }
                    if (sourceAccount == destinationAccount) {
                        progressBar.visibility = View.GONE
                        transferAmountLayout.error = "Can't send money to the same account"
                        transferAmountLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED))
                        Toast.makeText(
                            requireContext(),
                            "Source and destination accounts cannot be the same",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener

                    }


                    if (source != null && amountNum <= source) {
                        progressBar.visibility = View.GONE
                        transactionDialogue(sourceAccount, destinationAccount, amountNum)

                    } else {
                        progressBar.visibility = View.GONE
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Insufficient balance")
                            .setMessage("You only have $source in your Source account balance")
                            .setNegativeButton("Cancel", null)
                            .show()
                        Toast.makeText(requireContext(), "Insufficient balance", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            }

        }


    }


    private fun transactionDialogue(
        sourceAccount: String,
        destinationAccount: String,
        amountNum: Double
    ) {
        val dialog = Dialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.transaction_dialogue, null)
        sAccount = dialogView.findViewById(R.id.sourceAccountTxt)
        desAccount = dialogView.findViewById(R.id.receivingAccountTxt)
        sAccount.text = sourceAccount
        desAccount.text = destinationAccount
        finalAmount = dialogView.findViewById(R.id.finalAmount)
        finalAmount.text = getString(R.string.amount_1s, amountNum.toString())



        dialog.setContentView(dialogView)
        dialogView.findViewById<Button>(R.id.yesBtn)?.setOnClickListener {
            viewModel.makeTransfer(sourceAccount, destinationAccount, amountNum)
            Toast.makeText(requireContext(), "Transfer Successful", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.homeFragment)
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
            Toast.makeText(requireContext(), "Transfer Cancelled", Toast.LENGTH_SHORT)
                .show()
            dialog.dismiss()
        }

        dialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}