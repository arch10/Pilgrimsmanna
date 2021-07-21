package com.gigaworks.tech.bible.ui.yeshu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gigaworks.tech.bible.R
import com.gigaworks.tech.bible.databinding.AudioBookItemBinding
import com.gigaworks.tech.bible.domain.Sound
import com.gigaworks.tech.bible.util.getSoundUrl

class YeshuListAdapter(
    private val yeshuList: List<Sound>,
    private val clickListener: OnYeshuListItemClickListener
) : RecyclerView.Adapter<YeshuListAdapter.YeshuListViewHolder>() {

    interface OnYeshuListItemClickListener {
        fun onItemClick(sound: Sound)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YeshuListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AudioBookItemBinding.inflate(layoutInflater, parent, false)
        return YeshuListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YeshuListViewHolder, position: Int) {
        holder.bind(yeshuList[position])
    }

    override fun getItemCount() = yeshuList.size


    inner class YeshuListViewHolder(private val binding: AudioBookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sound: Sound) {
            binding.title.text = sound.mainCategory
            binding.subtitle.text = "More"

            if(!sound.image.isNullOrEmpty()) {
                val imgUrl = getSoundUrl(sound.image)
                Glide.with(binding.root)
                    .load(imgUrl)
                    .placeholder(R.drawable.bg)
                    .into(binding.bookImg)
            }
            binding.root.setOnClickListener {
                clickListener.onItemClick(sound)
            }
        }

    }
}