package com.tennis.score.activity;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Ali on 9/18/2018.
 */

class RetrieveFeedTask extends AsyncTask<String, Void, String> {

    private Exception exception;
    private String myURL;
    private String[] params;

    public RetrieveFeedTask(String urlToRun, String[] inputParams) {
        myURL = urlToRun;
        params = inputParams;
    }

    protected String doInBackground(String... urls) {
        try {
            String urlString = myURL + getParamURLString();

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

            return response.toString();

            /*JSONArray jsonArray = new JSONArray(response.toString());

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            System.out.println(jsonObject.get("name"));*/

        } catch (Exception e) {
            this.exception = e;
            e.printStackTrace();
            return null;
        }
    }

    private String getParamURLString() {
        String result = "?";
        if (params.length % 2 == 0) {
            for (int i = 0; i < params.length; i += 2) {
                result += params[i] + "=" + params[i+1] + "&";
            }
        }
        return result;
    }
}
