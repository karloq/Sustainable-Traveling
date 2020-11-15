package com.example.group7_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class TravelAdapter extends ListAdapter<Travel, TravelAdapter.TravelHolder> {
    private OnItemClickListener listener;

    public TravelAdapter() {super(DIFF_CALLBACK);}

    private static final DiffUtil.ItemCallback<Travel> DIFF_CALLBACK = new DiffUtil.ItemCallback<Travel>() {
        @Override
        public boolean areItemsTheSame(@NonNull Travel oldItem, @NonNull Travel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Travel oldItem, @NonNull Travel newItem) {
            return oldItem.getDeparture().equals(newItem.getDeparture()) &&
                    oldItem.getLine() == newItem.getLine();
        }
    };

    @NonNull
    @Override
    public TravelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_card_item, parent, false);
        return new TravelHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelHolder holder, int position) {
        Travel currentTravel = getItem(position);

        holder.textViewFrom.setText(currentTravel.getFrom());
        holder.textViewDeparture.setText(currentTravel.getDeparture());
        holder.textViewArrival.setText(currentTravel.getArrival());
        //holder.textViewDuration.setText(currentTravel.getDuration());
    }

    public Travel getTravelAt(int position){
        return getItem(position);
    }

    class TravelHolder extends RecyclerView.ViewHolder {
        private TextView textViewFrom;
        private TextView textViewDeparture;
        private TextView textViewArrival;
        private TextView textViewDuration;

        public TravelHolder(@NonNull View itemView) {
            super(itemView);
            textViewFrom = itemView.findViewById(R.id.textview_travel_from);
            textViewDeparture = itemView.findViewById(R.id.textview_travel_departure);
            textViewArrival = itemView.findViewById(R.id.textview_travel_arrival);
            textViewDuration = itemView.findViewById(R.id.textview_travel_duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Travel travel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
