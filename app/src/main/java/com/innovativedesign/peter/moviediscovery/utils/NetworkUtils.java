package com.innovativedesign.peter.moviediscovery.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by petershodeinde on 09/02/2017.
 */

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();



    public static URL buildURL(String sortString) throws MalformedURLException {
        Uri buildMovieUri = Uri.parse(Constants.MOVIE_BASE_URL).buildUpon()
                .appendPath(Constants.MOVIE)
                .appendPath(sortString)
                .appendQueryParameter(Constants.API_PARAMS, Constants.API_KEY)
                .build();
        URL url = new URL(buildMovieUri.toString());
        return url;
    }

    public static String getURLData(URL url) {

        String responseData = null;
        try {

            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(connection.getInputStream());
            responseData = convertStreamToString(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch(ProtocolException e) {
            Log.e("ProtocolException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException ", e.getMessage());
        }
        return responseData;
    }

    private static String convertStreamToString(InputStream inputStream) {
        String line;

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        }catch(IOException e) {
            Log.e("#readline IOException", e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e("#close steam", e.getMessage());
            }
        }
        return builder.toString();
    }

}
