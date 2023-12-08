package com.example.journey.ui.users

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.journey.R

/**
 * A [Fragment] subclass that serves as a splash screen.
 * This fragment is responsible for checking if the user is already logged in and navigating to the appropriate fragment.
 * If the user is already logged in, it navigates to the notebook fragment.
 * If the user is not logged in, it navigates to the login fragment.
 */
class SplashFragment : Fragment(R.layout.fragment_splash) {

    /**
     * Called when the view is created.
     * Initializes UI elements and sets up listeners.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if the user is already logged in
        val sharedPref = activity?.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        val username = sharedPref?.getString("username", null)
        val password = sharedPref?.getString("password", null)
        if (username != null && password != null) {
            // User is already logged in, navigate to the notebook fragment
            findNavController().navigate(R.id.action_splashFragment_to_notebookFragment)
        } else {
            // User is not logged in, navigate to the login fragment
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }
}