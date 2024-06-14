package com.example.vpd.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vpd.R
import com.example.vpd.adapters.AccountAdapter
import com.example.vpd.data.DataSource
import com.example.vpd.databinding.FragmentHomeBinding
import com.example.vpd.view_model.SharedViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var auth: FirebaseAuth
    private val viewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.apply {
            val dataSource = DataSource().loadAccounts()
            val accountAdapter = AccountAdapter(dataSource)

            rv.apply {
                adapter = accountAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            viewModel.accounts.observe(viewLifecycleOwner) { accounts ->
                accounts.forEach { account ->
                    accountAdapter.updateAccountBalance(account.accountId, account.accountbalance)
                    Log.d("HomeFragment", "Accounts: $accounts")

                }

            }

            viewTransactionsBtn.setOnClickListener {
                findNavController().navigate(R.id.transactionHistoryFragment)

            }
            transferBtn.setOnClickListener {
                findNavController().navigate(R.id.transferFragment)

            }

            logOutBtn.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                auth.signOut()
                clearLoginState()
                findNavController().navigate(R.id.loginFragment)
                progressBar.visibility = View.GONE
            }


        }


    }


    private fun clearLoginState() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("VPDPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

