package com.assistant.priyank.android.assistant.Utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by primanda on 4/16/2017.
 */

public class NetworkUtils {

    public static URL GetUrl(String baseUrl, String quoteList)
    {
        Uri.Builder uriBuilder =  Uri.parse(baseUrl.toString()).buildUpon();
        try {
            return new URL(uriBuilder.appendQueryParameter("q", quoteList).build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
