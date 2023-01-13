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

class ShareRecyclerViewAdapter(val context: Context, val sharedAchievement:ArrayList<SharedAchievement>):
    RecyclerView.Adapter<ShareRecyclerViewAdapter.ShareViewHolder>() {

    class ShareViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val profileImg = itemView.findViewById<ImageView>(R.id.circleProfileImg)
        val postername = itemView.findViewById<TextView>(R.id.name_poster)
        val time = itemView.findViewById<TextView>(R.id.time)
        val date = itemView.findViewById<TextView>(R.id.date)
        val achievement = itemView.findViewById<TextView>(R.id.achievement)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShareViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.share_item,parent,false)
        return ShareViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShareViewHolder, position: Int) {
        val sharedItem = sharedAchievement[position]
        holder.postername.text = sharedItem.posterName
        holder.achievement.text = sharedItem.achievement
        holder.date.text = sharedItem.date
        holder.time.text = sharedItem.time
        Glide.with(context)
            .load(sharedItem.profileImg)
            .fitCenter()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.profileImg)
    }

    override fun getItemCount(): Int {
        return sharedAchievement.size
    }
}