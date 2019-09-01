package com.gigaworks.tech.bible.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigaworks.tech.bible.R;

import java.util.ArrayList;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.TitleViewHolder>  {

    private ArrayList<String> titleList;
    private OnTitleClickListener listener;

    public interface OnTitleClickListener {
        void onTitleClick(int position, String title);
    }

    public TitleAdapter(ArrayList<String> titleList, OnTitleClickListener listener) {
        this.titleList = titleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TitleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.book_layout, viewGroup, false);
        TitleViewHolder holder = new TitleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TitleViewHolder holder, int i) {
        holder.title.setText(titleList.get(i));
        holder.bind(listener, titleList.get(i));
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }

        public void bind(final OnTitleClickListener listener, final String title) {
            itemView.setOnClickListener((view) -> listener.onTitleClick(getAdapterPosition(), title));
        }
    }

}
