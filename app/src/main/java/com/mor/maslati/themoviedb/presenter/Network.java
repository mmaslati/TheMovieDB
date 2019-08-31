package com.mor.maslati.themoviedb.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mor.maslati.themoviedb.R;

import java.util.concurrent.Callable;

public class Network {

    Engine engine;
    final String limitToEnglish = "&language=en";
    String key;
    String baseUrl;
    Context context;

    public Network(Context context, Engine engine) {

        this.context = context;
        this.engine  = engine;
        this.baseUrl = context.getString(R.string.movie_db_url);
        this.key     = "&api_key="+context.getString(R.string.movie_db_api_key);
    }


    // Helper For all API Calls
    private void sendIt(String urlString, Response.Listener successListener, Response.ErrorListener failedListener){

        RequestQueue queue = Volley.newRequestQueue(this.context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlString,successListener, failedListener );

        queue.add(stringRequest);
    }


    public void getMoviesInTheaters(String from, String until ){



        Response.Listener successGettingMovies = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("MorDebug",response);

                engine.filterMoviesAndAdd(response);

            }
        };

        Response.ErrorListener failedGettingMovies = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MorDebug",error.toString());
            }
        };

        //2019-07-23
        //2019-07-26
        // = 37 Days

        String getMoviesUrl = baseUrl+"/discover/movie?primary_release_date.gte="+from+"&primary_release_date.lte="+until+this.key+limitToEnglish  ;

        Log.d("MorDebug",getMoviesUrl);

        sendIt(getMoviesUrl,successGettingMovies,failedGettingMovies);
    }



}
