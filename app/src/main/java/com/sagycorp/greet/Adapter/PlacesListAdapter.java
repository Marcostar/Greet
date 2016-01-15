package com.sagycorp.greet.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sagycorp.greet.FullPlaceInfo;
import com.sagycorp.greet.Model.PlacesArchiveModel;
import com.sagycorp.greet.R;

import java.util.ArrayList;

/**
 * Created by Dzeko on 1/15/2016.
 */
public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.ViewHolder> {

    private TextView Index, Title;
    private ArrayList<PlacesArchiveModel> archiveModels;

    public PlacesListAdapter(ArrayList<PlacesArchiveModel> archiveModels) {
        this.archiveModels = archiveModels;
    }

    @Override
    public PlacesListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_story_archive_row, viewGroup, false);
        Index = (TextView) v.findViewById(R.id.Index);
        Title = (TextView) v.findViewById(R.id.Title);
        return new ViewHolder(v, Index, Title);
    }

    @Override
    public void onBindViewHolder(PlacesListAdapter.ViewHolder holder, int position) {

        holder.Index.setText(archiveModels.get(position).getIndex());
        holder.Title.setText(archiveModels.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return archiveModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView Index, Title;

        public ViewHolder(View itemView, TextView index, TextView title) {
            super(itemView);
            Index = index;
            Title = title;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();

            String URL = archiveModels.get(position).getURL();
            Intent CompleteStory = new Intent(v.getContext(), FullPlaceInfo.class);
            CompleteStory.putExtra("URL",URL);
            v.getContext().startActivity(CompleteStory);
        }
    }

}
