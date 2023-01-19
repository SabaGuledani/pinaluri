package com.saba.spark

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.saba.spark.databinding.FragmentLogInBinding


class LogIn : Fragment(R.layout.fragment_log_in) {
    private lateinit var binding:FragmentLogInBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLogInBinding.bind(view)

        binding.logInBtn.setOnClickListener {
            val email = (binding.email).editText?.text.toString()
            val password = (binding.password).editText?.text.toString()


            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(context,MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()

                    }
                }
        }

        binding.changePassword.setOnClickListener {
            val action = LogInDirections.actionLoginFragmentToChangePassword()
            findNavController().navigate(action)

        }


        binding.noAcc.setOnClickListener {
            val action = LogInDirections.actionLoginFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }
        binding.noPassword.setOnClickListener {
            val action = LogInDirections.actionLoginFragmentToResetPasswordFragment()
            findNavController().navigate(action)
        }



    }
}
