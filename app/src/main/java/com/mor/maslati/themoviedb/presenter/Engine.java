package com.mor.maslati.themoviedb.presenter;

import android.content.Context;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mor.maslati.themoviedb.view.MyRecyclerViewAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





public class Engine {

    public Engine(Context context, RecyclerView recyclerView) {
        this.recyclerView   = recyclerView;
        this.context        = context;
    }

    Context context;
    RecyclerView recyclerView;

    private Map getTwoDatesOfRelease(){

        SimpleDateFormat theMovieDbFormat = new SimpleDateFormat("yyyy-MM-dd");

        long howFarBackToGetMovies = 3196800000L;

        Date today      = Calendar.getInstance().getTime();
        Date monthBack = new Date(today.getTime() - howFarBackToGetMovies);

        String todayFormattedDate       = theMovieDbFormat.format(today);
        String monthBackFormattedDate   = theMovieDbFormat.format(monthBack);

        //Log.d("MorDebug","Todays Date    => " + todayFormattedDate);
        //Log.d("MorDebug","monthBack Date => " + monthBackFormattedDate);

        Map<String, String> dates = new HashMap<>();

        dates.put("today" , todayFormattedDate);
        dates.put("monthBack", monthBackFormattedDate);

        return dates;
    }



    public void getMoviesToList(){

        Map dates = getTwoDatesOfRelease();

        String today  = dates.get("today").toString();
        String monthBack = dates.get("monthBack").toString();

        Network network = new Network(this.context, this);
        network.getMoviesInTheaters(monthBack, today);
    }


    public void filterMoviesAndAdd(String fullMoviesAsJSON){

        ArrayList<String> movies = new ArrayList<>();


        JSONObject reader;
        try {
            reader = new JSONObject(fullMoviesAsJSON);

            JSONArray results = (JSONArray) reader.get("results");


            for (int i = 0; i < results.length(); i++) {
                JSONObject movie = results.getJSONObject(i);

                String language = (String)movie.get("original_language");

                //Log.d("MorDebug","i = "+ String.valueOf(i)+": "+(String)movie.get("title"));

                movies.add((String)movie.get("title"));
            }


            fillUpList( movies );


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void fillUpList( ArrayList<String> moviesToShow ){

        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(context, moviesToShow);
        adapter.setClickListener((MyRecyclerViewAdapter.ItemClickListener)context);
        recyclerView.setAdapter(adapter);

        //return adapter;
    }


}
