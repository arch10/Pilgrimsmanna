package com.gigaworks.tech.bible.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigaworks.tech.bible.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoachFragment extends Fragment {

    private String string = "Coaching at its best is founded on biblical principles. Explore the " +
            "wisdom from the Word and the Word's Men & Women on the habit which is foundational to " +
            "sustainable pilgrim journey.\n" +
            "\n" +
            "Coaching is a mutually beneficial process. Change the way you lead forever with " +
            "the guidance of the Holy Spirit.";

    private TextView textView;

    public CoachFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coach, container, false);

        textView = view.findViewById(R.id.tv_maintext);
        textView.setText(string);
        return view;
    }

}
