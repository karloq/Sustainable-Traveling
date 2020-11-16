package com.example.group7_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.TravelViewHolder> {
    private ArrayList<Travel> mtravelList;
    private OnItemClickListener mlistener;

    public  interface OnItemClickListener {
        void onItemClick(int position);

        void onTrackClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public static class TravelViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewFrom;
        private TextView textViewDeparture;
        private TextView textViewArrival;
        private ImageView imageViewLine;
        //private TextView textViewDuration;
        //Tracking button

        public TravelViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewFrom = itemView.findViewById(R.id.textview_travel_from);
            textViewDeparture = itemView.findViewById(R.id.textview_travel_departure);
            textViewArrival = itemView.findViewById(R.id.textview_travel_arrival);
            imageViewLine = itemView.findViewById(R.id.imageview_travel_line);
            //textViewDuration = itemView.findViewById(R.id.textview_travel_duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            /*trackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onTrackClick(position);
                        }
                }
            });
        }*/
        }
    }

    public TravelAdapter(ArrayList<Travel> travelList) {
        mtravelList = travelList;
        }

    @Override
    public TravelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_card_item, parent, false);
        TravelViewHolder tvh = new TravelViewHolder(itemView, mlistener);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TravelViewHolder holder, int position) {
        Travel currentTravel = mtravelList.get(position);

        holder.textViewFrom.setText(currentTravel.getFrom());
        holder.textViewDeparture.setText(currentTravel.getDeparture());
        holder.textViewArrival.setText(currentTravel.getArrival());
        if (currentTravel.getLine() == 7) {
            holder.imageViewLine.setImageResource(R.drawable.linje7);
        }else {
            holder.imageViewLine.setImageResource(R.drawable.linje6);
        }

        //holder.textViewDuration.setText(currentTravel.getDuration());
    }

    @Override
    public int getItemCount() {
        return mtravelList.size();
    }
}
