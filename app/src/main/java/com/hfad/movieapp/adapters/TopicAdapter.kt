package com.hfad.movieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hfad.movieapp.R
import com.hfad.movieapp.models.Topic

class TopicAdapter :
    RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    var onItemClick: ((Topic) -> Unit)? = null
    private var topics: MutableList<Topic> = initializeNavBar()

    inner class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.topic_name)
        val imageView: ImageView = itemView.findViewById(R.id.topic_nav_guide)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.topic_item, parent, false)

        return TopicViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val currentTopic = topics[position]
        val textView = holder.textView
        textView.text = currentTopic.name

        if (currentTopic.navGuideVisible) {
            holder.imageView.visibility = ImageView.VISIBLE
        } else {
            holder.imageView.visibility = ImageView.INVISIBLE
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentTopic)
        }

    }

    override fun getItemCount(): Int {
        return topics.size
    }

    fun updateNavGuideSelectedItem(topic: Topic) {
        topic.navGuideVisible = true
        topics[topic.position] = topic
        notifyItemChanged(topic.position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun cleanNavGuide() {
        topics = initializeUnselectedNav()
        this.notifyDataSetChanged()
    }

    private fun initializeNavBar(): MutableList<Topic> {
        return mutableListOf(
            Topic("Populares", true, 0),
            Topic("Em cartaz", false, 1),
            Topic("Aclamados pela crítica", false, 2)
        )
    }

    private fun initializeUnselectedNav(): MutableList<Topic> {
        return mutableListOf(
            Topic("Populares", false, 0),
            Topic("Em cartaz", false, 1),
            Topic("Aclamados pela crítica", false, 2)
        )
    }
}