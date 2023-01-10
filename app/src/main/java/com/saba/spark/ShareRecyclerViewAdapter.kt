package com.saba.spark

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShareRecyclerViewAdapter(val sharedAchievement:ArrayList<SharedAchievement>):
    RecyclerView.Adapter<ShareRecyclerViewAdapter.HabitViewHolder>() {

    class HabitViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val profileImg = itemView.findViewById<ImageView>(R.id.circleProfileImg)
        val postername = itemView.findViewById<TextView>(R.id.name_poster)
        val time = itemView.findViewById<TextView>(R.id.time)
        val date = itemView.findViewById<TextView>(R.id.date)
        val achievement = itemView.findViewById<TextView>(R.id.achievement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return sharedAchievement.size
    }
}