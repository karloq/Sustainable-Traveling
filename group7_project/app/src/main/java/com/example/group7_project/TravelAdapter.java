package com.example.group7_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.TravelViewHolder> {
    private ArrayList<Travel> mtravelList;
    private ArrayList<Travel> mtravelListFull;
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
        private CardView cardView;
        private TextView textViewDuration;
        //private TextView textViewDuration;
        //Tracking button

        public TravelViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewFrom = itemView.findViewById(R.id.textview_travel_from);
            textViewDeparture = itemView.findViewById(R.id.textview_travel_departure);
            textViewArrival = itemView.findViewById(R.id.textview_travel_arrival);
            imageViewLine = itemView.findViewById(R.id.imageview_travel_line);
            textViewDuration = itemView.findViewById(R.id.textview_travel_duration);
            //textViewDuration = itemView.findViewById(R.id.textview_travel_duration);
            cardView = itemView.findViewById(R.id.cardview_travel);

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
        this.mtravelList = travelList;
        mtravelListFull = new ArrayList<>(travelList);
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
        holder.textViewDeparture.setText(fromtimetostring(currentTravel.getDeparture(),false));
        holder.textViewArrival.setText(fromtimetostring(currentTravel.getArrival(),true));
        holder.textViewDuration.setText("Duration "+(currentTravel.getArrival()-currentTravel.getDeparture()) + " min");
        if (currentTravel.getLine_1() == 7) {
            holder.imageViewLine.setImageResource(R.drawable.linje7);
        }else {
            holder.imageViewLine.setImageResource(R.drawable.linje6);
        }
        if(currentTravel.getBest()){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(MainActivity.getAppContext(), R.color.sus_green));
        }

        //holder.textViewDuration.setText(currentTravel.getDuration());
    }

    private String fromtimetostring(int time, boolean arrival) {
        StringBuilder string = new StringBuilder();
        int minutes = time % 60;
        int hours = (time - minutes) / 60;
        if (arrival) {
            if (minutes < 10) {
                string.append(" - " + hours + ":0" + minutes);
            } else {
                string.append(" - " + hours + ":" + minutes);
            }
        } else {
            if (minutes < 10) {
                string.append(hours + ":0" + minutes);
            } else {
                string.append(hours + ":" + minutes);
            }
        }

        return string.toString();
    }
    @Override
    public int getItemCount() {
        return mtravelList.size();
    }

}
