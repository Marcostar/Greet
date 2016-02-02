package com.sagycorp.greet.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sagycorp.greet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhyAds extends Fragment {

    private TextView whyAdsText;

    public WhyAds() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_why_ads, container, false);
        whyAdsText = (TextView) rootView.findViewById(R.id.whyAds);
        whyAdsText.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.inspire, menu);
    }
}
