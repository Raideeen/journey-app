package com.example.journey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.journey.data.UsersData
import com.example.journey.databinding.FragmentLoginBinding
import com.example.journey.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding

    lateinit var newusername : EditText

    lateinit var newpassword : EditText

    lateinit var ChangeUsername : Button

    lateinit var ChangePassword : Button

    lateinit var btnML : Button


    var user = UsersData.getUsersData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= FragmentProfilBinding.inflate(layoutInflater)

        binding.ChangeUsername.setOnClickListener {
            user.newUsername = binding.newusername.text.toString()

        }
        binding.ChangePassword.setOnClickListener {
            user.newPassword = binding.newpassword.text.toString()
        }

        binding.btnML.setOnClickListener{
            //it.findNavController().navigate(R.id.action_profilFragment_to_mentionsLegalesFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }


}