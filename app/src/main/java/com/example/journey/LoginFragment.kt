package com.example.journey

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.journey.R
import com.example.journey.databinding.FragmentLoginBinding
import com.example.journey.data.UsersData

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    lateinit var username : EditText

    lateinit var password : EditText

    lateinit var loginButton : Button

    var user = UsersData.getUsersData()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentLoginBinding.inflate(layoutInflater)

        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            println("LoginFragmentBefore login check")
            if(binding.username.text.toString().trim().equals("user")/*user.Username*/ && binding.password.text.toString().trim().equals("1234")/*user.Password*/)
            {
                Toast.makeText(activity, "login successful", Toast.LENGTH_SHORT).show()
                // navigation jusqu'au fragment home
                //it.findNavController().navigate(R.id.action_loginFragment_to_notebookFragment)
                findNavController().navigate(R.id.action_loginFragment2_to_storyNotebookFragment)
            }
            else
            {
                println("LoginFragmentUsername: ${binding.username.text.toString()}")
                println("LoginFragmentPassword: ${binding.password.text.toString()}")
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