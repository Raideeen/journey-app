package com.example.journey.ui.users

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.journey.R

class ProfileFragment : Fragment(R.layout.fragment_profile) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the username and password from SharedPreferences
        val sharedPref = activity?.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        val username = sharedPref?.getString("username", null)
        val password = sharedPref?.getString("password", null)

        view.findViewById<TextView>(R.id.username).text = username
        view.findViewById<TextView>(R.id.password).text = password

        view.findViewById<Button>(R.id.btnML).setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_profileLegalMention)
        }

    }
}