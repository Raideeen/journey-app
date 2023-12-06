package com.example.journey.ui.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.journey.R
import com.example.journey.data.UsersData
import com.example.journey.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding

    lateinit var newusername : EditText

    lateinit var newpassword : EditText

    lateinit var ChangeUsername : Button

    lateinit var ChangePassword : Button

    lateinit var btnML : Button


    var user = UsersData.getUsersData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentProfilBinding.inflate(layoutInflater)

        view.findViewById<Button>(R.id.ChangeUsername).setOnClickListener {
            user.newUsername = binding.newusername.text.toString()

        }
        view.findViewById<Button>(R.id.ChangePassword).setOnClickListener {
            user.newPassword = binding.newpassword.text.toString()
        }

        view.findViewById<Button>(R.id.btnML).setOnClickListener{
            findNavController().navigate(R.id.action_profilFragment_to_mentionsLegalesFragment)
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