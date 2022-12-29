package com.saba.spark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.saba.spark.databinding.FragmentPasswordChangeBinding
import com.saba.spark.databinding.FragmentResetPasswordBinding


class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {
    private lateinit var binding: FragmentResetPasswordBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResetPasswordBinding.bind(view)

        binding.sendBtn.setOnClickListener {
            var email = binding.email.editText?.text.toString()

            FirebaseAuth.getInstance()
                .sendPasswordResetEmail((email))
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(context, "check your email!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }


}


