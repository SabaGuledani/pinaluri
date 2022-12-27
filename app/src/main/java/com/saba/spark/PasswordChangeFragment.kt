package com.saba.spark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.saba.spark.databinding.FragmentLogInBinding
import com.saba.spark.databinding.FragmentPasswordChangeBinding


class PasswordChangeFragment : Fragment(R.layout.fragment_password_change) {
    private lateinit var binding: FragmentPasswordChangeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordChangeBinding.bind(view)

        binding.resetBtn.setOnClickListener {
            val email = (binding.email).editText?.text.toString()
            val password = (binding.password).editText?.text.toString()
            val repeatedPassword = (binding.passwordRepeat).editText?.text.toString()

            if (password == repeatedPassword){


            }else{
                Toast.makeText(context, "check the password cowboy!", Toast.LENGTH_SHORT).show()
            }
        }


    }

}