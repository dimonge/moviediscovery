package com.innovativedesign.peter.moviediscovery.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieInfo implements Parcelable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String releaseDate;
    private String moviePoster;
    private String votes;
    private String plotSynopsis;

    public MovieInfo(Parcel source) {
        this.title = source.readString();
        this.releaseDate = source.readString();
        this.moviePoster = source.readString();
        this.votes = source.readString();
        this.plotSynopsis = source.readString();

    }
    public MovieInfo(String title, String releaseDate, String poster, String vote_average, String plotSynopsis) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.moviePoster = poster;
        this.votes = vote_average;
        this.plotSynopsis = plotSynopsis;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(releaseDate);
        out.writeString(moviePoster);
        out.writeString(votes);
        out.writeString(plotSynopsis);
    }

    static final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>() {

        @Override
        public MovieInfo createFromParcel(Parcel source) {
            return new MovieInfo(source);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    public String getVotes() {
        return votes;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }
}