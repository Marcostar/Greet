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
public class Facts extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private TextView factView, factNo;
    private Integer factPermanentNo, factTempNo;
    private String DidYouKnow, FactNo;
    private MainActivity activity = new MainActivity();
    private ArchiveHelper helper;
    private ImageButton previousArrow, nextArrow;
    private Tracker mTracker;

    public Facts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String Name = "Facts";
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

        sharedPreferences = getActivity().getSharedPreferences(Startup.PreferenceSETTINGS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        helper = new ArchiveHelper(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_facts, container, false);
        previousArrow = (ImageButton) rootView.findViewById(R.id.previous);
        nextArrow = (ImageButton) rootView.findViewById(R.id.next);
        factView = (TextView) rootView.findViewById(R.id.DidYouKnow);
        factNo = (TextView) rootView.findViewById(R.id.factNo);
        factPermanentNo = sharedPreferences.getInt(Startup.FactPermanentNo, 0);
        String tmpDate = sharedPreferences.getString(Startup.FactDate, "24Mar1992");


        //Get Data from database
        if (!tmpDate.equals(activity.TodayDate()))
        {
            factPermanentNo = factPermanentNo + 1;
            Cursor cursor = helper.getFact(factPermanentNo);
            try
            {
                if (cursor.getCount()>0)
                {
                    cursor.moveToFirst();
                    DidYouKnow = cursor.getString(cursor.getColumnIndex("fact"));
                    FactNo = cursor.getString(cursor.getColumnIndex("_id"));
                    FactNo = "#"+FactNo;
                }
                factView.setText(DidYouKnow);
                factNo.setText(FactNo);
                editor.putString(Startup.FactDate,activity.TodayDate()).apply();
                editor.putInt(Startup.FactPermanentNo,factPermanentNo).apply();
            }
            finally {
                cursor.close();
            }

        }
        else if(tmpDate.equals(activity.TodayDate()))
        {
            Cursor cursor = helper.getFact(factPermanentNo);
            try
            {
                if (cursor.getCount()>0)
                {
                    cursor.moveToFirst();
                    DidYouKnow = cursor.getString(cursor.getColumnIndex("fact"));
                    FactNo = cursor.getString(cursor.getColumnIndex("_id"));
                    FactNo = "#"+FactNo;
                }
                factView.setText(DidYouKnow);
                factNo.setText(FactNo);
            }
            finally {
                cursor.close();
            }

        }

        if (factPermanentNo <= 1)
        {
            previousArrow.setVisibility(View.GONE);
        }

        factTempNo = sharedPreferences.getInt(Startup.FactPermanentNo,0);
        previousArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                factTempNo = factTempNo - 1;
                Cursor cursor = helper.getFact(factTempNo);
                try
                {
                    if (cursor.getCount()>0)
                    {
                        cursor.moveToFirst();
                        DidYouKnow = cursor.getString(cursor.getColumnIndex("fact"));
                        FactNo = cursor.getString(cursor.getColumnIndex("_id"));
                        FactNo = "#"+FactNo;
                    }
                    factView.setText(DidYouKnow);
                    factNo.setText(FactNo);
                    if (factTempNo <= 1)
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
                factTempNo = factTempNo + 1;

                Cursor cursor = helper.getFact(factTempNo);
                try
                {
                    if (cursor.getCount()>0)
                    {
                        cursor.moveToFirst();
                        DidYouKnow = cursor.getString(cursor.getColumnIndex("fact"));
                        FactNo = cursor.getString(cursor.getColumnIndex("_id"));
                        FactNo = "#"+FactNo;
                    }
                    factView.setText(DidYouKnow);
                    factNo.setText(FactNo);
                    if (factTempNo > 1)
                    {
                        previousArrow.setVisibility(View.VISIBLE);
                    }

                    if(factTempNo == 200)
                    {
                        nextArrow.setVisibility(View.GONE);
                    }

                }
                finally {
                    cursor.close();
                }

            }
        });
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

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Share").setLabel("ArchiveStory")
                        .build());

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Fact: "+DidYouKnow + "\nvia Greet."+"\n"+"http://goo.gl/T1AS5u");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        helper.close();
    }
}
