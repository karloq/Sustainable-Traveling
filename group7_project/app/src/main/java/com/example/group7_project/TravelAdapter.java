package com.example.group7_project;

import android.util.Log;
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
    private static final String TAG = "TravelAdapter";
    private ArrayList<Travel> mtravelList;

    private OnItemClickListener mlistener;

    public  interface OnItemClickListener {
        void onItemClick(int position);

        void onLongItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public static class TravelViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewFrom;
        private TextView textViewDeparture;
        private TextView textViewArrival;
        private ImageView imageViewLine_1;
        private ImageView imageViewLine_2;
        private ImageView imageViewLine_3;
        private CardView cardView;
        private TextView textViewDuration;
        private TextView textViewLine_1;
        private TextView textViewLine_2;
        private TextView textViewLine_3;

        public TravelViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewFrom = itemView.findViewById(R.id.textview_travel_from);
            textViewDeparture = itemView.findViewById(R.id.textview_travel_departure);
            textViewArrival = itemView.findViewById(R.id.textview_travel_arrival);
            imageViewLine_1 = itemView.findViewById(R.id.imageview_travel_line_1);
            imageViewLine_2 = itemView.findViewById(R.id.imageview_travel_line_2);
            imageViewLine_3 = itemView.findViewById(R.id.imageview_travel_line_3);
            textViewDuration = itemView.findViewById(R.id.textview_travel_duration);
            textViewDuration = itemView.findViewById(R.id.textview_travel_duration);
            cardView = itemView.findViewById(R.id.cardview_travel);
            textViewLine_1 = itemView.findViewById(R.id.textview_travel_line_1);
            textViewLine_2 = itemView.findViewById(R.id.textview_travel_line_2);
            textViewLine_3 = itemView.findViewById(R.id.textview_travel_line_3);
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

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    boolean result = false;
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onLongItemClick(position);
                            result = true;
                        } else {
                            result = false;
                        }
                    }
                    return result;
                }
            });
        }
    }

    public TravelAdapter(ArrayList<Travel> travelList) {
        this.mtravelList = travelList;
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


        lineGraphics(currentTravel.getLine_1(), holder.imageViewLine_1, holder.textViewLine_1);
        lineGraphics(currentTravel.getLine_2(), holder.imageViewLine_2, holder.textViewLine_2);
        lineGraphics(currentTravel.getLine_3(), holder.imageViewLine_3, holder.textViewLine_3);


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

    public void lineGraphics(String line, ImageView imageView, TextView textView) {
        Log.d(TAG, "line: " + line);
        if(line == null){
        }else{
            switch (line) {
                case "7":
                    imageView.setImageResource(R.drawable.linje7);
                    break;
                case "6":
                    imageView.setImageResource(R.drawable.linje6);
                    break;
                case "241":
                    imageView.setImageResource(R.drawable.linje241);
                    break;
                case "50":
                    imageView.setImageResource(R.drawable.linje50);
                    break;
                case "60":
                    imageView.setImageResource(R.drawable.linje60);
                    break;
                case "9":
                    imageView.setImageResource(R.drawable.linje9);
                    break;
                case "1":
                    imageView.setImageResource(R.drawable.linje1);
                    break;
                case "16":
                    imageView.setImageResource(R.drawable.linje16);
                    break;
                case "13":
                    imageView.setImageResource(R.drawable.linje13);
                    break;
                case "10":
                    imageView.setImageResource(R.drawable.linje10);
                    break;
                case "285ÄLV":
                    imageView.setImageResource(R.drawable.linje285);
                    break;
                case "286ÄLV":
                    imageView.setImageResource(R.drawable.linje286);
                    break;
                case "55":
                    imageView.setImageResource(R.drawable.linje55);
                    break;
                case "58":
                    imageView.setImageResource(R.drawable.linje58);
                    break;
                case "WALK":
                    imageView.setImageResource(R.drawable.linjew);
                    break;
                case "":
                    break;
                default:
                    imageView.setImageResource(R.drawable.linjedefault);
                    textView.setText(line);
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mtravelList.size();
    }

    public Travel getTravel(int position){
        return mtravelList.get(position);
    }

}
