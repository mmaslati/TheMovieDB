package com.mor.maslati.themoviedb.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.mor.maslati.themoviedb.R;
import com.mor.maslati.themoviedb.presenter.Engine;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {


    Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.moviesShowingList);

        engine = Engine.getInstance(this,recyclerView);//new Engine(this,recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        engine.getMoviesToList();

    }


    @Override
    public void onItemClick(View view, int position) {

        JSONObject movie = (JSONObject)view.getTag();

        engine.showSelectedMovie(movie);
    }
}
