package com.estimote.proximity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Article {


    Article() {

    }

    public String getArticle(String location) {
        String j = download("http://54.180.116.110/download.php?location=" + location.replace(" ", "%20"));
        try {
            JSONObject json = new JSONObject(j);
            return json.getString("body");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getHead(String location) {
        String j = download("http://54.180.116.110/download.php?location=" + location.replace(" ", "%20"));
        try {
            JSONObject json = new JSONObject(j);
            return json.getString("head");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getNotification(String location) {
        String j = download("http://54.180.116.110/download.php?location=" + location.replace(" ", "%20"));
        try {
            JSONObject json = new JSONObject(j);
            return json.getString("notification");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String download(String _url) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        StringBuilder data = new StringBuilder();

        try {
            url = new URL(_url);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                data.append(line);
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
        return data.toString();
    }
}
