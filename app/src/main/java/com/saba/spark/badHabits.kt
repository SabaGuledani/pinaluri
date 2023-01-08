package com.saba.spark

import android.animation.ObjectAnimator
import android.app.Dialog
import android.app.ProgressDialog.show
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.saba.spark.databinding.FragmentBadHabitsBinding
import com.saba.spark.databinding.HabitCreateDialogBinding
import com.saba.spark.databinding.HabitOpenDialogFragmentBinding
import java.util.concurrent.TimeUnit


class badHabits : Fragment(R.layout.fragment_bad_habits) {
    private lateinit var binding: FragmentBadHabitsBinding
    private lateinit var habitList: ArrayList<Habit>
    private lateinit var dbref: DatabaseReference
    private lateinit var habitRecyclerviewAdapter: HabitRecyclerviewAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBadHabitsBinding.bind(view)


        var senderuid = FirebaseAuth.getInstance().currentUser?.uid

        val recyclerview = binding.recyclerviewHabit
        recyclerview.layoutManager = LinearLayoutManager(context)
        habitList = ArrayList()

        dbref = FirebaseDatabase.getInstance().getReference()
        habitRecyclerviewAdapter = HabitRecyclerviewAdapter(habitList)
        recyclerview.adapter = habitRecyclerviewAdapter




        dbref.child("users").child("$senderuid").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                habitList.clear()
                for (postsnapshot in snapshot.children) {
                    val habitobject = postsnapshot.getValue(Habit::class.java)
                    habitList.add(habitobject!!)
                }

                habitRecyclerviewAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })





        class CustomDialogFragment : DialogFragment() {
            private lateinit var binding: HabitCreateDialogBinding
            override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
            ): View {
                binding = HabitCreateDialogBinding.inflate(inflater, container, false)

                return binding.root
            }

            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)


                binding.tiet.setOnFocusChangeListener { view, b ->
                    false
                    val dateRangePicker =
                        MaterialDatePicker.Builder.dateRangePicker().setTitleText("Select dates")
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
                //saving into database
                binding.saveTV.setOnClickListener {
                    val habitName = binding.habitet.editText?.text.toString()
                    val habitProgress = binding.tiet.text.toString()
                    val dailyUseMoney = binding.money.editText?.text.toString()
                    val status = "status:active"
                    val progressnow = "0"
                    if (habitName.isNotEmpty() && habitProgress.isNotEmpty() && dailyUseMoney.isNotEmpty()) {

                        val habitObject =
                            Habit(habitName, habitProgress, dailyUseMoney, status, progressnow)

                        dbref.child("users").child("$senderuid").child(habitName)
                            .setValue(habitObject)

                        dismiss()


                    }

                }
                binding.exitIV.setOnClickListener {
                    dismiss()


                }



            }







        }

        class HabitDialog(var position: Int) : DialogFragment() {
            var habitObject = habitList[position]


            private lateinit var binding: HabitOpenDialogFragmentBinding
            override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
            ): View {
                binding = HabitOpenDialogFragmentBinding.inflate(inflater, container, false)

                return binding.root
            }

            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)

                binding.habitName.text = habitObject.habitName
                binding.progressTV.text =
                    habitObject.habitprogressnow.toString() + "/" + habitObject.habitprogress.toString()
                var progressbar = binding.circularProgressIndicator

                progressbar.max = habitObject.habitprogress.toInt() * 1000
                ObjectAnimator.ofInt(progressbar,"progress",(habitObject.habitprogressnow.toInt())*1000)
                    .setDuration(1700)
                    .start()

                binding.daysRemaining.text = "Days remaining:\n" + (habitObject.habitprogress.toInt() - habitObject.habitprogressnow.toInt())

            }



        }


        fun showDialog(dialogFragment: DialogFragment) {
            val fragmentManager = parentFragmentManager
            val newFragment = dialogFragment

            val transaction = fragmentManager.beginTransaction()

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction
                .add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit()

        }
        binding.createHabitButton.setOnClickListener {
            showDialog(CustomDialogFragment())

        }
        habitRecyclerviewAdapter.setOnItemClickListener(object :
            HabitRecyclerviewAdapter.onItemClickListener {
            override fun onItemClick(position: Int): String {
                showDialog(HabitDialog(position))
                return position.toString()
            }

        }

        )



    }

}

