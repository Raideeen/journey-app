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

/**
 * A [Fragment] subclass that provides a login screen for the user.
 * This fragment is responsible for validating the user's login credentials and saving them in shared preferences.
 * If the user is already logged in, the fragment will navigate to the notebook fragment.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    // Binding object instance corresponding to the fragment layout
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    /**
     * Called when the view is created.
     * Initializes UI elements and sets up listeners.
     */
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

        // Set up the login button click listener
        binding.loginButton.setOnClickListener {
            if (isLoginValid()) {
                saveLoginDetails()
                Toast.makeText(activity, R.string.login_successful, Toast.LENGTH_SHORT).show()

                // Create NavOptions to clear the back stack
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment, true)
                    .build()

                // Navigate to the notebook fragment
                findNavController().navigate(R.id.action_loginFragment_to_notebookFragment, null, navOptions)
            } else {
                Toast.makeText(activity, R.string.login_failed, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Checks if the login credentials entered by the user are valid.
     * In this case, the valid username is "user" and the valid password is "1234".
     *
     * @return True if the login credentials are valid, false otherwise.
     */
    private fun isLoginValid(): Boolean {
        val username = binding.loginUsername.text.toString().trim()
        val password = binding.loginPassword.text.toString().trim()
        Log.d("LoginFragment", "username: $username, password: $password")
        return username == "user" && password == "1234"
    }

    /**
     * Saves the login credentials entered by the user in shared preferences.
     */
    private fun saveLoginDetails() {
        val sharedPref = activity?.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        with (sharedPref?.edit()) {
            this?.putString("username", binding.loginUsername.text.toString().trim())
            this?.putString("password", binding.loginPassword.text.toString().trim())
            this?.apply()
        }
    }

    /**
     * Called when the view hierarchy associated with the fragment is being destroyed.
     * It allows the fragment to clean up resources associated with its View.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}