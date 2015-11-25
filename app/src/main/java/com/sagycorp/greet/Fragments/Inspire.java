package com.sagycorp.greet.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sagycorp.greet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Inspire extends Fragment {

    private Button button;
    public Inspire() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inspire, container, false);
        button = (Button) rootView.findViewById(R.id.shareButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","sagar@sagycorp.com",null));
                Email.putExtra(Intent.EXTRA_SUBJECT,"My Story");
                Email.putExtra(Intent.EXTRA_TEXT,"***Do not forget to tell your NAME and your COUNTRY (So We can put your name along with your story.)***");
                startActivity(Intent.createChooser(Email, "Share Your Story with :"));
            }
        });
        return rootView;
    }

}
