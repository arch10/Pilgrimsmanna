package com.gigaworks.tech.bible;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gigaworks.tech.bible.container.DailyRead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener,
        View.OnClickListener {

    private String songUrl = "";
    private TextView trackTitle;
    private String audioHomeURL = "http://biblebook.pilgrimsmanna.com/biblebook/";
    private Toolbar toolbar;
    private ProgressBar mediaProgressBar;
    private ImageButton play,next,prev,pause;
    private Boolean isPrepared = false;
    private ImageView imageTrack;
    private Animation animation;
    private String category;
    private int pos;
    private AppPreferences preferences;
    private ArrayList<DailyRead> arrayList;


    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = AppPreferences.getInstance(this);

        toolbar = findViewById(R.id.toolbar);
        mediaProgressBar = findViewById(R.id.media_progressbar);
        imageTrack = findViewById(R.id.iv_imgTrack);
        trackTitle = findViewById(R.id.tv_track_title);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);

        play = findViewById(R.id.play);
        next = findViewById(R.id.forward);
        prev = findViewById(R.id.backward);
        pause = findViewById(R.id.pause);


        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Now Playing");

        Intent intent = getIntent();
        songUrl = intent.getStringExtra("soundUrl");
        trackTitle.setText(intent.getStringExtra("trackTitle"));
        category = intent.getStringExtra("category");
        pos = intent.getIntExtra("pos",0);

        songUrl = audioHomeURL+songUrl;

        arrayList = new ArrayList<>();

        arrayList = getMonthData(category);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);

        //setting animation
//        animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
//        animation.setFillAfter(true);

        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            pause.setVisibility(View.GONE);
            mediaProgressBar.setVisibility(GONE);
            play.setVisibility(VISIBLE);
            //imageTrack.clearAnimation();
        });

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        //start media player
        mediaPlayer.start();

        //imageTrack.startAnimation(animation);
        //dismiss dialog
        mediaProgressBar.setVisibility(View.GONE);
        play.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);

        isPrepared = true;
    }

    private void pressedPause() {
        pause.setVisibility(View.GONE);
        mediaProgressBar.setVisibility(GONE);
        play.setVisibility(VISIBLE);

        mediaPlayer.pause();
        imageTrack.clearAnimation();
    }

    private void pressedPlay() {
        mediaProgressBar.setVisibility(GONE);
        play.setVisibility(GONE);
        pause.setVisibility(VISIBLE);

        mediaPlayer.start();

        //imageTrack.startAnimation(animation);
    }

    private void pressedNext() {
        if(pos < arrayList.size() - 1){
            pos++;
            DailyRead main = arrayList.get(pos);
            String url = main.getUrl();
            String title = main.getTitle();
            isPrepared = false;

            if(mediaPlayer!=null) {
                mediaPlayer.pause();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setOnPreparedListener(MainActivity.this);
            }
            songUrl = audioHomeURL + url;
            trackTitle.setText(title);

            pause.setVisibility(View.GONE);
            play.setVisibility(View.VISIBLE);
            mediaProgressBar.setVisibility(GONE);
            imageTrack.clearAnimation();
        }
    }

    private void pressedPrev() {
        if(pos > 0){
            pos--;
            DailyRead main = arrayList.get(pos);
            String url = main.getUrl();
            String title = main.getTitle();
            isPrepared = false;

            if(mediaPlayer!=null) {
                mediaPlayer.pause();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setOnPreparedListener(MainActivity.this);
            }
            songUrl = audioHomeURL + url;
            trackTitle.setText(title);

            pause.setVisibility(View.GONE);
            play.setVisibility(View.VISIBLE);
            mediaProgressBar.setVisibility(GONE);
            imageTrack.clearAnimation();
        }
    }

    private void playAudio(String songUrl) {
        //Toast.makeText(MainActivity.this, songUrl, Toast.LENGTH_SHORT).show();
        if(isPrepared) {
            //Toast.makeText(this, "Play again", Toast.LENGTH_SHORT).show();
            pressedPlay();
        }
        else {
            try {
                loadMediaPlayer(songUrl);
            } catch (IOException e) {
                Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadMediaPlayer(String audioUrl) throws IOException{
        // give data to mediaPlayer
        mediaPlayer.setDataSource(audioUrl);
        // media player asynchronous preparation
        mediaPlayer.prepareAsync();

        //loading media
        play.setVisibility(View.INVISIBLE);
        pause.setVisibility(GONE);
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
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.play:
                playAudio(songUrl);
                break;
            case R.id.pause:
                pressedPause();
                break;
            case R.id.forward:
                pressedNext();
                break;
            case R.id.backward:
                pressedPrev();
                break;
        }
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
