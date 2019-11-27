package com.gigaworks.tech.bible;

import android.app.Notification;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gigaworks.tech.bible.container.Chapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.gigaworks.tech.bible.App.CHANNEL_ID;


public class BibleRead extends AppCompatActivity implements View.OnClickListener,
        MediaPlayer.OnPreparedListener {

    private Toolbar toolbar;
    private Spinner chapterSpinner;
    private String currentAudioURL = Constants.getAudioHome() + "john_1.mp3";
    private ProgressBar mediaProgressBar;
    private SeekBar seekBar;
    private TextView textView;
    private AppPreferences preferences;
    private ImageButton play,next,prev,pause;
    private Boolean isPrepared = false;
    private String bookData = "";
    private int currentChapter = 1;
    private Handler mHandler = new Handler();
    private String title;
    private NotificationManagerCompat managerCompat;
    private ScrollView scrollView;

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
        seekBar = findViewById(R.id.seekBar);
        preferences = new AppPreferences(this);
        managerCompat = NotificationManagerCompat.from(this);
        scrollView = findViewById(R.id.scrollView2);

        play = findViewById(R.id.play);
        next = findViewById(R.id.forward);
        prev = findViewById(R.id.backward);
        pause = findViewById(R.id.pause);

        int textSize = preferences.getIntegerPreference(AppPreferences.APP_TEXT_SIZE, 18);
        textView.setTextSize(textSize);

        boolean nightMode = preferences.getBooleanPreference(AppPreferences.APP_NIGHT_MODE, false);
        if(nightMode) {
            scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_background));
            textView.setTextColor(ContextCompat.getColor(this, R.color.light_background));
        } else {
            scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_background));
            textView.setTextColor(ContextCompat.getColor(this, R.color.dark_background));
        }

        if(mediaPlayer == null)
            mediaPlayer = new MediaPlayer();

        if(!mediaPlayer.isPlaying()){
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
        }

        //Make sure you update Seekbar on UI thread
        BibleRead.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });

        Intent intent = getIntent();
        title = intent.getStringExtra(Constants.BOOK_TITLE);
        bookData = intent.getStringExtra(Constants.BOOK_DATA);
        Gson gson = new Gson();
        TypeToken<List<Chapter>> token = new TypeToken<List<Chapter>>() {};
        List<Chapter> chapters = gson.fromJson(bookData, token.getType());
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
        currentChapter = preferences.getIntegerPreference(title);
        chapterSpinner.setSelection(currentChapter - 1);

        Chapter chapter = getChapter(chapters);
        assert chapter != null;
        setChapterContent(chapter);
        setAudioUrl(chapter);

        chapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                currentChapter = pos + 1;
                Chapter chapter = getChapter(chapters);
                assert chapter != null;
                setChapterContent(chapter);
                setAudioUrl(chapter);
                preferences.setIntegerPreference(title, currentChapter);
                isPrepared = false;

                if(mediaPlayer!=null) {
                    mediaPlayer.pause();
                    mediaPlayer.release();
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setOnPreparedListener(BibleRead.this);
                }

                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
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

    private Chapter getChapter(List<Chapter> chapters) {
        Chapter chapter;
        for(int i=0; i<chapters.size(); i++) {
            chapter = chapters.get(i);
            int chapterNo = chapter.getChapterNo();

            if(chapterNo == currentChapter){
                return chapter;
            }
        }
        return null;
    }

    private void setChapterContent(Chapter chapter) {
        textView.setText(refineText(chapter.getText()));
    }

    private void setAudioUrl(Chapter chapter) {
        currentAudioURL = Constants.getAudioHome() + chapter.getSoundfile();
    }

    private String refineText(String text) {
        text = text.replaceAll("</p><p>","\n\n");
        text = text.replaceAll("<p>","");
        text = text.replaceAll("</p>","");
        return text;
    }

    private void playAudio() {
        if(isPrepared) {
//            Toast.makeText(this, "Play again", Toast.LENGTH_SHORT).show();
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
//        Toast.makeText(this, audioUrl, Toast.LENGTH_SHORT).show();
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

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music_note_black_24dp)
                .setContentTitle(title)
                .setContentText(chapterSpinner.getSelectedItem().toString())
                .addAction(R.drawable.ic_action_rewind, "Previous", null)
                .addAction(R.drawable.ic_action_play,"Play", null)
                .addAction(R.drawable.ic_action_fast_forward, "Next", null)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(1,2,3))
                .build();
        managerCompat.notify(1, notification);
        //dismiss dialog
        mediaProgressBar.setVisibility(View.GONE);
        play.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);

//        Toast.makeText(BibleRead.this, currentAudioURL, Toast.LENGTH_SHORT).show();
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
