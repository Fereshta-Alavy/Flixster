package codepath.com.flixter;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import codepath.com.flixter.models.Movie;
import codepath.com.flixter.models.adapters.MovieAdapter;
import okhttp3.Headers;

public class  MainActivity extends AppCompatActivity {
    public static final String NOW_PLATING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainAtivity";
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        //create the adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this,movies);

        //set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        //set a Layout Manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        AsyncHttpClient client = new AsyncHttpClient();

        client.get(NOW_PLATING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray result = jsonObject.getJSONArray("results");
                    Log.i(TAG, "results: "+ result.toString());
                    movies.addAll(Movie.fromJsonArray(result));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "movies: "+ movies.size());
                } catch (JSONException e) {
                    Log.e(TAG,"hit json exception");
                }


            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });
    }
}
