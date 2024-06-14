package com.example.vpd.views

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vpd.R
import com.example.vpd.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.apply {
            val txt = "Don't have an account? Sign up"
            val spannedText = SpannableString(txt)
            val customSpan = CustomSpan(1.01f, Color.BLUE)

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    findNavController().navigate(R.id.signUpFragment)

                }
            }

            spannedText.setSpan(customSpan, 22, txt.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannedText.setSpan(clickableSpan, 22, txt.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            tvSignup.movementMethod = android.text.method.LinkMovementMethod.getInstance()
            tvSignup.text = spannedText


            loginBtn.setOnClickListener {
                val email = loginEmailEt.text?.toString()
                val password = passwordEt.text?.toString()

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
                    return@setOnClickListener
                } else {
                    progressBar.visibility = View.VISIBLE
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        progressBar.visibility = View.GONE
                        if (it.isSuccessful) {
                            findNavController().navigate(R.id.homeFragment)
                            saveLoginState()
                            Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            if (it.exception is FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(
                                    requireContext(),
                                    "Invalid Credentials",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@addOnCompleteListener
                            }

                            Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                        it.addOnFailureListener {
                            progressBar.visibility = View.GONE
                            return@addOnFailureListener
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


class CustomSpan(
    size: Float,
    @ColorInt private val color: Int,
) : RelativeSizeSpan(size) {
    override fun updateDrawState(textPaint: TextPaint) {
        super.updateDrawState(textPaint)
        textPaint?.color = color
    }
}

