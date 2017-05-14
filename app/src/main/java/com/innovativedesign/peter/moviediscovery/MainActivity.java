package com.innovativedesign.peter.moviediscovery;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.innovativedesign.peter.moviediscovery.utils.Constants;
import com.innovativedesign.peter.moviediscovery.utils.MovieInfo;
import com.innovativedesign.peter.moviediscovery.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String MOVIES_KEY = "results";

    private RecyclerView mMovieGridList;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mProgressBar;

    private Toast mToast;
    public JSONArray results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_movie);
        try {
            new MovieAsynTask().execute(NetworkUtils.buildURL(Constants.POPULAR_FILTER));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.most_popular:
                try {
                    new MovieAsynTask().execute(NetworkUtils.buildURL(Constants.POPULAR_FILTER));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.highest_rated:
                try {
                    new MovieAsynTask().execute(NetworkUtils.buildURL(Constants.TOP_RATED));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }




    private class MovieAsynTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL movieQuery = params[0];
            return NetworkUtils.getURLData(movieQuery);
        }

        @Override
        protected void onPostExecute(String movieResultData) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (movieResultData != null && !movieResultData.equals("")) {

                mMovieGridList = (RecyclerView) findViewById(R.id.rv_movie_thumbnail);
                mMovieGridList.setHasFixedSize(true);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                final Context context = MainActivity.this;
                try {
                    JSONObject data = new JSONObject(movieResultData);
                    results = data.getJSONArray(MOVIES_KEY);
                    mMovieAdapter = new MovieAdapter(results, context);

                    mMovieGridList.setAdapter(mMovieAdapter);
                    mMovieAdapter.setClickListener(new MovieAdapter.MovieItemClickListener() {
                        @Override
                        public void onClick(int position)  {
                            try {
                                JSONObject getMovie = results.getJSONObject(position);

                                Log.d(TAG, "onClick" + position + " : " + getMovie);

                                Class detailActivity = DetailActivity.class;
                                // String title, release_date, movie_poster, vote_average, plot_synopsis

                                String title = getMovie.get("title").toString();
                                String release_date = getMovie.get("release_date").toString();
                                String movie_poster = getMovie.get("poster_path").toString();
                                String vote_average = getMovie.get("vote_average").toString();
                                String plot_synopsis = getMovie.get("overview").toString();

                                Intent intent = new Intent(MainActivity.this, detailActivity);
                                MovieInfo movieInfo = new MovieInfo(title, release_date, movie_poster,
                                        vote_average, plot_synopsis);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("MovieInfo", movieInfo);
                                intent.putExtras(bundle);

                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    mMovieGridList.setLayoutManager(gridLayoutManager);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
