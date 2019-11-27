package com.gigaworks.tech.bible.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigaworks.tech.bible.R;
import com.gigaworks.tech.bible.SettingsActivity;
import com.gigaworks.tech.bible.container.ListData;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.SettingViewHolder> {

    private ArrayList<ListData> list;
    private ListAdapter.OnSettingClickListener listener;

    public interface OnSettingClickListener {
        void OnSettingClick(ListData data, int position);
    }

    public ListAdapter(SettingsActivity settingsActivity, ArrayList<ListData> list, OnSettingClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public void setList(ArrayList<ListData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.setting_list_layout, parent, false);
        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder holder, int position) {
        ListData data = list.get(position);

        holder.title.setText(data.getTitle());
        holder.body.setText(data.getBody());
        holder.icon.setImageResource(data.getImg());
        holder.bind(data, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class SettingViewHolder extends RecyclerView.ViewHolder {

        TextView title, body;
        ImageView icon;

        SettingViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.desc);
            icon = itemView.findViewById(R.id.iv_icon);
        }

        void bind(final ListData data, final ListAdapter.OnSettingClickListener listener) {
            itemView.setOnClickListener(v -> listener.OnSettingClick(data, getAdapterPosition()));
        }
    }

}