package com.gigaworks.tech.bible.ui.bible.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gigaworks.tech.bible.databinding.CardItemBinding
import com.gigaworks.tech.bible.domain.Book

class BookAdapter(
    private val bookList: List<Book>,
    private val clickListener: OnBookClickListener
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    interface OnBookClickListener {
        fun onBookClick(book: Book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardItemBinding.inflate(layoutInflater, parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount() = bookList.size


    inner class BookViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.title.text = book.title
            binding.root.setOnClickListener {
                clickListener.onBookClick(book)
            }
        }

    }
}