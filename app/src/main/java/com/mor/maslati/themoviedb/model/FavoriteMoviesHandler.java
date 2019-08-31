package com.mor.maslati.themoviedb.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FavoriteMoviesHandler {

    public FavoriteMoviesHandler() {
    }


    final String favoriteMoviesFileName = "favorites.txt";


    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(favoriteMoviesFileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(favoriteMoviesFileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }



    private boolean doesFileExists(Context context){

        boolean exists = false;

        File file = context.getFileStreamPath(favoriteMoviesFileName);

        if (file.exists()) {
            exists=true;
        }

        return exists;
    }


    public void addMovieToFavorites(Context context, JSONObject movie){

        JSONArray favoriteMovies = null;
        try {
            favoriteMovies = new JSONArray("[]");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (doesFileExists(context)){

            String fileContent = readFromFile( context);

            try {

                favoriteMovies = new JSONArray(fileContent);

            } catch (JSONException e) {e.printStackTrace();}

        }

        favoriteMovies.put(movie);

        writeToFile(favoriteMovies.toString(), context);
    }

    @SuppressLint("NewApi")
    public void removeMovieFromFavorites(Context context, JSONObject movie){

        JSONArray favoriteMovies = null;
        try {
            favoriteMovies = new JSONArray("[]");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (doesFileExists(context)){

            String fileContent = readFromFile( context);

            try {

                favoriteMovies = new JSONArray(fileContent);

            } catch (JSONException e) {e.printStackTrace();}

        }

        int movieIdToRemove = 0;
        try {
            movieIdToRemove = movie.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < favoriteMovies.length(); i++) {
            try {

                JSONObject favoriteMovie = favoriteMovies.getJSONObject(i);

                int currentMovieId = favoriteMovie.getInt("id");

                if(currentMovieId == movieIdToRemove){
                    favoriteMovies.remove(i);
                    break;
                }

            } catch (JSONException e) {e.printStackTrace();}
        }

        writeToFile(favoriteMovies.toString(), context);
    }




    public boolean isThisAFavoriteMovie(Context context, JSONObject movie){

        boolean isThisFavorite = false;
        int movieIdToCheck = 0;

        JSONArray favoriteMovies = null;
        try {
            favoriteMovies = new JSONArray("[]");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (doesFileExists(context)){

            String fileContent = readFromFile( context);

            try {

                favoriteMovies = new JSONArray(fileContent);

            } catch (JSONException e) {e.printStackTrace();}

            try {
                movieIdToCheck = movie.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < favoriteMovies.length(); i++) {
                try {

                    JSONObject favoriteMovie = favoriteMovies.getJSONObject(i);

                    int currentMovieId = favoriteMovie.getInt("id");

                    if(currentMovieId == movieIdToCheck){
                        isThisFavorite = true;
                        break;
                    }

                } catch (JSONException e) {e.printStackTrace();}
            }

        }else{
            isThisFavorite = false;
        }



        return isThisFavorite;

    }
}
