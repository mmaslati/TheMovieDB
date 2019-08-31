package com.mor.maslati.themoviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.mor.maslati.themoviedb.R;
import com.mor.maslati.themoviedb.presenter.Engine;


public class MovieDetailsActivity extends AppCompatActivity {

    Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ImageView moviePoster           = findViewById(R.id.moviePoster);
        TextView titleTextview          = findViewById(R.id.movieDetailsTitle);
        TextView yearTextview           = findViewById(R.id.movieDetailsYear);
        TextView ratingTextview         = findViewById(R.id.movieDetailsRating);
        TextView descriptionTextview    = findViewById(R.id.movieDetailsDescription);


        engine = Engine.getInstance(null,null);
        engine.showSelectedMovieInfo(getIntent(),moviePoster,titleTextview,yearTextview,ratingTextview,descriptionTextview);


    }
}
