package com.saba.spark

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.saba.spark.databinding.FragmentBadHabitsBinding
import com.saba.spark.databinding.FragmentGoodHabitsBinding



class GoodHabits : Fragment(R.layout.fragment_good_habits) {
    private lateinit var binding: FragmentGoodHabitsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGoodHabitsBinding.bind(view)
        val editSpentMoney = binding.editSpentMoney
        val moneySpentPerDay = binding.moneySpentPerDay
        val monthlySaving = binding.monthlySaving
        val yearlySaving = binding.yearlySaving
        val startDate = binding.startDate
        val currentSavings = binding.currentSavings



        editSpentMoney.setOnClickListener{

            val mDialogView = LayoutInflater.from(context).inflate(R.layout.good_habits_edit_money , null);
            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("Bad Habit")
            val mAlertDialog = mBuilder.show()
            var submit =mAlertDialog.findViewById<Button>(R.id.submit)
            var cancel = mAlertDialog.findViewById<TextView>(R.id.cancelButton)

            submit.setOnClickListener{
                mAlertDialog.dismiss()
                var editmoney1 = mAlertDialog.findViewById<EditText>(R.id.editMoney)
                var editMoney = editmoney1.text.toString()
                moneySpentPerDay.setText(editMoney + " Gel")
                monthlySaving.setText( (editMoney.toInt() * 30).toString() + "Gel")
                yearlySaving.setText( (editMoney.toInt() * 30 * 12).toString() + "Gel")

            }
            cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }


        }



    }






}
