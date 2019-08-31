package com.mor.maslati.themoviedb.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mor.maslati.themoviedb.model.FavoriteMoviesHandler;
import com.mor.maslati.themoviedb.model.Network;
import com.mor.maslati.themoviedb.view.MovieDetailsActivity;
import com.mor.maslati.themoviedb.view.MyRecyclerViewAdapter;
import com.squareup.picasso.Picasso;

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

    private static Engine INSTANCE = null;

    private Engine(Context context, RecyclerView recyclerView) {
        this.recyclerView   = recyclerView;
        this.context        = context;
    }

    public static Engine getInstance(Context context, RecyclerView recyclerView) {
        if (INSTANCE == null) {
            INSTANCE = new Engine(context,recyclerView);
        }
        return(INSTANCE);
    }

    Context context;
    RecyclerView recyclerView;

    public final String EXTRA_MOVIE_AS_STRING = "EXTRA_MOVIE_AS_STRING";

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

        //ArrayList<String> movies = new ArrayList<>();
        ArrayList<JSONObject> movies = new ArrayList<>();


        JSONObject reader;
        try {
            reader = new JSONObject(fullMoviesAsJSON);

            JSONArray results = (JSONArray) reader.get("results");


            for (int i = 0; i < results.length(); i++) {
                JSONObject movie = results.getJSONObject(i);

                String language = (String)movie.get("original_language");

                //Log.d("MorDebug","i = "+ String.valueOf(i)+": "+(String)movie.get("title"));

                //movies.add((String)movie.get("title"));
                movies.add(movie);
            }


            fillUpList( movies );


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //private void fillUpList( ArrayList<String> moviesToShow ){
    private void fillUpList( ArrayList<JSONObject> moviesToShow ){

        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(context, moviesToShow);
        adapter.setClickListener((MyRecyclerViewAdapter.ItemClickListener)context);
        recyclerView.setAdapter(adapter);

        //return adapter;
    }

    public void showSelectedMovie(JSONObject selectedMovie){


        Intent intent = new Intent(context, MovieDetailsActivity.class);

        String movieAsJson = selectedMovie.toString();
        intent.putExtra(EXTRA_MOVIE_AS_STRING,movieAsJson);
        context.startActivity(intent);

    }

    public void createPosterUrlAndShow(ImageView posterImageView, String posterImageName){

        String baseImagesUrl = "https://image.tmdb.org/t/p/w500";

        Picasso.get()
                .load(baseImagesUrl+posterImageName)
                .into(posterImageView);

    }


    public void showSelectedMovieInfo(Intent intent, ImageView posterImageview, TextView titleTextview, TextView yearTextview, TextView ratingTextview, TextView descriptionTextview){

        String movieAsString = intent.getStringExtra(EXTRA_MOVIE_AS_STRING);

        JSONObject movie;

        try {
            movie = new JSONObject(movieAsString);

            createPosterUrlAndShow(posterImageview,(String)movie.get("poster_path"));

            String Title = titleTextview.getText()+((String)movie.get("title"));
            titleTextview.setText(Title);

            String Year = yearTextview.getText()+((String)movie.get("release_date")).substring(0,4);
            yearTextview.setText(Year);

            String Rating = ratingTextview.getText()+(String.valueOf(movie.get("vote_average")));
            ratingTextview.setText(Rating);

            String Description = descriptionTextview.getText()+((String)movie.get("overview"));
            descriptionTextview.setText(Description);


            // TESTING TESTING TESTING TESTING
            /*
            FavoriteMoviesHandler handler = new FavoriteMoviesHandler();

            boolean isThisFavorite = handler.isThisAFavoriteMovie(context,movie);

            if (!isThisFavorite){

                handler.addMovieToFavorites(context,movie);
            }

            boolean isThisFavorite2 = handler.isThisAFavoriteMovie(context,movie);

            if (isThisFavorite2){

                handler.removeMovieFromFavorites(context,movie);
            }

            boolean isThisFavorite3 = handler.isThisAFavoriteMovie(context,movie);

            */


        } catch (JSONException e) {
            e.printStackTrace();
        }




    }


}
