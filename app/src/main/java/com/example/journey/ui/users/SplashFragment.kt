package com.example.journey.ui.users

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.journey.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

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