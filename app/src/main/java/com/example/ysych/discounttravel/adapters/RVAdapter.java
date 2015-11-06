package com.example.ysych.discounttravel.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.model.Tour;

import java.util.List;

/**
 * Created by ysych on 06.11.2015.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    List<Tour> tours;

    public RVAdapter(List<Tour> tours) {
        this.tours = tours;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.tourTitle.setText(tours.get(position).getTitle());
        holder.tourPhoto.setImageResource(R.drawable.relax);
    }

    @Override
    public int getItemCount() {
        return tours.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tourTitle;
        ImageView tourPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            tourTitle = (TextView)itemView.findViewById(R.id.tour_title);
            tourPhoto = (ImageView)itemView.findViewById(R.id.tour_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}