package com.gigaworks.tech.bible;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AboutActivity extends AppCompatActivity {

    private TextView version, build;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        version = findViewById(R.id.version);
        build = findViewById(R.id.build);

        Date buildDate = BuildConfig.buildTime;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        build.setText("Build Date: " + sdf.format(buildDate));
        version.setText("Version: " + BuildConfig.VERSION_NAME);

    }
}
