package com.gigaworks.tech.bible.ui.audiobook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gigaworks.tech.bible.R
import com.gigaworks.tech.bible.databinding.AudioBookItemBinding
import com.gigaworks.tech.bible.databinding.CardItemBinding
import com.gigaworks.tech.bible.domain.Sound
import com.gigaworks.tech.bible.util.getSoundUrl

class AudioBookAdapter(
    private val bookList: List<Sound>,
    private val clickListener: OnAudioBookClickListener
) : RecyclerView.Adapter<AudioBookAdapter.BookViewHolder>() {

    interface OnAudioBookClickListener {
        fun onAudioBookClick(book: Sound)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AudioBookItemBinding.inflate(layoutInflater, parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount() = bookList.size


    inner class BookViewHolder(private val binding: AudioBookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Sound) {
            binding.title.text = book.title
            binding.subtitle.text = "Audio Book"

            if(!book.image.isNullOrEmpty()) {
                val imgUrl = getSoundUrl(book.image)
                Glide.with(binding.root)
                    .load(imgUrl)
                    .placeholder(R.drawable.bg)
                    .into(binding.bookImg)
            }
            binding.root.setOnClickListener {
                clickListener.onAudioBookClick(book)
            }
        }

    }
}