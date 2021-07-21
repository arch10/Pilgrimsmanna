package com.gigaworks.tech.bible.ui.daily.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gigaworks.tech.bible.databinding.DailyReadItemBinding

class DailyReadAdapter(
    private val dailyReads: List<String>,
    private val clickListener: OnClickListener
) : RecyclerView.Adapter<DailyReadAdapter.DailyReadViewHolder>() {

    interface OnClickListener {
        fun onClick(string: String, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyReadViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DailyReadItemBinding.inflate(inflater, parent, false)
        return DailyReadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyReadViewHolder, position: Int) =
        holder.bind(dailyReads[position], position)

    override fun getItemCount() = dailyReads.size

    inner class DailyReadViewHolder(private val binding: DailyReadItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String, position: Int) {
            binding.title.text = title
            binding.root.setOnClickListener {
                clickListener.onClick(title, position)
            }
        }

    }

}