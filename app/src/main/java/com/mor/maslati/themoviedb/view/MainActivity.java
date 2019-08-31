package com.mor.maslati.themoviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.mor.maslati.themoviedb.R;
import com.mor.maslati.themoviedb.presenter.Engine;


public class MainActivity extends AppCompatActivity {


    Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.moviesShowingList);

        engine = new Engine(this,recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        engine.getMoviesToList();
    }



    public void setUpAdapter(MyRecyclerViewAdapter adapter){




    }


}
