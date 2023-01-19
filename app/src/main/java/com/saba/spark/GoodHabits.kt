package com.saba.spark

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import com.saba.spark.databinding.FragmentGoodHabitsBinding


class GoodHabits : Fragment(R.layout.fragment_good_habits) {
    private lateinit var binding: FragmentGoodHabitsBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var times: ArrayList<Int>
    private lateinit var habitMoneyArray: ArrayList<Double>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGoodHabitsBinding.bind(view)
        val editSpentMoney = binding.editSpentMoney
        val moneySpentPerDay = binding.moneySpentPerDay
        val monthlySaving = binding.monthlySaving
        val yearlySaving = binding.yearlySaving
        val startDate = binding.startDate
        val currentSavings = binding.currentSavings
        dbref = FirebaseDatabase.getInstance().getReference()
        val userUid = FirebaseAuth.getInstance().currentUser?.uid
        times = ArrayList()
        habitMoneyArray = ArrayList()

        dbref.child("users").child("$userUid").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                currentSavings.text = ""
                var timesXmoney = 0.0
                var dailyMoney = 0.0
                for (postsnapshot in snapshot.children) {
                    times.clear()
                    habitMoneyArray.clear()
                    val habitobject = postsnapshot.getValue(Habit::class.java)
                    if (habitobject != null) {
                        times.add(habitobject.habitprogressnow.toInt())
                        habitMoneyArray.add(habitobject.dailyUseMoney.toDouble())
                        dailyMoney += habitMoneyArray.sum()
                        moneySpentPerDay.text = dailyMoney.toString() + " Gel"
                        monthlySaving.setText((dailyMoney.toInt() * 30).toString() + "Gel")
                        yearlySaving.setText((dailyMoney.toInt() * 30 * 12).toString() + "Gel")

                        for (i in 0 until times.size) {
                            timesXmoney += times[i].toDouble() * habitMoneyArray[i]
                        }

                        currentSavings.text = timesXmoney.toString() + "Gel"

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                var miki  = "miki"
                var mausi = "mausi"
                var mikimausi = miki+mausi
            }

        })


        editSpentMoney.setOnClickListener {

            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.good_habits_edit_money, null);
            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("Bad Habit")
            val mAlertDialog = mBuilder.show()
            var submit = mAlertDialog.findViewById<Button>(R.id.submit)
            var cancel = mAlertDialog.findViewById<TextView>(R.id.cancelButton)

            submit.setOnClickListener {
                mAlertDialog.dismiss()
                var editmoney1 = mAlertDialog.findViewById<EditText>(R.id.editMoney)
                var editMoney = editmoney1.text.toString()
                moneySpentPerDay.setText(editMoney + " Gel")
                monthlySaving.setText((editMoney.toInt() * 30).toString() + "Gel")
                yearlySaving.setText((editMoney.toInt() * 30 * 12).toString() + "Gel")

            }
            cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }


        }

        binding.logout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("log out")
                .setMessage("are you sure?")
                .setNegativeButton("no") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("yes") { dialog, which ->
                    dialog.dismiss()
                    val intent = Intent(requireContext(),AuthActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                    FirebaseAuth.getInstance().signOut()
                }
                .show()

        }


    }


}
