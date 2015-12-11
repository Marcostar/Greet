package com.sagycorp.greet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Startup extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static final String PreferenceSETTINGS = "Preferences";
    public static final String DidYouKnow = "Didyouknow";
    public static final String Quotes = "quotes";
    public static final String Author = "name";
    public static final String IsHoroSet = "no";
    private String Horoscopes[],sign;
    public static final String HoroSign = "sign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(PreferenceSETTINGS, MODE_PRIVATE);
        editor = preferences.edit();
        if (preferences.getBoolean(IsHoroSet,true))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();

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
                    sign = "Aries";
                }
            });
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.putString(HoroSign, sign);
                    editor.putBoolean(IsHoroSet, false);
                    editor.apply();
                    Intent intent = new Intent(Startup.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
            builder.setCancelable(false);
            builder.create().show();
        }
        else
        {
            Intent intent = new Intent(Startup.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        }

        @Override
        public void onBackPressed()
        {
            finish();
        }
    }


