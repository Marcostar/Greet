package com.sagycorp.greet.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.sagycorp.greet.Helper.ArchiveHelper;
import com.sagycorp.greet.MainActivity;
import com.sagycorp.greet.PushStart;
import com.sagycorp.greet.R;
import com.sagycorp.greet.Startup;

/**
 * A simple {@link Fragment} subclass.
 */
public class Quotes extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Integer quotePermanentNo, quoteTempNo;
    private ImageButton previousArrow, nextArrow;
    private TextView quoteView, authorView, quoteNumber;
    private String Quote, Author, quoteID, tmpDate;
    private MainActivity activity = new MainActivity();
    private ArchiveHelper helper;
    private Tracker mTracker;

    public Quotes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String Name = "Quotes";
        // Obtain the shared Tracker instance.
        PushStart application = (PushStart) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName(Name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // Notify the system to allow an options menu for this fragment.
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quotes, container, false);
        sharedPreferences = getActivity().getSharedPreferences(Startup.PreferenceSETTINGS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //DB connect
        helper = new ArchiveHelper(getActivity());

        previousArrow = (ImageButton) rootView.findViewById(R.id.previous);
        nextArrow = (ImageButton) rootView.findViewById(R.id.next);
        quoteNumber = (TextView) rootView.findViewById(R.id.quoteNo);
        quoteView = (TextView) rootView.findViewById(R.id.Quotes);
        authorView = (TextView) rootView.findViewById(R.id.Author);
        quotePermanentNo = sharedPreferences.getInt(Startup.QuotePermanentNo,0);
        tmpDate = sharedPreferences.getString(Startup.QuoteDate,"27Nov1987");

        if (!tmpDate.equals(activity.TodayDate()))
        {
            quotePermanentNo = quotePermanentNo + 1;
            Cursor cursor = helper.getQuote(quotePermanentNo);
            try
            {
                if (cursor.getCount()>0)
                {
                    cursor.moveToFirst();
                    Quote = cursor.getString(cursor.getColumnIndex("quote"));
                    quoteID = cursor.getString(cursor.getColumnIndex("_id"));
                    Author = cursor.getString(cursor.getColumnIndex("author"));
                    quoteID = "#"+ quoteID;
                }
                quoteView.setText(Quote);
                quoteNumber.setText(quoteID);
                authorView.setText("- "+Author);
                editor.putString(Startup.QuoteDate,activity.TodayDate()).apply();
                editor.putInt(Startup.QuotePermanentNo,quotePermanentNo).apply();
            }
            finally {
                cursor.close();
            }
        }
        else
        {
            Cursor cursor = helper.getQuote(quotePermanentNo);
            try
            {
                if (cursor.getCount()>0)
                {
                    cursor.moveToFirst();
                    Quote = cursor.getString(cursor.getColumnIndex("quote"));
                    quoteID = cursor.getString(cursor.getColumnIndex("_id"));
                    Author = cursor.getString(cursor.getColumnIndex("author"));
                    quoteID = "#"+ quoteID;
                }
                quoteView.setText(Quote);
                quoteNumber.setText(quoteID);
                authorView.setText("- "+Author);
            }
            finally {
                cursor.close();
            }
        }

        if (quotePermanentNo <= 1)
        {
            previousArrow.setVisibility(View.GONE);
        }

        quoteTempNo = sharedPreferences.getInt(Startup.QuotePermanentNo,0);
        previousArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quoteTempNo = quoteTempNo - 1;
                Cursor cursor = helper.getQuote(quoteTempNo);
                try
                {
                    if (cursor.getCount()>0)
                    {
                        cursor.moveToFirst();
                        Quote = cursor.getString(cursor.getColumnIndex("quote"));
                        quoteID = cursor.getString(cursor.getColumnIndex("_id"));
                        Author = cursor.getString(cursor.getColumnIndex("author"));
                        quoteID = "#"+ quoteID;
                    }
                    quoteView.setText(Quote);
                    quoteNumber.setText(quoteID);
                    authorView.setText("- "+Author);

                    if (quoteTempNo <= 1)
                    {
                        previousArrow.setVisibility(View.GONE);
                    }
                    if (!nextArrow.isShown()) {
                        nextArrow.setVisibility(View.VISIBLE);
                    }
                }
                finally {
                    cursor.close();
                }

            }
        });

        nextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quoteTempNo = quoteTempNo + 1;

                Cursor cursor = helper.getQuote(quoteTempNo);
                try
                {
                    if (cursor.getCount()>0)
                    {
                        cursor.moveToFirst();
                        Quote = cursor.getString(cursor.getColumnIndex("quote"));
                        quoteID = cursor.getString(cursor.getColumnIndex("_id"));
                        Author = cursor.getString(cursor.getColumnIndex("author"));
                        quoteID = "#"+ quoteID;
                    }
                    quoteView.setText(Quote);
                    quoteNumber.setText(quoteID);
                    authorView.setText("- "+Author);

                    if (quoteTempNo > 1)
                    {
                        previousArrow.setVisibility(View.VISIBLE);
                    }

                    if(quoteTempNo == 200)
                    {
                        nextArrow.setVisibility(View.GONE);
                    }

                }
                finally {
                    cursor.close();
                }
            }
        });
        /*quoteView.setText(Quote);
        authorView.setText("- "+ Author);
*/        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.quote_fact, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Share:

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Share").setLabel("ArchiveStory")
                        .build());

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, Quote + "\n- "+ Author + "\nvia Greet."+"\nhttps://goo.gl/Sdc4w4");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
