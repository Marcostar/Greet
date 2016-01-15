package com.sagycorp.greet.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sagycorp.greet.Adapter.PlacesListAdapter;
import com.sagycorp.greet.Model.PlacesArchiveModel;
import com.sagycorp.greet.MySingleton;
import com.sagycorp.greet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesArchive extends Fragment {

    private RecyclerView PlacesArchiveList;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PlacesArchiveModel> PlacesList = new ArrayList<>();
    private PlacesListAdapter PlacesListAdapter;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout LoadingLayout, ErrorLayout;
    private final String url="http://regal-river-115009.appspot.com/ArchiveCalls/PlacesResponse";
    private Boolean visiblity = false;


    public PlacesArchive() {
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
        View rootView = inflater.inflate(R.layout.fragment_places_archive, container, false);
        LoadingLayout = (LinearLayout) rootView.findViewById(R.id.Loading);
        ErrorLayout = (LinearLayout) rootView.findViewById(R.id.ErrorLayout);
        PlacesArchiveList = (RecyclerView) rootView.findViewById(R.id.PlacesArchiveList);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
        layoutManager = new LinearLayoutManager(getActivity());
        PlacesArchiveList.setLayoutManager(layoutManager);

        /*LoadingLayout.setVisibility(View.VISIBLE);*/

        refreshLayout.setColorSchemeResources(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.archive_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.Refresh:
                // We make sure that the SwipeRefreshLayout is displaying it's refreshing indicator
                if(!visiblity) {
                    if (!refreshLayout.isRefreshing()) {
                        ErrorLayout.setVisibility(View.GONE);
                        refreshLayout.setRefreshing(true);
                    }

                    // Start our refresh background task
                    initiateRequest();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initiateRequest()
    {
        refreshLayout.setRefreshing(true);
        //New Implementation
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                visiblity = true;

                try {
                    JSONArray array = response.getJSONArray("Places");
                    for (int i = array.length()-1; i>=0; i--)
                    {
                        JSONObject object = array.getJSONObject(i);
                        PlacesArchiveModel archiveModel = new PlacesArchiveModel();
                        archiveModel.setIndex(object.getString("ID"));
                        archiveModel.setTitle(object.getString("PLACE"));
                        archiveModel.setURL(object.getString("URL"));
                        PlacesList.add(archiveModel);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*LoadingLayout.setVisibility(View.GONE);*/
                PlacesListAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                refreshLayout.setRefreshing(false);
                /*LoadingLayout.setVisibility(View.GONE);*/
                ErrorLayout.setVisibility(View.VISIBLE);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(4000, 2, 2f));
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PlacesListAdapter = new PlacesListAdapter(PlacesList);
        PlacesArchiveList.setAdapter(PlacesListAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!visiblity) {
                    ErrorLayout.setVisibility(View.GONE);
                    initiateRequest();
                }
            }
        });
        initiateRequest();


    }
}
