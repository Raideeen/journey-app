package com.example.journey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.journey.R
import com.example.journey.databinding.FragmentLoginBinding
import com.example.journey.data.UsersData

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    lateinit var username : EditText

    lateinit var password : EditText

    lateinit var loginButton : Button

    var user = UsersData.getUsersData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= FragmentLoginBinding.inflate(layoutInflater)

        binding.loginButton.setOnClickListener {
            if(binding.username.text.toString()==user.Username && binding.password.text.toString()==user.Password)
            {
                Toast.makeText(activity, "login successful", Toast.LENGTH_SHORT).show()
                // navigation jusqu'au fragment home
            }
            else
            {
                Toast.makeText(activity, "login failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }



}