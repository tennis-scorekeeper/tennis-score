package com.tennis.score.activity;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Ali on 9/18/2018.
 */

class RetrieveFeedTask extends AsyncTask<String, Void, Integer> {

    private Exception exception;

    protected Integer doInBackground(String... urls) {
        try {
            String urlString = "https://mikenguyenmd.com/testWebService";

            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            System.out.println("Sending 'GET' request to URL : " + urlString);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            con.disconnect();

            JSONArray jsonArray = new JSONArray(response.toString());

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            System.out.println(jsonObject.get("name"));

        } catch (Exception e) {
            this.exception = e;
            e.printStackTrace();
            return null;
        }
        return 0;
    }
}
