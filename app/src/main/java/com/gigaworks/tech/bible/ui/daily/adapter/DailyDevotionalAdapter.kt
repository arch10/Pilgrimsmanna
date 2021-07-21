package com.gigaworks.tech.bible.ui.daily.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gigaworks.tech.bible.R
import com.gigaworks.tech.bible.databinding.AudioBookItemBinding
import com.gigaworks.tech.bible.databinding.CardItemBinding
import com.gigaworks.tech.bible.domain.Sound
import com.gigaworks.tech.bible.util.getSoundUrl

class DailyDevotionalAdapter(
    private val bookList: List<Sound>,
    private val clickListener: OnClickListener
) : RecyclerView.Adapter<DailyDevotionalAdapter.DailyDevotionalViewHolder>() {

    interface OnClickListener {
        fun onClick(sound: Sound)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyDevotionalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AudioBookItemBinding.inflate(layoutInflater, parent, false)
        return DailyDevotionalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyDevotionalViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount() = bookList.size


    inner class DailyDevotionalViewHolder(private val binding: AudioBookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sound: Sound) {
            binding.title.text = sound.mainCategory
            binding.subtitle.text = "Daily Devotional"
            if(!sound.image.isNullOrEmpty()) {
                val imgUrl = getSoundUrl(sound.image)
                Glide.with(binding.root)
                    .load(imgUrl)
                    .placeholder(R.drawable.bg)
                    .into(binding.bookImg)
            }
            binding.root.setOnClickListener {
                clickListener.onClick(sound)
            }
        }

    }
}