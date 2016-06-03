package edu.delamora.demo.gurunaviplaces.utils;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.delamora.demo.gurunaviplaces.MainActivityTests;
import edu.delamora.demo.gurunaviplaces.R;
import edu.delamora.demo.gurunaviplaces.interfaces.TaskCallbacks;

/**
 * Created by delamora on 16/05/26.
 */

//Places = Restaurants, bars, etc...
public class TaskGetNearbyPlaces extends AsyncTask<Object, Integer, JSONObject> {

    public TaskCallbacks delegate;
    //API App params
    private String API_URL          = "http://api.gnavi.co.jp/RestSearchAPI/20150630/?";
    private String API_KEY          = "37ea75c0a8541c51bb542457c545f694";
    private String RESPONSE_FORMAT  = "json";
    private String NUM_OF_PLACES    = "200"; //Default is 10, trying 50

    //User selected params
    private int apiRange = 2; // 1:300m、2:500m、3:1000m、4:2000m、5:3000m (Values from GURUNAVI API)
    private Activity appContext;

    public TaskGetNearbyPlaces(MainActivityTests activity, int rcvRange) {
        //On construction, define the reference activity and the user selected range
        this.appContext = activity;
        this.apiRange = rcvRange;
        this.delegate = activity;
    }

    @Override
    protected JSONObject doInBackground(Object... params) {

        //Don't process NULL locations
        if(params.length < 1 ){
            return null;
        }else if( params[0] == null){
            return null;
        }

        Location currentLocation = (Location) params[0];
        JSONObject response = null;

        try {
            /*Predefine and error we can quickly verify if there is any issue connecting with the
             service*/
            response = new JSONObject("{\"error\":-1}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        URL url = null;
        try {
            url = new URL(API_URL + "keyid=" + API_KEY + "&format=" + RESPONSE_FORMAT +  "&range=" +
                    apiRange + "&latitude=" + ""+(currentLocation.getLatitude())+ "&longitude=" +
                    ""+(currentLocation.getLongitude()) + "&hit_per_page="+NUM_OF_PLACES);
//           TEST  url = new URL("http://api.gnavi.co.jp/RestSearchAPI/20150630/?keyid="+
//             API_KEY+"&format=json"+ "&hit_per_page="+NUM_OF_PLACES);
            Log.e("URL ",(API_URL + "keyid=" + API_KEY + "&format=" + RESPONSE_FORMAT +  "&range=" +
                    apiRange + "&latitude=" + ""+(currentLocation.getLatitude())+ "&longitude=" +
                    ""+(currentLocation.getLongitude()) + "&hit_per_page="+NUM_OF_PLACES));
            // apiRange+ "&latitude=" + ""+(currentLocation.getLatitude())+ "&longitude=" + "" +
            // (currentLocation.getLongitude()));


            //Connect to service and get the data (JSON)
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                //Log.d.println("Webservice Response:", stringBuilder.toString());
                response = new JSONObject(stringBuilder.toString());

                return response;

            } catch (JSONException e) {
                e.printStackTrace();
            } finally{
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }
    @Override
    protected void onProgressUpdate(Integer... progress) {
        //TODO setProgressPercent(progress[0]);
    }
    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            if(result == null){
                delegate.taskError();
                Toast.makeText(appContext, "No available location" , Toast.LENGTH_SHORT).show();
            } //Fidling with network status and GPS permissions allows result to be null here
            if(result != null && result.has("error")){
                delegate.taskError();
                Toast.makeText(appContext, appContext.getString(R.string.error_occurred),
                        Toast.LENGTH_SHORT).show();
            }else{
                if(result.has("rest")){ //rest = Restaurants (places)
                    new TaskProcessPlaces(appContext).execute(result.getJSONArray("rest"));
                }else{
                    delegate.taskError();
                    Toast.makeText(appContext, appContext.getString(R.string.zero_results) ,
                            Toast.LENGTH_SHORT).show();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

}
