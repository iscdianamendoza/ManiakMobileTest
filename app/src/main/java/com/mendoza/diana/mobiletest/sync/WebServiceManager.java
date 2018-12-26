package com.mendoza.diana.mobiletest.sync;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

class WebServiceManager {

    Object postToWeb(String url, String params){
        String sRes;
        try{
            URL pUrl = new URL(url.concat(params));
            URLConnection urlConnection = pUrl.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            Log.i("ResponseMessage", httpURLConnection.getResponseMessage());
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream instream = httpURLConnection.getInputStream();
                sRes = convertStreamToString(instream);
                instream.close();
            }
            else {
                return new Exception();
            }
        } catch (Exception e){
            Log.e("postToWebError", e.toString());
            return e;
        }
        return sRes;
    }

    Object getToWeb(String url, String token) {
        String sRes;
        try {
            URL pUrl = new URL(url);
            URLConnection urlConnection = pUrl.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            Log.i("ResponseMessage", httpURLConnection.getResponseMessage());
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream instream = httpURLConnection.getInputStream();
                sRes = convertStreamToString(instream);
                instream.close();
            } else {
                return new Exception();
            }
        } catch (Exception e) {
            Log.e("getToWebError", e.toString());
            return e;
        }
        return sRes;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            Log.e("convertStream", e.toString());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e("convertStream", e.toString());
            }
        }
        return sb.toString();
    }

}
