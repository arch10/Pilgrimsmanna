package com.gigaworks.tech.bible.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.gigaworks.tech.bible.AppPreferences;
import com.gigaworks.tech.bible.MainActivity;
import com.gigaworks.tech.bible.R;
import com.gigaworks.tech.bible.adapter.DailyReadAdapter;
import com.gigaworks.tech.bible.container.DailyRead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class YeshuFragment extends Fragment {

    private RecyclerView recyclerView;
    private DailyReadAdapter adapter;
    private LinearLayoutManager manager;
    private AppPreferences preferences;
    private ArrayList<DailyRead> months;

    public YeshuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        months = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yeshu, container, false);

        preferences = AppPreferences.getInstance(getActivity());
        recyclerView = view.findViewById(R.id.rv_daily);

        months = getMonthData("part1");

        adapter = new DailyReadAdapter(months, getActivity(), new DailyReadAdapter.OnMonthClickListener() {
            @Override
            public void onMonthClick(DailyRead main, int position) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.putExtra("soundUrl",main.getUrl());
                intent.putExtra("trackTitle",main.getTitle());
                intent.putExtra("category",main.getCategory());
                intent.putExtra("pos",position);
                startActivity(intent);
            }
        });

        manager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        return view;
    }

    private ArrayList<DailyRead> getMonthData(String category) {
        String response = preferences.getStringPreference(AppPreferences.APP_SOUND_RESPONSE);

        ArrayList<DailyRead> arrayList = new ArrayList<>();

        if(response.equals(""))
            return arrayList;

        try {
            JSONArray array = new JSONArray(response);
            for (int i=0; i<array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                if(jsonObject.getString("category").equals(category)){
                    int id = Integer.parseInt(jsonObject.getString("id"));
                    String title = jsonObject.getString("title");
                    String url = jsonObject.getString("soundfile");
                    DailyRead read = new DailyRead(title,id,url,category);
                    arrayList.add(read);
                }
            }

            return arrayList;

        }catch (JSONException e){
            return arrayList;
        }
    }
}
