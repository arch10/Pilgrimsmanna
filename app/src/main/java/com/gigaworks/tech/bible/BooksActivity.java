package com.gigaworks.tech.bible;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.gigaworks.tech.bible.adapter.BookAdapter;
import com.gigaworks.tech.bible.container.Book;
import com.gigaworks.tech.bible.container.Chapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

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

        adapter = new BookAdapter(getBooks(data), (position, book) -> {
            //JSONArray array = getBook(data, title);
            Gson gson = new Gson();
            String jsonString = gson.toJson(book.getChapters());
            Intent intent = new Intent(this, BibleRead.class);
            intent.putExtra(Constants.BOOK_TITLE, book.getTitle());
            intent.putExtra(Constants.BOOK_DATA, jsonString);
            intent.putExtra(Constants.BOOK_CHAPTERS, book.getChapters().size());
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
            for (int i = 0; i < list.length(); i++) {
                JSONObject object = list.getJSONObject(i);
                if (object.getString("title").equals(title)) {
                    array.put(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    private ArrayList<String> getBookTitles(String data) {

        try {
            ArrayList<String> list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(data);
            Iterator<String> books = jsonObject.keys();
            while (books.hasNext()) {
                String key = books.next();
                JSONArray array = jsonObject.getJSONArray(key);
                String title = array.getJSONObject(0).getString("title");
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

    private ArrayList<Book> getBooks(String data) {
        ArrayList<Book> list = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator<String> books = jsonObject.keys();
            while (books.hasNext()) {
                String key = books.next();
                JSONArray array = jsonObject.getJSONArray(key);
                Book book = new Book();
                ArrayList<Chapter> chapters = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject chapterObj = array.getJSONObject(i);
                    Chapter chapter = new Chapter();
                    if (i == 0) {
                        book.setId(Integer.parseInt(chapterObj.getString("sort")));
                        book.setCategory(chapterObj.getString("category"));
                        book.setTitle(chapterObj.getString("title"));
                    }
                    chapter.setId(Integer.parseInt(chapterObj.getString("id")));
                    chapter.setChapterNo(Integer.parseInt(chapterObj.getString("chapter")));
                    chapter.setText(chapterObj.getString("text"));
                    chapter.setSoundfile(chapterObj.getString("soundfile"));
                    chapter.setBlocked(Boolean.parseBoolean(chapterObj.getString("blocked")));
                    chapters.add(chapter);
                }
                book.setChapters(chapters);
                list.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
