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
public class ToolsFragment extends Fragment {

    private String string = "The word tool occurs only four times in the KJV. In fact, in all these" +
            " instances, there is a negative connotation. God never wanted to use tools in his " +
            "altars or his buildings. Tools were banned in the construction site. So, everything " +
            "was already prepared before, made ready before it was brought to the site.\n\n" +
            "So the tools are used away from the site. The pilgrim needs tools to get on with his " +
            "journey and pitch his tent. He needs tools to hew the stones which will become his " +
            "altar. He will find some of them here.";

    private TextView textView;

    public ToolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tools, container, false);

        textView = view.findViewById(R.id.tv_maintext);
        textView.setText(string);
        return view;
    }

}
