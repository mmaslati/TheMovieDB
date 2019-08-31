package com.mor.maslati.themoviedb.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.mor.maslati.themoviedb.R;
import com.mor.maslati.themoviedb.presenter.Engine;

public class MovieDetailsActivity extends AppCompatActivity {

    Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

//        Intent intent = getIntent();
//        engine = Engine.getInstance(null,null);
//        String message = intent.getStringExtra(engine.EXTRA_MOVIE_AS_STRING);


        
    }
}
