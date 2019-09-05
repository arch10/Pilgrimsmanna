package com.gigaworks.tech.bible.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigaworks.tech.bible.R;
import com.gigaworks.tech.bible.container.DailyRead;

import java.util.ArrayList;

public class DailyReadAdapter extends RecyclerView.Adapter<DailyReadAdapter.DailyReadViewHolder> {

    private ArrayList<DailyRead> titleArrayList;
    private OnMonthClickListener listener;


    public interface OnMonthClickListener {
        void onMonthClick(DailyRead main, int position);
    }

    public DailyReadAdapter(ArrayList<DailyRead> titleArrayList, OnMonthClickListener listener){
        this.titleArrayList = titleArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DailyReadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.main_title_layout, viewGroup, false);
        DailyReadViewHolder holder = new DailyReadViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyReadViewHolder dailyReadViewHolder, int i) {
        DailyRead title = titleArrayList.get(i);
        dailyReadViewHolder.title.setText(title.getTitle());
        dailyReadViewHolder.bind(title, listener);
    }

    @Override
    public int getItemCount() {
        return titleArrayList.size();
    }

    class DailyReadViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        public DailyReadViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }

        public void bind(final DailyRead dailyRead, final OnMonthClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onMonthClick(dailyRead, getAdapterPosition());
                }
            });
        }
    }
}
