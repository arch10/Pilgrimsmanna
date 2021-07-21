package com.gigaworks.tech.bible.ui.yeshu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gigaworks.tech.bible.databinding.DailyReadItemBinding
import com.gigaworks.tech.bible.domain.Sound

class YeshuItemAdapter(
    private val yeshuList: List<Sound>,
    private val clickListener: OnClickListener
) : RecyclerView.Adapter<YeshuItemAdapter.YeshuItemViewHolder>() {

    interface OnClickListener {
        fun onClick(sound: Sound, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YeshuItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DailyReadItemBinding.inflate(inflater, parent, false)
        return YeshuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YeshuItemViewHolder, position: Int) =
        holder.bind(yeshuList[position], position)

    override fun getItemCount() = yeshuList.size

    inner class YeshuItemViewHolder(private val binding: DailyReadItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sound: Sound, position: Int) {
            binding.title.text = sound.title
            binding.root.setOnClickListener {
                clickListener.onClick(sound, position)
            }
        }

    }

}