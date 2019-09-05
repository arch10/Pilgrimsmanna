package com.gigaworks.tech.bible;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gigaworks.tech.bible.adapter.TitleAdapter;
import com.gigaworks.tech.bible.container.DailyRead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DailyDevotionalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TitleAdapter adapter;
    private AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_devotional);

        preferences = AppPreferences.getInstance(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.rv_devotional);

        adapter = new TitleAdapter(getTitles(), (position, title) -> {
            if (title.equals(Constants.getYeshuKaJeevan())) {
                Intent intent = new Intent(this, YeshuActivity.class);
                startActivity(intent);
            } else if (title.equals(Constants.getMonthlyBread())) {
                Intent intent = new Intent(this, DailyActivity.class);
                startActivity(intent);
            } else if (title.equals(Constants.getDailyDevotion())){
                Intent intent = new Intent(this, MainActivity.class);
                DailyRead dailyRead = getDailyDevotion("Daily Devotion");
                intent.putExtra("soundUrl",dailyRead.getUrl());
                intent.putExtra("trackTitle",dailyRead.getTitle());
                intent.putExtra("category","xyz");
                intent.putExtra("pos",0);
                intent.putExtra("hide", true);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

    }

    private ArrayList<String> getTitles() {

        ArrayList<String> list = new ArrayList<>();
        list.add(Constants.getYeshuKaJeevan());
        list.add(Constants.getMonthlyBread());
        list.add(Constants.getDailyDevotion());
        return list;
    }

    private DailyRead getDailyDevotion(String category) {
        String response = preferences.getStringPreference(AppPreferences.APP_SOUND_RESPONSE);

        if(response.equals(""))
            return null;

        try {
            JSONArray array = new JSONArray(response);
            for (int i=0; i<array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                if(jsonObject.getString("maincategory").equals(category)){
                    int id = Integer.parseInt(jsonObject.getString("id"));
                    String title = jsonObject.getString("title");
                    String url = jsonObject.getString("soundfile");
                    return new DailyRead(title,id,url,category);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }
}
