package com.saba.spark

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitRecyclerviewAdapter(val habitList:ArrayList<Habit>):
RecyclerView.Adapter<HabitRecyclerviewAdapter.HabitViewHolder>(){

    private lateinit var mlistener:onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int):String?
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistener = listener
    }

    class HabitViewHolder(itemView:View,listener:onItemClickListener):RecyclerView.ViewHolder(itemView){
        val habitName = itemView.findViewById<TextView>(R.id.habit_name)
        val habitProgress = itemView.findViewById<TextView>(R.id.habit_progress)
        val status = itemView.findViewById<TextView>(R.id.active_status)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.goal_layout,parent,false)
        return HabitViewHolder(view,mlistener)
    }
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentHabit = habitList[position]
        holder.habitName.text = currentHabit.habitName
        holder.habitProgress.text = currentHabit.habitprogressnow.toString()+ "/" + currentHabit.habitprogress.toString()
        holder.status.text = currentHabit.status
    }


    override fun getItemCount(): Int {
        return habitList.size
    }




}