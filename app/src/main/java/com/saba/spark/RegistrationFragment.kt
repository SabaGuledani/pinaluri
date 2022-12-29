package com.saba.spark

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.saba.spark.databinding.FragmentPasswordChangeBinding
import com.saba.spark.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private lateinit var binding: FragmentRegistrationBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)




        binding.registerBtn.setOnClickListener {
            var email = binding.email.editText?.text.toString()
            var password = binding.password.editText?.text.toString()
            var repeatPassword = binding.passwordRepeat.editText?.text.toString()


            if(password==repeatPassword){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                    if(it.isSuccessful){
                                        val intent = Intent(context,MainActivity::class.java)
                                        startActivity(intent)
                                        activity?.finish()
                                    }

                                }

                        }else{
                            Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(context, "enter password correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }
}