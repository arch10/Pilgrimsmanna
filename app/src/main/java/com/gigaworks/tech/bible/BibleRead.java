package com.gigaworks.tech.bible;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class BibleRead extends AppCompatActivity implements View.OnClickListener,
        MediaPlayer.OnPreparedListener {

    private Toolbar toolbar;
    private Spinner chapterSpinner;
    private String currentAudioURL = Constants.getAudioHome() + "john_1.mp3";
    private ProgressBar mediaProgressBar;
    private TextView textView;
    private AppPreferences preferences;
    private ImageButton play,next,prev,pause;
    private Boolean isPrepared = false;
    private String bookData = "";
    private int currentChapter = 0;

    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible_read);

        //initializations
        toolbar = findViewById(R.id.toolbar);
        chapterSpinner = findViewById(R.id.sp_chapter);
        mediaProgressBar = findViewById(R.id.media_progressbar);
        textView = findViewById(R.id.tv_content);
        preferences = new AppPreferences(this);

        play = findViewById(R.id.play);
        next = findViewById(R.id.forward);
        prev = findViewById(R.id.backward);
        pause = findViewById(R.id.pause);


        if(mediaPlayer == null)
            mediaPlayer = new MediaPlayer();

        if(!mediaPlayer.isPlaying()){
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
        }

        Intent intent = getIntent();
        String title = intent.getStringExtra(Constants.BOOK_TITLE);
        bookData = intent.getStringExtra(Constants.BOOK_DATA);
        int noOfChapters = intent.getIntExtra(Constants.BOOK_CHAPTERS,1);

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setTitle(title);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(BibleRead.this,
                R.layout.spinner_item_layout,
                getChapterList(noOfChapters));

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        chapterSpinner.setAdapter(spinnerAdapter);

        //change this if adding more books
        currentChapter = preferences.getIntegerPreference(AppPreferences.APP_LAST_READ_CHAPTER)-1;
        chapterSpinner.setSelection(currentChapter);

        try {
            String chapterResponse = getChapter(bookData);
            setChapterContent(chapterResponse);
            setAudioUrl(chapterResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        chapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String chapterResponse = getChapter(bookData);
                    setChapterContent(chapterResponse);
                    setAudioUrl(chapterResponse);
                    preferences.setIntegerPreference(AppPreferences.APP_LAST_READ_CHAPTER,chapterSpinner.getSelectedItemPosition()+1);
                    isPrepared = false;

                    if(mediaPlayer!=null) {
                        mediaPlayer.pause();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setOnPreparedListener(BibleRead.this);
                    }

                    pause.setVisibility(View.GONE);
                    play.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);


    }

    private ArrayList<String> getChapterList(int noOfChapters) {
        ArrayList<String> chapters = new ArrayList<>();

        for(int i=1; i<=noOfChapters; i++){
            chapters.add("Chapter " + i);
        }
        return chapters;
    }

    private String getChapter(String response) throws JSONException {
        int chapter = chapterSpinner.getSelectedItemPosition() + 1;
        JSONArray array = new JSONArray(response);
        for(int i=0; i<array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            int resChapter = Integer.parseInt(jsonObject.getString("chapter"));

            if(resChapter == chapter){
                return jsonObject.toString();
            }
        }
        return "";
    }

    private void setChapterContent(String string) throws JSONException {
        JSONObject jsonObject = new JSONObject(string);
        textView.setText(refineText(jsonObject.getString("text")));
    }

    private String refineText(String text) {
        text = text.replaceAll("</p><p>","\n\n");
        text = text.replaceAll("<p>","");
        text = text.replaceAll("</p>","");
        return text;
    }

    private void setAudioUrl(String string) throws JSONException {
        JSONObject jsonObject = new JSONObject(string);
        currentAudioURL = Constants.getAudioHome() + (jsonObject.getString("soundfile"));
    }

    private void playAudio() {
        if(isPrepared) {
            Toast.makeText(this, "Play again", Toast.LENGTH_SHORT).show();
            mediaPlayer.start();
            play.setVisibility(View.GONE);
            pause.setVisibility(View.VISIBLE);
        }
        else {
            try {
                loadMediaPlayer(currentAudioURL);
            } catch (IOException e) {
                Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pauseAudio() {
        mediaPlayer.pause();
        pause.setVisibility(View.GONE);
        play.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.play:
                playAudio();
                break;
            case R.id.pause:
                pauseAudio();
                break;
            case R.id.forward:
                playNextChapter();
                break;
            case R.id.backward:
                playLastChapter();
                break;

        }
    }

    private void loadMediaPlayer(String audioUrl) throws IOException{
        // give data to mediaPlayer
        mediaPlayer.setDataSource(audioUrl);
        // media player asynchronous preparation
        mediaPlayer.prepareAsync();

        //loading media
        play.setVisibility(View.INVISIBLE);
        mediaProgressBar.setVisibility(View.VISIBLE);

    }

    public void onBackPressed(){
        super.onBackPressed();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        finish();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        //start media player
        mediaPlayer.start();

        //dismiss dialog
        mediaProgressBar.setVisibility(View.GONE);
        play.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);

        Toast.makeText(BibleRead.this, currentAudioURL, Toast.LENGTH_SHORT).show();
        isPrepared = true;

    }

    private void playNextChapter() {
        int count = chapterSpinner.getAdapter().getCount();
        int curr = chapterSpinner.getSelectedItemPosition();
        if(count > curr + 1)
            chapterSpinner.setSelection(curr + 1);
    }

    private void playLastChapter() {
        int curr = chapterSpinner.getSelectedItemPosition();
        if(curr > 0)
            chapterSpinner.setSelection(curr - 1);
    }
}
