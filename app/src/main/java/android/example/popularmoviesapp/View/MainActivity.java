package android.example.popularmoviesapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.example.popularmoviesapp.Networking.NetworkUtils;
import android.example.popularmoviesapp.R;
import android.example.popularmoviesapp.Repository.DataExchanger;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import static android.example.popularmoviesapp.Networking.NetworkUtils.*;

public class MainActivity extends AppCompatActivity implements MainScreenAdapter.MyClickListener {

    private static RecyclerView mainScreenSingleView;
    private static DataExchanger dataExchanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataExchanger = new DataExchanger(getApplicationContext(), this);

        dataExchanger.dataGenerator(GET_MOST_POPULAR_MOVIES);

        mainScreenSingleView = (RecyclerView) findViewById(R.id.rv_main_screen);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 2);

        mainScreenSingleView.setLayoutManager(gridLayoutManager);

        mainScreenSingleView.setHasFixedSize(true);

        mainScreenSingleView.setAdapter(dataExchanger.mainScreenAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("my_extra_data", dataExchanger.cacheData.get(position));
        startActivity(detailIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_popularity){
            dataExchanger.dataGenerator(GET_MOST_POPULAR_MOVIES);
            return true;
        }
        if(id == R.id.action_Rating){
            dataExchanger.dataGenerator(GET_TOP_RATED_MOVIES);
            return true;
        }
        if(id == R.id.action_Favorite){
            dataExchanger.showFavoriteMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
