package com.saba.spark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.saba.spark.databinding.FragmentUglyHabitBinding
import kotlinx.coroutines.NonDisposableHandle.parent


class UglyHabit : Fragment(R.layout.fragment_ugly_habit) {
    private lateinit var binding:FragmentUglyHabitBinding
    private lateinit var shareList:ArrayList<SharedAchievement>
    private lateinit var dbref:DatabaseReference
    private lateinit var shareRecyclerViewAdapter:ShareRecyclerViewAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUglyHabitBinding.bind(view)

/*
        val recyclerView = binding.recyclerViewShare
        recyclerView.layoutManager = LinearLayoutManager(context)
        shareRecyclerViewAdapter = ShareRecyclerViewAdapter(requireContext(),shareList)
        recyclerView.adapter = shareRecyclerViewAdapter

        //dbref.child()


 */

    }

}