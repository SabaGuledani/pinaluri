package com.saba.spark

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.saba.spark.databinding.FragmentBadHabitsBinding
import com.saba.spark.databinding.HabitCreateDialogBinding
import java.util.concurrent.TimeUnit


class badHabits : Fragment(R.layout.fragment_bad_habits) {
    private lateinit var binding: FragmentBadHabitsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBadHabitsBinding.bind(view)

        val recyclerview = binding.recyclerviewHabit
        recyclerview.layoutManager = LinearLayoutManager(context)
        var habitList = ArrayList<Habit>()






        recyclerview.adapter = HabitRecyclerviewAdapter(habitList)

        class CustomDialogFragment : DialogFragment() {
            private lateinit var binding: HabitCreateDialogBinding

            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
            ): View {
                binding = HabitCreateDialogBinding.inflate(inflater, container, false)
                return binding.root
            }

            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)

                binding.tiet.setOnFocusChangeListener { view, b ->
                    false
                    val dateRangePicker =
                        MaterialDatePicker.Builder.dateRangePicker()
                            .setTitleText("Select dates")
                            .build()
                    if (view.isFocused) {
                        dateRangePicker.show(parentFragmentManager, "tag")

                    }
                    dateRangePicker.addOnPositiveButtonClickListener {
                        var startDate = it.first
                        var endDate = it.second
                        var msDiff = endDate - startDate
                        var daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff)
                        binding.tiet.setText(daysDiff.toString())
                        binding.tiet.clearFocus()
                    }
                }
                binding.saveTV
                binding.exitIV.setOnClickListener {
                    dismiss()
                }


            }


        }

        fun showDialog() {
            val fragmentManager = parentFragmentManager
            val newFragment = CustomDialogFragment()

            val transaction = fragmentManager.beginTransaction()

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction
                .add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit()

        }
        binding.createHabitButton.setOnClickListener {
            showDialog()

        }


/*
        var habitname = "habit name"
        var active = "status: active"
        var progress = "3/10"
        var money = "asdad"
        val habitObject = Habit(habitname,progress,money,active)
        habitList.add(habitObject)

 */

    }

}

