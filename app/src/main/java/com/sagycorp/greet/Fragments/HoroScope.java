package com.sagycorp.greet.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sagycorp.greet.MainActivity;
import com.sagycorp.greet.MySingleton;
import com.sagycorp.greet.R;
import com.sagycorp.greet.Startup;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HoroScope extends Fragment {

    private View rootview;
    private ScrollView scrollViewLayout;
    private LinearLayout LoadingLayout,LoadingFirst, ErrorLayout;
    private SwipeRefreshLayout RefreshLayout;
    SharedPreferences sharedPreferences ;
    private Boolean visiblity = false;
    private TextView signName, signDate, signQuote;
    private String url = "http://192.168.1.4/Greet/Horoscope/";
    private String horoScope;
    private MainActivity activity = new MainActivity();

    public HoroScope() {
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
        sharedPreferences = getActivity().getSharedPreferences(Startup.PreferenceSETTINGS, Context.MODE_PRIVATE);
        rootview = inflater.inflate(R.layout.fragment_horoscope, container, false);
        signName = (TextView) rootview.findViewById(R.id.signName);
        signDate = (TextView) rootview.findViewById(R.id.signDate);
        signQuote = (TextView) rootview.findViewById(R.id.signQuote);
        scrollViewLayout = (ScrollView) rootview.findViewById(R.id.HoroScrollView);
        LoadingLayout = (LinearLayout) rootview.findViewById(R.id.Loading);
        LoadingLayout.setVisibility(View.VISIBLE);
        LoadingFirst = (LinearLayout) rootview.findViewById(R.id.LoadingFirst);
        ErrorLayout = (LinearLayout) rootview.findViewById(R.id.Error);
        RefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipeRefresh);
        horoScope = sharedPreferences.getString(Startup.HoroSign,"Aries");
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
        JsonObjectRequest request = new JsonObjectRequest(url+horoScope+".php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoadingFirst.setVisibility(View.GONE);
                LoadingLayout.setVisibility(View.GONE);
                scrollViewLayout.setVisibility(View.VISIBLE);
                try {

                    signName.setText(sharedPreferences.getString(Startup.HoroSign,"Aries"));
                    signDate.setText(activity.Stamp());
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
                System.out.println(error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(4000, 2, 2f));
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.story, menu);
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

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
