package com.saba.spark

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity):FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("ragac","gaaketa man igi")
        return when(position){
            0 ->badHabits()
            1->GoodHabits()
            2->UglyHabit()
            else -> badHabits()
        }
    }


}