package com.saba.spark

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitRecyclerviewAdapter(val habitList:ArrayList<Habit>):
RecyclerView.Adapter<HabitRecyclerviewAdapter.HabitViewHolder>(){

    class HabitViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val habitName = itemView.findViewById<TextView>(R.id.habit_name)
        val habitProgress = itemView.findViewById<TextView>(R.id.habit_progress)
        val status = itemView.findViewById<TextView>(R.id.active_status)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.goal_layout,parent,false)
        return HabitViewHolder(view)
    }
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentHabit = habitList[position]
        holder.habitName.text = currentHabit.habitName
        holder.habitProgress.text = currentHabit.habitprogress
        holder.status.text = currentHabit.status
    }


    override fun getItemCount(): Int {
        return habitList.size
    }




}