package com.example.vpd.views

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vpd.R
import com.example.vpd.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException


class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.apply {
            val txt = "Already have an account? Log in"
            val spannedText = SpannableString(txt)


            val customSpan = CustomSpan(1.01f, Color.BLUE)
            spannedText.setSpan(customSpan, 24, txt.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    findNavController().popBackStack(R.id.loginFragment, false)
                }
            }

            spannedText.setSpan(clickableSpan, 24, txt.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            loginTv.movementMethod = android.text.method.LinkMovementMethod.getInstance()
            loginTv.text = spannedText

            signUpBtn.setOnClickListener {
                val email = binding.signUpEmailEt.text?.toString()
                val password = binding.signUpPasswordEt.text?.toString()

                getSystemService(
                    requireContext(),
                    InputMethodManager::class.java
                )?.hideSoftInputFromWindow(
                    requireView().windowToken,
                    0
                )
                if (email.isNullOrBlank() || password.isNullOrBlank()) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    progressBar.visibility = View.VISIBLE
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            progressBar.visibility = View.GONE
                            if (task.isSuccessful) {
                                saveLoginState()
                                Toast.makeText(
                                    requireContext(),
                                    "Account created successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().navigate(R.id.homeFragment)
                            } else {
                                if (task.exception is FirebaseAuthUserCollisionException) {
                                    Toast.makeText(
                                        requireContext(),
                                        "User already exists. Please login.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    findNavController().navigate(R.id.loginFragment)
                                    return@addOnCompleteListener
                                }
                                Toast.makeText(
                                    requireContext(),
                                    "Opps...Registration failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }

        }


    }

    private fun saveLoginState() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("VPDPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}