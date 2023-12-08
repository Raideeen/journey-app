package com.example.journey.ui.users

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.journey.R
import com.example.journey.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        // Check if the user is already logged in
        val sharedPref = activity?.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        val username = sharedPref?.getString("username", null)
        val password = sharedPref?.getString("password", null)
        if (username != null && password != null) {
            // User is already logged in, navigate to the notebook fragment
            findNavController().navigate(R.id.action_loginFragment_to_notebookFragment)
        }

        binding.loginButton.setOnClickListener {
            if (isLoginValid()) {
                saveLoginDetails()
                Toast.makeText(activity, R.string.login_successful, Toast.LENGTH_SHORT).show()

                // Create NavOptions to clear the back stack
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment, true)
                    .build()

                findNavController().navigate(R.id.action_loginFragment_to_notebookFragment, null, navOptions)
            } else {
                Toast.makeText(activity, R.string.login_failed, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isLoginValid(): Boolean {
        val username = binding.loginUsername.text.toString().trim()
        val password = binding.loginPassword.text.toString().trim()
        Log.d("LoginFragment", "username: $username, password: $password")
        return username == "user" && password == "1234"
    }

    private fun saveLoginDetails() {
        val sharedPref = activity?.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        with (sharedPref?.edit()) {
            this?.putString("username", binding.loginUsername.text.toString().trim())
            this?.putString("password", binding.loginPassword.text.toString().trim())
            this?.apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}