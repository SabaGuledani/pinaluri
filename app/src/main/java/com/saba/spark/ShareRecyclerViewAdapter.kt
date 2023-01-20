package com.saba.spark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ShareRecyclerViewAdapter(val context: Context, val sharedAchievement:ArrayList<SharedAchievement>):
    RecyclerView.Adapter<ShareRecyclerViewAdapter.ShareViewHolder>() {

    class ShareViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val profileImg = itemView.findViewById<ImageView>(R.id.circleProfileImg)
        val postername = itemView.findViewById<TextView>(R.id.name_poster)
        val time = itemView.findViewById<TextView>(R.id.time)
        val date = itemView.findViewById<TextView>(R.id.date)
        val achievement = itemView.findViewById<TextView>(R.id.achievement)
        val achievedGoal = itemView.findViewById<TextView>(R.id.achieved_goal)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShareViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.share_item,parent,false)
        return ShareViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShareViewHolder, position: Int) {
        val sharedItem = sharedAchievement[position]
        var senderuid = sharedItem.senderuid
        val storageRef = Firebase.storage.reference

        holder.postername.text = sharedItem.posterName
        holder.achievement.text = sharedItem.achievement
        holder.date.text = sharedItem.date
        holder.time.text = sharedItem.time
        if(sharedItem.selectedHabit != ""){
            holder.achievedGoal.text = "achieved desired goal: "+ sharedItem.selectedHabit
            holder.achievedGoal.visibility = View.VISIBLE
        }else{
            holder.achievedGoal.visibility = View.GONE
        }
        if(sharedItem.achievement == ""){
            holder.achievement.visibility = View.GONE
        }else{
            holder.achievement.visibility = View.VISIBLE
        }
        storageRef.child("images/${senderuid}.jpg").downloadUrl.addOnSuccessListener {
            Glide.with(context)
                .load(it)
                .circleCrop()
                .into(holder.profileImg)
        }

    }

    override fun getItemCount(): Int {
        return sharedAchievement.size
    }
}