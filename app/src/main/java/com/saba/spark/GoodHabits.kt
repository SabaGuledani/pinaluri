package com.saba.spark

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.saba.spark.databinding.FragmentBadHabitsBinding
import com.saba.spark.databinding.FragmentGoodHabitsBinding


class GoodHabits : Fragment(R.layout.fragment_good_habits) {
    private lateinit var binding: FragmentGoodHabitsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGoodHabitsBinding.bind(view)

        showEditTextDialog()

        }

    private fun showEditTextDialog() {
        val editMoney = binding.editSpentMoney
        editMoney.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater(context)
            val dialogLayout = inflater.inflate(R.layout.good_habits_settings , null)
            val editText = dialogLayout.findViewById<EditText>(R.id.)
        }

    }


}
