package com.saba.spark

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.saba.spark.databinding.DialogShareAchievementBinding
import com.saba.spark.databinding.FragmentUglyHabitBinding
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class UglyHabit : Fragment(R.layout.fragment_ugly_habit) {
    private lateinit var binding: FragmentUglyHabitBinding
    private lateinit var shareList: ArrayList<SharedAchievement>
    private lateinit var dbref: DatabaseReference
    private lateinit var shareRecyclerViewAdapter: ShareRecyclerViewAdapter
    private lateinit var habitList:ArrayList<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUglyHabitBinding.bind(view)
        shareList = ArrayList()
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        val recyclerView = binding.recyclerViewShare
        var linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager
        shareRecyclerViewAdapter = ShareRecyclerViewAdapter(requireContext(), shareList)
        recyclerView.adapter = shareRecyclerViewAdapter
        dbref = FirebaseDatabase.getInstance().getReference()
        habitList = ArrayList()
        val storageRef = Firebase.storage.reference



        storageRef.child("images/${senderuid}.jpg").downloadUrl.addOnSuccessListener {
            Glide.with(requireActivity())
                .load(it)
                .circleCrop()
                .into(binding.circleimg)
        }


        dbref.child("share").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                shareList.clear()
                for (postsnapshot in snapshot.children) {
                    val shareObject = postsnapshot.getValue(SharedAchievement::class.java)
                    shareList.add(shareObject!!)
                }
                shareRecyclerViewAdapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(shareList.size - 1 )

            }

            override fun onCancelled(error: DatabaseError) {
                var miki  = "miki"
                var mausi = "mausi"
                var mikimausi = miki+mausi

            }

        })
        var shareTi = binding.shareTi
        
        
        class ShareEditDialog : DialogFragment() {
            private lateinit var binding: DialogShareAchievementBinding
            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
            ): View? {
                binding = DialogShareAchievementBinding.inflate(inflater, container, false)
                return binding.root
            }

            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)
                dbref.child("profile").child(senderuid.toString()).addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var profileObject = snapshot.getValue(userProfile::class.java)
                        if(profileObject != null){

                            binding.namePoster.text = profileObject.profileName
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        var miki  = "miki"
                        var mausi = "mausi"
                        var mikimausi = miki+mausi
                    }
                })
                storageRef.child("images/${senderuid}.jpg").downloadUrl.addOnSuccessListener {
                    Glide.with(requireActivity())
                        .load(it)
                        .circleCrop()
                        .into(binding.circleImg)
                }


                var adapter = ArrayAdapter<String>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,habitList)
                binding.spinnerHabit.adapter = adapter
                dbref.child("users").child(senderuid.toString()).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        habitList.clear()
                        habitList.add("")
                        for (postsnapshot in snapshot.children) {
                            val habitobject = postsnapshot.getValue(Habit::class.java)
                            if (habitobject != null && habitobject.habitprogressnow == habitobject.habitprogress) {
                                habitList.add(habitobject.habitName)
                            }
                        }


                        adapter.notifyDataSetChanged()


                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })



                var selectedHabit = ""
                binding.spinnerHabit.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        adapterView: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectedHabit = adapterView?.getItemAtPosition(position).toString()

                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }


                binding.sharebtn.setOnClickListener {
                    val timeglobal = Calendar.getInstance().time
                    val formatter = SimpleDateFormat("HH:mm")
                    var formatterDate = SimpleDateFormat("yyyy-MM-dd")

                    val time = formatter.format(timeglobal)
                    val date = formatterDate.format(timeglobal)


                    dbref.child("profile").child("$senderuid").get()
                        .addOnSuccessListener {
                            if (it.exists()) {
                                val senderuid = senderuid
                                val posterImg = it.child("profileImg").value.toString()
                                val posterName = it.child("profileName").value.toString()
                                val time = time
                                val date = date
                                var posttext = binding.postText.editText?.text.toString()
                                var select = binding.spinnerHabit.selectedItem.toString()


                                val shareObject = SharedAchievement(
                                    senderuid,
                                    posterImg,
                                    posterName,
                                    time,
                                    date,
                                    posttext,
                                    select

                                )

                                dbref.child("share").push().setValue(shareObject)

                                dismiss()
                                shareTi.clearFocus()
                            }
                        }
                }

                binding.exitIV.setOnClickListener {
                    dismiss()
                    shareTi.clearFocus()
                }
            }
        }
        shareTi.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                showDialog(ShareEditDialog())
            }
        }


//Xoda reference.child.downloadurl.addonsuccesslistener{ } gamoviyene
        // TODO: iyos es aq
    }

    fun showDialog(dialogFragment: DialogFragment) {
        val fragmentManager = parentFragmentManager
        val newFragment = dialogFragment

        val transaction = fragmentManager.beginTransaction()

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit()

    }
}

