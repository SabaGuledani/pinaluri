package com.saba.spark

import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.saba.spark.databinding.FragmentBadHabitsBinding
import com.saba.spark.databinding.HabitCreateDialogBinding
import com.saba.spark.databinding.HabitOpenDialogFragmentBinding
import java.time.LocalDateTime
import java.util.Calendar
import java.util.concurrent.TimeUnit


class badHabits : Fragment(R.layout.fragment_bad_habits) {
    private lateinit var binding: FragmentBadHabitsBinding
    private lateinit var habitList: ArrayList<Habit>
    private lateinit var dbref: DatabaseReference


    private lateinit var habitRecyclerviewAdapter: HabitRecyclerviewAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBadHabitsBinding.bind(view)


        var calendar = Calendar.getInstance()
        var today =
            ("${calendar.get(Calendar.YEAR)}" + "${calendar.get(Calendar.DAY_OF_YEAR)}").toInt()
        var sharedPrefs = context?.getSharedPreferences("date", Context.MODE_PRIVATE)
        var editor = sharedPrefs?.edit()

        var useruid = FirebaseAuth.getInstance().currentUser?.uid

        val recyclerview = binding.recyclerviewHabit
        recyclerview.layoutManager = LinearLayoutManager(context)
        habitList = ArrayList()

        val swipeGesture = object : SwipeGesture(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        var habitObjectRemove = habitRecyclerviewAdapter.getItem(viewHolder.bindingAdapterPosition)
                        editor?.remove("today day: ${habitObjectRemove.habitName}")


                        habitRecyclerviewAdapter.deleteItem(viewHolder.bindingAdapterPosition)

                    }
                }

            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerview)




        dbref = FirebaseDatabase.getInstance().getReference()
        habitRecyclerviewAdapter = HabitRecyclerviewAdapter(habitList)
        recyclerview.adapter = habitRecyclerviewAdapter


        dbref.child("users").child("$useruid").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                habitList.clear()
                for (postsnapshot in snapshot.children) {
                    val habitobject = postsnapshot.getValue(Habit::class.java)
                    if (habitobject != null) {
                        if (today - sharedPrefs?.getInt(
                                "today day ${habitobject.habitName}",
                                1
                            )!! >= 2
                        ) {
                            if (habitobject != null) {
                                habitobject.status = "inactive"
                                dbref.child("users").child(useruid.toString())
                                    .child(habitobject.habitName).child("status")
                                    .setValue("inactive")
                                Log.d("tag", "habit")
                            }
                        }
                    }
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
                    val status = "active"
                    val progressnow = "0"
                    if (habitName.isNotEmpty() && habitProgress.isNotEmpty() && dailyUseMoney.isNotEmpty()) {

                        val habitObject =
                            Habit(habitName, habitProgress, dailyUseMoney, status, progressnow)

                        dbref.child("users").child("$useruid").child(habitName)
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

                var progressbar = binding.circularProgressIndicator
                dbref.child("users").child("$useruid").child("${habitObject.habitName}")
                    .child("habitprogressnow").addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            binding.progressTV.text =
                                snapshot.value.toString() + "/" + habitObject.habitprogress
                            binding.daysRemaining.text =
                                "Days remaining: " + (habitObject.habitprogress.toInt() - snapshot.value.toString()
                                    .toInt()).toString()

                            progressbar.max = habitObject.habitprogress.toInt() * 1000
                            ObjectAnimator.ofInt(
                                progressbar, "progress", (snapshot.value.toString().toInt()) * 1000
                            ).setDuration(1700).start()
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                binding.habitName.text = habitObject.habitName
                binding.progressTV.text =
                    habitObject.habitprogressnow + "/" + habitObject.habitprogress

                binding.statusTV.text = "status: " + habitObject.status

                progressbar.max = habitObject.habitprogress.toInt() * 1000
                ObjectAnimator.ofInt(
                    progressbar, "progress", (habitObject.habitprogressnow.toInt()) * 1000
                ).setDuration(1700).start()

                binding.daysRemaining.text =
                    "Days remaining: " + (habitObject.habitprogress.toInt() - habitObject.habitprogressnow.toInt())

                binding.progressTV.setOnLongClickListener {
                    if (binding.statusTV.text == "status: inactive") {
                        Toast.makeText(context, "hey maybe next time!", Toast.LENGTH_SHORT).show()
                    } else {

                        var lastTime = sharedPrefs?.getInt("today day ${habitObject.habitName}", 1)



                        if (today != lastTime && habitObject.habitprogressnow != habitObject.habitprogress) {

                            dbref.child("users").child("$useruid").child("${habitObject.habitName}")
                                .child("habitprogressnow")
                                .setValue((habitObject.habitprogressnow.toInt() + 1).toString())
                            editor?.putInt("today day ${habitObject.habitName}", today)
                            editor?.apply()

                        } else {
                            if (today == lastTime && habitObject.habitprogressnow != habitObject.habitprogress) {
                                Toast.makeText(
                                    context,
                                    "hey you already clicked today",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else {
                                dbref.child("users").child("$useruid")
                                    .child("${habitObject.habitName}")
                                    .child("status").setValue("completed")
                                binding.statusTV.text = "status: completed"

                                Toast.makeText(
                                    context,
                                    "you successfully achieved your goal good job!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                    true

                }
                binding.closeDialog.setOnClickListener {
                    dismiss()
                }


            }


        }


        fun showDialog(dialogFragment: DialogFragment) {
            val fragmentManager = parentFragmentManager
            val newFragment = dialogFragment

            val transaction = fragmentManager.beginTransaction()

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit()

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

