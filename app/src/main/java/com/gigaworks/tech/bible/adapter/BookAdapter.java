package com.gigaworks.tech.bible.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigaworks.tech.bible.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<String> bookList;
    private OnBookClickListener listener;

    public BookAdapter(ArrayList<String> bookList, OnBookClickListener listener) {
        this.bookList = bookList;
        this.listener = listener;
    }

    public interface OnBookClickListener {
        void onBookClick(int position, String title);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.book_layout, parent, false);
        BookViewHolder holder = new BookViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.title.setText(bookList.get(position));
        holder.bind(listener, bookList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public BookViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }

        public void bind(final OnBookClickListener listener, final String title) {
            itemView.setOnClickListener((view) -> listener.onBookClick(getAdapterPosition(), title));
        }
    }
}
