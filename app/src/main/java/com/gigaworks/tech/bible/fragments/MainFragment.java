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
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView textView;

    private String string = "Pilgrim: A pilgrim (from the Latin peregrinus) is a traveler " +
            "(literally one who has come from afar) who is on a journey to a holy place. Typically," +
            " this is a physical journey (often on foot) to some place of special significance to " +
            "the adherent of a particular religious belief system.\n\nManna (Hebrew: מָן\u200E mān," +
            " Greek: μάννα; Arabic: المَنّ\u200E, Persian: گزانگبین\u200E), sometimes or archaically " +
            "spelled mana, is an edible substance which, according to the Bible and the Quran,[1] " +
            "God provided for the Israelites during their travels in the desert during the forty-year" +
            " period following the Exodus and prior to the conquest of Canaan.\n\nThe universe is " +
            "expanding or, as I like to put it, traveling. In fact, everything in the universe is " +
            "traveling. From the atoms and molecules of the tiniest life-form on this earth to the " +
            "scariest of stars light years away, everything is moving. Some are moving to glory, " +
            "others to black holes.\n\nBut many of us earthlings believe we, as spiritual beings," +
            " are traveling to a Holy Place, a Holy City, which is called Jerusalem, the only city " +
            "which is found in Earth and Heaven. This is a supernatural city where there is no light" +
            " but that which flows from the Glory of God and the Lamb lamp. The nations will walk " +
            "in its light and earth\\'s kings bring in their splendor.\n\nNothing dirty or defiled" +
            " will get into the City, and no one who defiles or deceives. Only those whose names " +
            "are written in the Lamb\\'s Book Life will get in.\n\nWe believe we will look on " +
            "God, our foreheads will mirror God.\n\nWe therefore need the food of angels to " +
            "sustain our spirits when we are thus traveling with such hope. Because we walk in the " +
            "Spirit, by faith, and not by sight. We walk in love, not in fear.\n\nWe also share " +
            "our food. Millions sharing their food and goods show that we are traveling with a God " +
            "of bounty.\n\nCome, take your share and that which you have freely received, freely " +
            "give.\n";

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textView = view.findViewById(R.id.tv_maintext);
        textView.setText(string);
        return view;
    }

}
