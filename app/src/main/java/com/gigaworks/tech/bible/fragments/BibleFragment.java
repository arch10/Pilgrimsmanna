package com.gigaworks.tech.bible.fragments;


import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gigaworks.tech.bible.AppPreferences;
import com.gigaworks.tech.bible.BibleRead;
import com.gigaworks.tech.bible.Constants;
import com.gigaworks.tech.bible.DailyActivity;
import com.gigaworks.tech.bible.MainActivity;
import com.gigaworks.tech.bible.R;
import com.gigaworks.tech.bible.YeshuActivity;
import com.gigaworks.tech.bible.adapter.BookAdapter;
import com.gigaworks.tech.bible.viewmodel.HomeViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BibleFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BookAdapter adapter;
    private ProgressBar progressBar;
    private AppPreferences preferences;


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

        preferences = AppPreferences.getInstance(getActivity());
        progressBar = view.findViewById(R.id.pb_home);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.rv_home);

        HomeViewModel model = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        model.getBookData(preferences.getStringPreference(AppPreferences.APP_BOOK_RESPONSE))
                .observe(getActivity(), data -> {
                    if (data.equals("")) {
                        progressBar.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter = new BookAdapter(getBookTitles(data), (position, title) -> {
                            if (title.equals(Constants.getYeshuKaJeevan())) {
                                Intent intent = new Intent(getActivity(), YeshuActivity.class);
                                startActivity(intent);
                            } else if (title.equals(Constants.getMonthlyBread())) {
                                Intent intent = new Intent(getActivity(), DailyActivity.class);
                                startActivity(intent);
                            } else {
                                JSONArray array = getBook(data, title);
                                Intent intent = new Intent(getActivity(), BibleRead.class);
                                intent.putExtra(Constants.BOOK_TITLE, title);
                                intent.putExtra(Constants.BOOK_DATA, array.toString());
                                intent.putExtra(Constants.BOOK_CHAPTERS, array.length());
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                });


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    private JSONArray getBook(String data, String title) {
        JSONArray array = new JSONArray();

        try {
            JSONArray list = new JSONArray(data);
            for(int i=0; i<list.length(); i++) {
                JSONObject object = list.getJSONObject(i);
                if(object.getString("title").equals(title)){
                    array.put(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    private ArrayList<String> getBookTitles(String data) {
        String bookResponse = data;

        try {
            ArrayList<String> list = new ArrayList<>();
            JSONArray array = new JSONArray(bookResponse);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String title = object.getString("title");
                if (!list.contains(title)) {
                    list.add(title);
                }
            }

            list.add(Constants.getYeshuKaJeevan());
            list.add(Constants.getMonthlyBread());

            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
