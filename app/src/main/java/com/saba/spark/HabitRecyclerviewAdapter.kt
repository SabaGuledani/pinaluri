package com.saba.spark

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.view.marginRight
import androidx.core.view.setMargins
import androidx.core.view.updateLayoutParams
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
        var status = itemView.findViewById<TextView>(R.id.active_status)
        var progressBar = itemView.findViewById<ProgressBar>(R.id.habit_progress_bar)
        var daysRemaining = itemView.findViewById<TextView>(R.id.days_remaining)
        var statusText = itemView.findViewById<TextView>(R.id.status)




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
        if(holder.status.text.toString() == "completed"){
            val param = ((holder.statusText).layoutParams as MarginLayoutParams)
            param.setMargins(0,0,100,0)
            holder.statusText.layoutParams = param
            Log.d("racxa","rac")
        }else{
            val param = ((holder.statusText).layoutParams as MarginLayoutParams)
            param.setMargins(0,0,0,0)
            holder.statusText.layoutParams = param
        }
        holder.progressBar.max = currentHabit.habitprogress.toInt() * 1000
        ObjectAnimator.ofInt(holder.progressBar,"progress",(currentHabit.habitprogressnow.toInt())*1000)
            .setDuration(1400)
            .start()

        holder.daysRemaining.text = "Days remaining: " + (currentHabit.habitprogress.toInt() - currentHabit.habitprogressnow.toInt())


    }


    override fun getItemCount(): Int {
        return habitList.size
    }




}