package com.sagycorp.greet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sagycorp.greet.Fragments.Facts;
import com.sagycorp.greet.Fragments.HoroScope;
import com.sagycorp.greet.Fragments.Inspire;
import com.sagycorp.greet.Fragments.Places;
import com.sagycorp.greet.Fragments.Quotes;
import com.sagycorp.greet.Fragments.Story;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    private Fragment fragment;
    private String sign, title;


    private boolean viewIsAtHome;
    public String[] Horoscopes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayView(R.id.nav_story);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if(!viewIsAtHome)
        {
            displayView(R.id.nav_story);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());
        return true;
    }

    //method to replace Views in ID = content_frame in content_main
    public void displayView(int viewID)
    {
        fragment = null;
        title = getString(R.string.app_name);

        switch (viewID)
        {
            case R.id.nav_story:
                fragment = new Story();
                title = getString(R.string.story);
                viewIsAtHome = true;
                break;

            case R.id.nav_horoscope:
                fragment = new HoroScope();
                viewIsAtHome = false;
                title = getString(R.string.horoscope);
                break;

            case R.id.nav_places:
                fragment = new Places();
                title = getString(R.string.places);
                viewIsAtHome = false;
                break;

            case R.id.nav_quotes:
                fragment = new Quotes();
                title = getString(R.string.quotes);
                viewIsAtHome = false;
                break;

            case R.id.nav_know:
                fragment = new Facts();
                title = getString(R.string.didYouKnow);
                viewIsAtHome = false;
                break;

            case R.id.nav_shareStory:
                fragment = new Inspire();
                title = getString(R.string.inspire);
                viewIsAtHome = false;
                break;

        }

        if (fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame,fragment);
            ft.commit();
        }

        //set the toolbar title
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public String Stamp()
    {
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

        Date today = Calendar.getInstance().getTime();

        String timeStamp = dateFormat.format(today);

        return timeStamp;
    }

    /*public void setHoroscopes()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.horoscope_selector,null);
        builder.setView(dialogView);
        builder.setTitle(R.string.horo_title);
        Spinner spinner = (Spinner) dialogView.findViewById(R.id.edition_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.horoscope_selector, android.R.layout.simple_spinner_item);
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

            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putString(MainActivity.HoroSign, sign);
                editor.apply();
                editor.putBoolean(MainActivity.IsHoroSet, true);
                editor.apply();
            }
        })*//*.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })*//*;
        builder.setCancelable(false);
        builder.create().show();
    }*/

}
