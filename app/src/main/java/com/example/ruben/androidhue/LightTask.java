package com.example.ruben.androidhue;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

/**
 * Created by Ruben on 27/10/2015.
 */
public class LightTask extends AsyncTask<String, Void, String> {


    // Static's
    private static final String TAG = "LightTask";
    private static final String urlString = "http://192.168.1.179/api/626b39467d5a4f110a85bc2f0843b7/lights/";

    private newLightsAvailable listener = null;

    public LightTask(newLightsAvailable listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        int responseCode = -1;
        String response = "";

        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {

                return null;
            }

            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
            }
        } catch (MalformedURLException e) {
            Log.e("TAG", e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", e.getLocalizedMessage());
            return null;
        }

        return response;
    }

    protected void onProgressUpdate(Integer... progress) {
        Log.i(TAG, progress.toString());
    }

    protected void onPostExecute(String response) {

        JSONObject jsonObjects;

        try {
            jsonObjects = new JSONObject(response);
            Iterator x = jsonObjects.keys();
            JSONArray jsonArray = new JSONArray();

            while (x.hasNext()){
                String key = (String) x.next();
                jsonArray.put(jsonObjects.getJSONObject(key).put("key",key));
            }

            for(int idx = 0; idx < jsonArray.length(); idx++) {

                JSONArray bla = jsonArray;


                JSONObject light = jsonArray.getJSONObject(idx);
                JSONObject state = light.getJSONObject("state");

                LightModel l = new LightModel();

                l.id = light.getInt("key");
                l.name = light.getString("name");
                l.stateOn = state.getString("on");
                l.stateBrightness = state.getString("bri");

                if(state.has("hue")){
                    l.stateHue = state.getString("hue");
                }

                if(state.has("sat")){
                    l.stateSaturation = state.getString("sat");
                }

                listener.processFinished(l);
            }

        } catch( JSONException ex) {
            Log.e("ERROR_"+TAG, ex.getLocalizedMessage());
        }
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public interface newLightsAvailable {
        void processFinished(LightModel output);
    }
}

class LightPostTask extends AsyncTask<Void,Void,Void>{

    private HttpURLConnection client;
    private URL url;
    private JSONObject response = new JSONObject();
    private LightModel lightModel;
    private Boolean lightStateOn;

    public LightPostTask(LightModel l, boolean b){
        lightModel = l;
        lightStateOn = b;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        URL url;
        HttpURLConnection urlConn;

        try {

            JSONObject jsonParam = new JSONObject();
            url = new URL("http://192.168.1.179/api/626b39467d5a4f110a85bc2f0843b7/lights/"+lightModel.id.toString()+"/state");

            urlConn = (HttpURLConnection)url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestMethod("PUT");
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");

            jsonParam.put("on",lightStateOn);

            OutputStream os = new BufferedOutputStream(urlConn.getOutputStream());
            os.write(jsonParam.toString().getBytes("UTF-8"));
            os.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
