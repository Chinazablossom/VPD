package com.example.vpd.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vpd.adapters.TransactionHistoryAdapter
import com.example.vpd.databinding.FragmentTransactionHistoryBinding
import com.example.vpd.view_model.SharedViewModel

class TransactionHistoryFragment : Fragment() {
    private var _binding: FragmentTransactionHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            rv.layoutManager = LinearLayoutManager(requireContext())

            viewModel.transactions.observe(viewLifecycleOwner) { transactions ->

                if (transactions.isNullOrEmpty()) {
                    noTrasactionTxt.visibility = View.VISIBLE
                } else {
                    noTrasactionTxt.visibility = View.GONE
                }

                rv.adapter = TransactionHistoryAdapter(transactions)

            }


        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}