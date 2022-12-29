package com.saba.spark

import android.content.Intent
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
            val newPassword = (binding.passwordNew).editText?.text.toString()
            
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { 
                    if(it.isSuccessful){
                        FirebaseAuth.getInstance()
                            .currentUser?.updatePassword(newPassword)
                            ?.addOnCompleteListener {
                                if(it.isSuccessful){
                                    val intent = Intent(context,MainActivity::class.java)
                                    startActivity(intent)
                                    activity?.finish()
                                }else{
                                    Toast.makeText(context, "new password is less than 6 characters", Toast.LENGTH_SHORT).show()   
                                }
                            }
                    }else{
                        Toast.makeText(context, "check your password and email", Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }

}