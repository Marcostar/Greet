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
public class Quotes extends Fragment {

    SharedPreferences sharedPreferences;
    private TextView quoteView, authorView;
    private String Quote, Author;

    public Quotes() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);
        sharedPreferences = getActivity().getSharedPreferences(Startup.PreferenceSETTINGS, Context.MODE_PRIVATE);
        Quote = sharedPreferences.getString(Startup.Quotes, getString(R.string.Quotes));
        Author = sharedPreferences.getString(Startup.Author, getString(R.string.Author));
        quoteView = (TextView) view.findViewById(R.id.Quotes);
        authorView = (TextView) view.findViewById(R.id.Author);
        quoteView.setText(Quote);
        authorView.setText("- "+ Author);
        return view;
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
                sendIntent.putExtra(Intent.EXTRA_TEXT, Quote + "\n- "+ Author + "\nvia Greet."+"\nDownload Greet\n"+ "https://goo.gl/Sdc4w4");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
