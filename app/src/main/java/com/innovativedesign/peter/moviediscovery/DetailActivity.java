package com.innovativedesign.peter.moviediscovery;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovativedesign.peter.moviediscovery.utils.Constants;
import com.innovativedesign.peter.moviediscovery.utils.MovieInfo;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final String MOVIE_TITLE_FIELD = "title";
    private static final String MOVIE_PLOT_SYNOPSIS = "plot_synopsis";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_VOTES = "votes";
    private static final String MOVIE_POSTER = "poster";
    
    private ImageView movieImage;
    private TextView movieTitle;
    private TextView movieDescription;
    private TextView movieVotes;
    private TextView movieReleaseDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        
        movieImage = (ImageView) findViewById(R.id.movie_image);
        movieTitle = (TextView) findViewById(R.id.movie_title);
        movieDescription = (TextView) findViewById(R.id.movie_description);
        movieVotes = (TextView) findViewById(R.id.votes);
        movieReleaseDate = (TextView) findViewById(R.id.release_date);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MovieInfo movieInfo = bundle.getParcelable("MovieInfo");
            String imageUrl = Constants.IMAGE_MOVIE_BASE_URL + Constants.IMAGE_SIZE_342 + movieInfo.getMoviePoster();
            Picasso.with(this).load(Uri.parse(imageUrl)).into(movieImage);

            movieTitle.setText(movieInfo.getTitle());
            movieDescription.setText(movieInfo.getPlotSynopsis());
            movieVotes.setText(movieInfo.getVotes());
            movieReleaseDate.setText(movieInfo.getReleaseDate());
        }
    }
}
