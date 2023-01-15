package com.saba.spark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.saba.spark.databinding.FragmentUglyHabitBinding
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class UglyHabit : Fragment(R.layout.fragment_ugly_habit) {
    private lateinit var binding:FragmentUglyHabitBinding
    private lateinit var shareList:ArrayList<SharedAchievement>
    private lateinit var dbref:DatabaseReference
    private lateinit var shareRecyclerViewAdapter:ShareRecyclerViewAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUglyHabitBinding.bind(view)
        shareList = ArrayList()
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        val recyclerView = binding.recyclerViewShare
        recyclerView.layoutManager = LinearLayoutManager(context)
        shareRecyclerViewAdapter = ShareRecyclerViewAdapter(requireContext(),shareList)
        recyclerView.adapter = shareRecyclerViewAdapter
        dbref = FirebaseDatabase.getInstance().getReference()




        dbref.child("share").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                shareList.clear()
                for (postsnapshot in snapshot.children){
                    val shareObject = postsnapshot.getValue(SharedAchievement::class.java)
                    shareList.add(shareObject!!)
                }
                shareRecyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.publishImgBtn.setOnClickListener {
            val timeglobal = Calendar.getInstance().time
            val formatter = SimpleDateFormat("HH:mm")
            var formatterDate = SimpleDateFormat("yyyy-MM-dd")

            val time = formatter.format(timeglobal)
            val date = formatterDate.format(timeglobal)


            dbref.child("profile").child("$senderuid").get()
                .addOnSuccessListener {
                    if(it.exists()){
                        val senderuid = senderuid
                        val posterImg = it.child("profileImg").value.toString()
                        val posterName = it.child(" profileName").value.toString()
                        val time = time
                        val date = date
                        val achievement = binding.habitet.editText?.text.toString()

                        val shareObject = SharedAchievement(senderuid,posterImg,posterName,time, date, achievement)

                        dbref.child("share").push().setValue(shareObject)


                    }
                }




        }




    }

}