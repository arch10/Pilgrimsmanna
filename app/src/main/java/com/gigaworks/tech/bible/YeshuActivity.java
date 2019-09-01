package com.gigaworks.tech.bible;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gigaworks.tech.bible.adapter.DailyReadAdapter;
import com.gigaworks.tech.bible.container.DailyRead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YeshuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DailyReadAdapter adapter;
    private LinearLayoutManager manager;
    private AppPreferences preferences;
    private ArrayList<DailyRead> months;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeshu);

        months = new ArrayList<>();

        preferences = AppPreferences.getInstance(YeshuActivity.this);
        recyclerView = findViewById(R.id.rv_daily);

        months = getMonthData("part1");

        adapter = new DailyReadAdapter(months, YeshuActivity.this, (main, position) -> {
            Intent intent = new Intent(YeshuActivity.this,MainActivity.class);
            intent.putExtra("soundUrl",main.getUrl());
            intent.putExtra("trackTitle",main.getTitle());
            intent.putExtra("category",main.getCategory());
            intent.putExtra("pos",position);
            startActivity(intent);
        });

        manager = new LinearLayoutManager(YeshuActivity.this.getApplicationContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
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
        }catch (JSONException e){
            e.printStackTrace();
        }
        return arrayList;
    }
}
