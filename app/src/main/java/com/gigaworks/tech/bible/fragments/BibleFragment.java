package com.gigaworks.tech.bible.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gigaworks.tech.bible.AppPreferences;
import com.gigaworks.tech.bible.AudioBookActivity;
import com.gigaworks.tech.bible.BooksActivity;
import com.gigaworks.tech.bible.Constants;
import com.gigaworks.tech.bible.DailyActivity;
import com.gigaworks.tech.bible.DailyDevotionalActivity;
import com.gigaworks.tech.bible.R;
import com.gigaworks.tech.bible.YeshuActivity;
import com.gigaworks.tech.bible.adapter.TitleAdapter;

import java.util.ArrayList;

public class BibleFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TitleAdapter adapter;


    public BibleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bible, container, false);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.rv_home);

        recyclerView.setVisibility(View.VISIBLE);
        adapter = new TitleAdapter(getBookTitles(), (position, title) -> {
            if (title.equals(Constants.getAudioBooks())) {
                Intent intent = new Intent(getActivity(), AudioBookActivity.class);
                startActivity(intent);
            } else if (title.equals(Constants.getGujaratiBible())) {
                Intent intent = new Intent(getActivity(), BooksActivity.class);
                startActivity(intent);
            } else if (title.equals(Constants.getDailyDevotional())) {
                Intent intent = new Intent(getActivity(), DailyDevotionalActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    private ArrayList<String> getBookTitles() {

        ArrayList<String> list = new ArrayList<>();
        list.add(Constants.getGujaratiBible());
        list.add(Constants.getAudioBooks());
        list.add(Constants.getDailyDevotional());
        return list;
    }

}
