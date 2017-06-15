package edu.wmich.cs.weather;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherFetcher {

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public String fetchItems(String zip, Context context) {
        try {
            String url = Uri.parse("https://query.yahooapis.com/v1/public/yql")
                    .buildUpon()
                    .appendQueryParameter("q", "select * from weather.forecast where woeid in (select woeid from geo.places where text=\"" + zip + "\" limit 1)")
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("diagnostics", "true")
                    .appendQueryParameter("env", "store://datatables.org/alltableswithkeys")
                    .appendQueryParameter("callback", "")
                    .build().toString();
            String jsonString = getUrlString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            return parseItems(jsonBody, context);
        } catch (IOException e) {
            Log.e("WeatherFetcher", "fetchItemsIo: " + e.getMessage(), e);
        } catch (JSONException e) {
            Log.e("WeatherFetcher", "fetchItemsJSON: ", e);
        }
        return null;
    }

    public String parseItems(JSONObject json, Context context) throws IOException, JSONException {

        JSONObject query = json.getJSONObject("query");
        JSONObject results = query.getJSONObject("results");
        JSONObject channel = results.getJSONObject("channel");
        JSONObject item = channel.getJSONObject("item");
        JSONObject condition = item.getJSONObject("condition");

        return condition.getString("temp");
    }


}
