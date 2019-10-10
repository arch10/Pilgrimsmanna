package com.gigaworks.tech.bible;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gigaworks.tech.bible.adapter.TitleAdapter;
import com.gigaworks.tech.bible.container.DailyRead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AudioBookActivity extends AppCompatActivity {

    private AppPreferences preferences;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private TitleAdapter adapter;
    private List<DailyRead> dailyReads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_book);

        preferences = AppPreferences.getInstance(this);
        int spanCount = 2;
        //gridLayoutManager = new GridLayoutManager(this, spanCount);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.rv_audio_books);

        dailyReads = getAllAudioBooks();

        adapter = new TitleAdapter(getAudioBookTitles(), (position, title) -> {
            showAudioBook(title);
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void showAudioBook(String title) {
        for (DailyRead dailyRead: dailyReads) {
            if(dailyRead.getTitle().equals(title)) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("soundUrl",dailyRead.getUrl());
                intent.putExtra("trackTitle",dailyRead.getTitle());
                intent.putExtra("category","xyz");
                intent.putExtra("pos",0);
                intent.putExtra("hide", true);
                startActivity(intent);
                break;
            }
        }
    }

    private ArrayList<String> getAudioBookTitles() {
        ArrayList<String> list = new ArrayList<>();
        if(dailyReads.size() == 0) {
            return list;
        }
        for(DailyRead dailyRead: dailyReads) {
            list.add(dailyRead.getTitle());
        }
        return list;
    }

    private List<DailyRead> getAllAudioBooks() {
        List<DailyRead> list = new ArrayList<>();

        String data = preferences.getStringPreference(AppPreferences.APP_SOUND_RESPONSE);

        if(data.equals(""))
            return list;

        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("page").equals("audiobook")) {
                    DailyRead dailyRead = new DailyRead();
                    dailyRead.setId(Integer.parseInt(jsonObject.getString("id")));
                    dailyRead.setCategory(jsonObject.getString("page"));
                    dailyRead.setTitle(jsonObject.getString("title"));
                    dailyRead.setUrl(jsonObject.getString("soundfile"));
                    list.add(dailyRead);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
