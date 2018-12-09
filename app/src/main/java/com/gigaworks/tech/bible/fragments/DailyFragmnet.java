package com.gigaworks.tech.bible.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gigaworks.tech.bible.AppPreferences;
import com.gigaworks.tech.bible.MainActivity;
import com.gigaworks.tech.bible.R;
import com.gigaworks.tech.bible.adapter.DailyReadAdapter;
import com.gigaworks.tech.bible.container.DailyRead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DailyFragmnet extends Fragment {

    private RecyclerView recyclerView;
    private DailyReadAdapter adapter;
    private LinearLayoutManager manager;
    private Spinner monthSpinner;
    private AppPreferences preferences;

    private ArrayList<DailyRead> months;

    public DailyFragmnet() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        months = new ArrayList<>();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily_fragmnet, container, false);

        monthSpinner = view.findViewById(R.id.sp_month);
        preferences = AppPreferences.getInstance(getActivity());
        recyclerView = view.findViewById(R.id.rv_daily);
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

        monthSpinner.setSelection(0);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = monthSpinner.getSelectedItem().toString().trim();
                months = getMonthData(selectedItem);
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
                recyclerView.swapAdapter(adapter,true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }



    private ArrayList<DailyRead> getMonthData(String month) {
        String response = preferences.getStringPreference(AppPreferences.APP_SOUND_RESPONSE);

        ArrayList<DailyRead> arrayList = new ArrayList<>();

        if(response.equals(""))
            return arrayList;

        try {
            JSONArray array = new JSONArray(response);
            for (int i=0; i<array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                if(jsonObject.getString("category").equals(month)){
                    int id = Integer.parseInt(jsonObject.getString("id"));
                    String title = jsonObject.getString("title");
                    String url = jsonObject.getString("soundfile");
                    DailyRead read = new DailyRead(title,id,url,month);
                    arrayList.add(read);
                }
            }

            return arrayList;

        }catch (JSONException e){
            return arrayList;
        }
    }

}
