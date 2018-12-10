package com.gigaworks.tech.bible;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gigaworks.tech.bible.adapter.BookAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BookAdapter adapter;
    private AppPreferences preferences;

    private String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        preferences = AppPreferences.getInstance(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.rv_books);

        data = preferences.getStringPreference(AppPreferences.APP_BOOK_RESPONSE);

        adapter = new BookAdapter(getBookTitles(data), (position, title) -> {
                JSONArray array = getBook(data, title);
                Intent intent = new Intent(this, BibleRead.class);
                intent.putExtra(Constants.BOOK_TITLE, title);
                intent.putExtra(Constants.BOOK_DATA, array.toString());
                intent.putExtra(Constants.BOOK_CHAPTERS, array.length());
                startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
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
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
