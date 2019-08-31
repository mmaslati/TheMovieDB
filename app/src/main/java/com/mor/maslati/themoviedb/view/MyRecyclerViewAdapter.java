package com.mor.maslati.themoviedb.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.mor.maslati.themoviedb.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    //private List<String> mData;
    private List<JSONObject> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    //public MyRecyclerViewAdapter(Context context, List<String> data) {
    public MyRecyclerViewAdapter(Context context, List<JSONObject> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_movie_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //String movieTitle = mData.get(position);
        JSONObject movie = mData.get(position);

        String movieTitle = "Unknown";
        try {
            movieTitle = movie.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.myTextView.setText(movieTitle);

        holder.rowLayout.setTag(movie);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myTextView;
        LinearLayout rowLayout;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.movieTitle);
            rowLayout = itemView.findViewById(R.id.row);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        //return mData.get(id);
        return "tempValue";
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
