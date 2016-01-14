package com.sagycorp.greet.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagycorp.greet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Stories extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private StoryTabs storyTabs;

    public Stories() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.all_stories, container, false);

        storyTabs = new StoryTabs(getChildFragmentManager());

        viewPager = (ViewPager) rootView.findViewById(R.id.container);
        viewPager.setAdapter(storyTabs);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    public class StoryTabs extends FragmentStatePagerAdapter
    {

        public StoryTabs(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new Fragment();
            switch (position)
            {
                case 0:
                    fragment = new Story();
                    break;
                case 1:
                    fragment = new StoryArchive();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle (int position)
        {
            String title = null;
            switch (position)
            {
                case 0:
                    title = "Today";
                    break;
                case 1:
                    title = "Archive";
                    break;
            }
            return title;
        }
    }

}
