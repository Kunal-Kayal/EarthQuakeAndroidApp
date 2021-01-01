package com.example.android.earthquake;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

public final class QueryUtils {
    /** Sample JSON response for a USGS query
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Return a list of {@link EQuack} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<EQuack> extractEarthquakes(String JsonResponse) {


        // Create an empty ArrayList that we can start adding earthquakes to
         List<EQuack> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

             JSONObject root  = new JSONObject(JsonResponse);

             JSONArray features = root.optJSONArray("features");

             for(int i=0;i<features.length();i++){
                 JSONObject temp = features.getJSONObject(i);

                 JSONObject properties =temp.getJSONObject("properties");

                 double mag = Double.parseDouble(properties.getString("mag").toString());
                 String place = properties.getString("place").toString();
                 String time = properties.getString("time").toString();

                 String url = properties.getString("url").toString();

                 DecimalFormat formater = new DecimalFormat("0.00");
                 String output = formater.format(mag);

                 long currentTimeInMilisecond = Long.parseLong(time);

                 Date dateObject = new Date(currentTimeInMilisecond);



                 SimpleDateFormat dateFormater = new SimpleDateFormat("MMM dd, YYYY");
                 String dateToDisplay = dateFormater.format(dateObject);
                 SimpleDateFormat timeFormater = new SimpleDateFormat("hh:mm a");
                 String timeToDisplay =timeFormater.format(dateObject);

                 String placeArray[] = place.split("of");
                 String place1 ="";
                 String place2 ="";
                 if(placeArray.length==2){
                     place1=placeArray[0]+" of";
                     place2 = placeArray[1];

                 }else {
                     place1="Near";
                     place2 = placeArray[0];
                 }

                 earthquakes.add(new EQuack(output,place1,place2,dateToDisplay,timeToDisplay,url));
             }



        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    public static List<EQuack> fetchEarthquakesData(String url) {
        URL mUrl = createUrl(url);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(mUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        return  extractEarthquakes(jsonResponse);
    }

    static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
