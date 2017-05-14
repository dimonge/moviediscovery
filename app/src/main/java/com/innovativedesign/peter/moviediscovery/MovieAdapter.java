package com.innovativedesign.peter.moviediscovery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.innovativedesign.peter.moviediscovery.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by petershodeinde on 10/02/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder > {

    private static final String TAG = MovieAdapter.class.getSimpleName();


    private final Context context;

    private String[] movieList;
    private JSONArray movieData;

    public MovieItemClickListener movieItemClicklistener;

    public void setClickListener(MovieItemClickListener  listener) {
        this.movieItemClicklistener = listener;
    }

    public interface MovieItemClickListener {
        void onClick(int position);
    }

    public MovieAdapter(JSONArray data, Context context) {
        this.context = context;
        this.movieData = data;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);
        MovieViewHolder  viewHolder = new MovieViewHolder (view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder  holder, final int position) {
        JSONObject movieIndex = null;
        try {

            movieIndex = movieData.getJSONObject(position);
            Log.d(TAG, '#' + movieIndex.get("title").toString());
            final String imageUrl = Constants.IMAGE_MOVIE_BASE_URL + Constants.IMAGE_SIZE_500 + movieIndex.get("poster_path").toString();

            Context context = holder.mMovieThumbnail.getContext();
            Picasso.with(context).load(imageUrl).into(holder.mMovieThumbnail);
            View.OnClickListener listener =  new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieItemClicklistener.onClick(position);
                }
            };

            holder.mMovieThumbnail.setOnClickListener(listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return movieData.length();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView mMovieThumbnail;

        public MovieViewHolder(View view) {
            super(view);
            mMovieThumbnail = (ImageView) view.findViewById(R.id.ib_movie_thumbnail);

        }
    }

    public void setMovieList(JSONArray movieList) {
        movieData = movieList;
        notifyDataSetChanged();
    }
}
