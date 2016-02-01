package com.sagycorp.greet.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.sagycorp.greet.MainActivity;
import com.sagycorp.greet.MySingleton;
import com.sagycorp.greet.PushStart;
import com.sagycorp.greet.R;
import com.sagycorp.greet.Startup;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HoroScope extends Fragment {


    private ScrollView scrollViewLayout;
    private LinearLayout LoadingLayout,LoadingFirst, ErrorLayout;
    private SwipeRefreshLayout RefreshLayout;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    private Boolean visiblity = false;
    private TextView signName, signDate, signQuote;
    private String url = "https://fd.sagycorp.com/Horoscope/";
    private String Today, Today_Horoscope, sign;
    private MainActivity activity = new MainActivity();
    private Tracker mTracker;
    public String[] Horoscopes;

    public HoroScope() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String Name = "Horoscope";
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
        sharedPreferences = getActivity().getSharedPreferences(Startup.PreferenceSETTINGS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        View rootview = inflater.inflate(R.layout.fragment_horoscope, container, false);
        signName = (TextView) rootview.findViewById(R.id.signName);
        signDate = (TextView) rootview.findViewById(R.id.signDate);
        signQuote = (TextView) rootview.findViewById(R.id.signQuote);
        scrollViewLayout = (ScrollView) rootview.findViewById(R.id.HoroScrollView);
        LoadingLayout = (LinearLayout) rootview.findViewById(R.id.Loading);
        LoadingLayout.setVisibility(View.VISIBLE);
        LoadingFirst = (LinearLayout) rootview.findViewById(R.id.LoadingFirst);
        ErrorLayout = (LinearLayout) rootview.findViewById(R.id.Error);
        Today = activity.TodayDate();
        /*System.out.println(Today);*/
        RefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipeRefresh);
        RefreshLayout.setColorSchemeResources(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);

       /* signName.setText(sharedPreferences.getString(Startup.HoroSign,"Aries"));*/
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!visiblity) {
                    ErrorLayout.setVisibility(View.GONE);
                    LoadingFirst.setVisibility(View.VISIBLE);
                    initiateRequest();
                }
            }
        });
        initiateRequest();


    }

    private void initiateRequest() {
        //Request StoryPage
        RefreshLayout.setRefreshing(true);
        JsonObjectRequest request = new JsonObjectRequest(url+ sharedPreferences.getString(Startup.HoroSign,"Aries") +"/"+ Today, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoadingFirst.setVisibility(View.GONE);
                LoadingLayout.setVisibility(View.GONE);
                scrollViewLayout.setVisibility(View.VISIBLE);
                try {

                    signName.setText(sharedPreferences.getString(Startup.HoroSign, "Aries"));
                    signDate.setText(activity.Stamp());
                    Today_Horoscope = response.getString("Horoscope");
                    signQuote.setText(response.getString("Horoscope"));
                    //set visiblity
                    visiblity = true;

                } catch (JSONException e) {
                    visiblity = false;
                    e.printStackTrace();
                }
                RefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                RefreshLayout.setRefreshing(false);
                LoadingFirst.setVisibility(View.GONE);
                LoadingLayout.setVisibility(View.GONE);
                ErrorLayout.setVisibility(View.VISIBLE);
                //System.out.println(error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(4000, 2, 2f));
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.horoscope, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Refresh:
                // We make sure that the SwipeRefreshLayout is displaying it's refreshing indicator
                if(!visiblity) {
                    if (!RefreshLayout.isRefreshing()) {
                        ErrorLayout.setVisibility(View.GONE);
                        RefreshLayout.setRefreshing(true);
                    }

                    // Start our refresh background task
                   // initiateRequest();
                }
                return true;

            case R.id.Share:

                if (Today_Horoscope!= null && !Today_Horoscope.isEmpty())
                {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Action")
                            .setAction("Share").setLabel("Horoscope")
                            .build());

                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,  "My today's horoscope\n"+ sharedPreferences.getString(Startup.HoroSign,"Aries")+":\n"+Today_Horoscope + "\nvia Greet."+"\nRead yours.\n"+ "http://goo.gl/T1AS5u");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }

                return true;

            case R.id.change_horoscope:


                //activity implementation
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();

                View dialogView = inflater.inflate(R.layout.horoscope_selector,null);
                builder.setView(dialogView);
                builder.setTitle(R.string.horo_title);
                Spinner spinner = (Spinner) dialogView.findViewById(R.id.edition_spinner);
                ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.horoscope_selector, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Horoscopes = getResources().getStringArray(R.array.horoscope_selector);

                        int positionID = position;
                        switch (positionID) {
                            case 0:
                                sign = "Aries";
                                break;
                            case 1:
                                sign = "Taurus";
                                break;
                            case 2:
                                sign = "Gemini";
                                break;
                            case 3:
                                sign = "Cancer";
                                break;
                            case 4:
                                sign = "Leo";
                                break;
                            case 5:
                                sign = "Virgo";
                                break;
                            case 6:
                                sign = "Libra";
                                break;
                            case 7:
                                sign = "Scorpio";
                                break;
                            case 8:
                                sign = "Sagittarius";
                                break;
                            case 9:
                                sign = "Capricorn";
                                break;
                            case 10:
                                sign = "Aquarius";
                                break;
                            case 11:
                                sign = "Pisces";
                                break;
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        sign = sharedPreferences.getString(Startup.HoroSign,"Aries");
                    }
                });
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String tmpSign = sharedPreferences.getString(Startup.HoroSign,"Aries");
                        if (sign.equals(tmpSign))
                        {
                            dialog.dismiss();
                        }
                        else
                        {

                            editor.putString(Startup.HoroSign, sign);
                            editor.putBoolean(Startup.IsHoroSet, false);
                            editor.apply();
                            initiateRequest();

                        }

                    }
                }).setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            /*builder.setCancelable(false);*/
                builder.create().show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
