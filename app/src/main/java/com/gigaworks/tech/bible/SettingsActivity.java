package com.gigaworks.tech.bible;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.RadioGroup;

import com.gigaworks.tech.bible.adapter.ListAdapter;
import com.gigaworks.tech.bible.container.ListData;
import com.warkiz.widget.IndicatorSeekBar;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mAdapter = new ListAdapter(this, setListData(), (data, position) -> {
            switch (position) {
                case 0:
                    showTextSizeDialog();
                    break;
                case 1:
                    showNightModeDialog();
                default:
                    break;
            }
        });

        //setting Recycler View
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }

    private ArrayList<ListData> setListData() {
        ListData data;

        ArrayList<ListData> list = new ArrayList<>();
        data = new ListData(getString(R.string.text_size), getTextSizeDesc(), R.drawable.ic_format_size_black_24dp);
        list.add(data);
        data = new ListData(getString(R.string.night_mode), getNightModeDesc(), R.drawable.ic_brightness_2_black_24dp);
        list.add(data);

        return list;
    }

    private String getTextSizeDesc() {
        int val = getTextSize();
        return "Text size : " + val;
    }

    private int getTextSize() {
        AppPreferences preferences = AppPreferences.getInstance(this);
        return preferences.getIntegerPreference(AppPreferences.APP_TEXT_SIZE, 18);
    }

    private void setTextSize(int size) {
        AppPreferences preferences = AppPreferences.getInstance(this);
        preferences.setIntegerPreference(AppPreferences.APP_TEXT_SIZE, size);
    }

    private boolean getNightMode() {
        AppPreferences preferences = AppPreferences.getInstance(this);
        return preferences.getBooleanPreference(AppPreferences.APP_NIGHT_MODE, false);
    }

    private void setNightMode(boolean val) {
        AppPreferences preferences = AppPreferences.getInstance(this);
        preferences.setBooleanPreference(AppPreferences.APP_NIGHT_MODE, val);
    }

    private String getNightModeDesc() {
        if(getNightMode()) {
            return "On";
        } else {
            return "Off";
        }
    }

    private void showTextSizeDialog() {
        Dialog precisionDialog = new Dialog(this);
        precisionDialog.setCanceledOnTouchOutside(true);
        precisionDialog.setContentView(R.layout.precision_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        Objects.requireNonNull(precisionDialog.getWindow()).setLayout((int) (width * 0.9), ConstraintLayout.LayoutParams.WRAP_CONTENT);

        Button cancelButton = precisionDialog.findViewById(R.id.buttonCancel);
        Button setButton = precisionDialog.findViewById(R.id.buttonSet);
        final IndicatorSeekBar indicatorSeekBar = precisionDialog.findViewById(R.id.indicatorSeekBar);

        indicatorSeekBar.setProgress(getTextSize());

        cancelButton.setOnClickListener(v -> precisionDialog.dismiss());

        setButton.setOnClickListener(v -> {
            setTextSize(indicatorSeekBar.getProgress());
            precisionDialog.dismiss();
            mAdapter.setList(setListData());
            recyclerView.setAdapter(mAdapter);
        });
        precisionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        precisionDialog.getWindow().getAttributes().windowAnimations = R.style.WindowTransition;
        precisionDialog.show();
    }

    private void showNightModeDialog() {
        Dialog precisionDialog = new Dialog(this);
        precisionDialog.setCanceledOnTouchOutside(true);
        precisionDialog.setContentView(R.layout.night_mode_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        Objects.requireNonNull(precisionDialog.getWindow()).setLayout((int) (width * 0.9), ConstraintLayout.LayoutParams.WRAP_CONTENT);

        RadioGroup radioGroup = precisionDialog.findViewById(R.id.rg_angle);

        boolean nightMode = getNightMode();
        if (nightMode) {
            radioGroup.check(R.id.on);
        } else {
            radioGroup.check(R.id.off);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.on:
                    setNightMode(true);
                    break;
                case R.id.off:
                    setNightMode(false);
                    break;
            }
            mAdapter.setList(setListData());
            recyclerView.setAdapter(mAdapter);
            precisionDialog.dismiss();
        });
        precisionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        precisionDialog.getWindow().getAttributes().windowAnimations = R.style.WindowTransition;
        precisionDialog.show();
    }

}
