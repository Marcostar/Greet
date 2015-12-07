package com.sagycorp.greet.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sagycorp.greet.R;
import com.sagycorp.greet.Startup;

/**
 * A simple {@link Fragment} subclass.
 */
public class Facts extends Fragment {

    SharedPreferences sharedPreferences;
    private TextView factView;
    private String DidYouKnow;

    public Facts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Notify the system to allow an options menu for this fragment.
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences(Startup.PreferenceSETTINGS, Context.MODE_PRIVATE);
        View rootView = inflater.inflate(R.layout.fragment_facts, container, false);
        factView = (TextView) rootView.findViewById(R.id.DidYouKnow);
        DidYouKnow = sharedPreferences.getString(Startup.DidYouKnow, getString(R.string.DidYouKnow));
        factView.setText(DidYouKnow);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.quote_fact, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Share:
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
