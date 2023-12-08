package com.example.journey.ui.users

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.journey.R

/**
 * A [Fragment] subclass that displays the user's profile.
 * This fragment is responsible for retrieving the user's login credentials from shared preferences and displaying them.
 * It also provides a button for navigating to the legal mention page.
 */
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    /**
     * Called when the view is created.
     * Initializes UI elements and sets up listeners.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the username and password from SharedPreferences
        val sharedPref = activity?.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        val username = sharedPref?.getString("username", null)
        val password = sharedPref?.getString("password", null)

        // Display the username and password
        view.findViewById<TextView>(R.id.username).text = username
        view.findViewById<TextView>(R.id.password).text = password

        // Set up the click listener for the legal mention button
        view.findViewById<Button>(R.id.btnML).setOnClickListener {
            // Navigate to the legal mention page when the button is clicked
            findNavController().navigate(R.id.action_profileFragment_to_profileLegalMention)
        }
    }
}