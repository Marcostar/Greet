package com.sagycorp.greet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.appodeal.ads.Appodeal;
import com.sagycorp.greet.Fragments.AllPlaces;
import com.sagycorp.greet.Fragments.Facts;
import com.sagycorp.greet.Fragments.HoroScope;
import com.sagycorp.greet.Fragments.Inspire;
import com.sagycorp.greet.Fragments.Quotes;
import com.sagycorp.greet.Fragments.Stories;
import com.sagycorp.greet.Fragments.WhyAds;
import com.sagycorp.greet.Helper.NotificationCreator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    private NavigationView navigationView;
    private Fragment fragment;
    private String sign, title;
    private boolean IsNotificationSet = false, CheckNotification;
    private static final String appKey = "7efbcd7b61614667cb4d65ddcf9a026a4c044e085755707c";


    private boolean viewIsAtHome;
    public String[] Horoscopes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(Startup.PreferenceSETTINGS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        CheckNotification = sharedPreferences.getBoolean(Startup.IsNotificationSet,false);

        if (IsNotificationSet == CheckNotification)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            Intent intent = new Intent(MainActivity.this, NotificationCreator.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            editor.putBoolean(Startup.IsNotificationSet,true).apply();
        }
        setContentView(R.layout.activity_main);

        //Ads
        /*Appodeal.setTesting(true);*/
        Appodeal.setBannerViewId(R.id.appodealBannerView);
        Appodeal.initialize(this, appKey, Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_VIEW);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(Startup.PreferenceSETTINGS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        displayView(R.id.nav_story);
        navigationView.setCheckedItem(R.id.nav_story);
        /*navigationView.getMenu().getItem(0).setChecked(true);*/
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
            navigationView.setCheckedItem(R.id.nav_story);
        }
        else {
            /*super.onBackPressed();*/
            new AlertDialog.Builder(this)
                    .setTitle("Read Enough?")
                    .setCancelable(false)
                    .setMessage("Are you sure you want to stop reading?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
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

        if(id == R.id.suggestions)
        {
            Intent Email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "suggestions@sagycorp.com", null));
            Email.putExtra(Intent.EXTRA_SUBJECT,"Advice for making this better app");
            Email.putExtra(Intent.EXTRA_TEXT, "**Your Demands/Suggestions here**");
            startActivity(Intent.createChooser(Email, "Share Your Advice with :"));
            return true;
        }
        if (id == R.id.tellFriend)
        {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I found very interesting app!!\nGreet! Your storyteller, trip consultant, an astrological advisor and much more."+"\nDownload Greet\n"+ "http://goo.gl/T1AS5u");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }

        if(id == R.id.rate_app) {

            Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
            }
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
                fragment = new Stories();
                title = getString(R.string.story);
                viewIsAtHome = true;
                break;

            case R.id.nav_places:
                fragment = new AllPlaces();
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

            case R.id.nav_horoscope:
                fragment = new HoroScope();
                viewIsAtHome = false;
                title = getString(R.string.horoscope);
                break;

            case R.id.nav_shareStory:
                fragment = new Inspire();
                title = getString(R.string.inspire);
                viewIsAtHome = false;
                break;

            case R.id.nav_whyAds:
                fragment = new WhyAds();
                title = getString(R.string.whyAds);
                viewIsAtHome = false;
                break;

        }

        if (fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
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
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

        Date today = Calendar.getInstance().getTime();

        return dateFormat.format(today);
    }

    public String TodayDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy", Locale.US);

        Date today = Calendar.getInstance().getTime();

        return dateFormat.format(today);
    }


    @Override public void onResume() {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);
    }

}
