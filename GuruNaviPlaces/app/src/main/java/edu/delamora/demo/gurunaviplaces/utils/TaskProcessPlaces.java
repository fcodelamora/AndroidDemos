package edu.delamora.demo.gurunaviplaces.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import edu.delamora.demo.gurunaviplaces.MainActivityTests;
import edu.delamora.demo.gurunaviplaces.interfaces.TaskCallbacks;
import edu.delamora.demo.gurunaviplaces.models.GuruPlace;


public class TaskProcessPlaces extends AsyncTask<JSONArray, Integer, ArrayList<GuruPlace>> {

    //Interfacing to loosely couple with the Activity
    public TaskCallbacks delegate = null;

    public TaskProcessPlaces(Activity activity) {
        delegate = ((MainActivityTests) activity);
    }

    @Override
    protected ArrayList<GuruPlace> doInBackground(JSONArray... places) {

        ArrayList<GuruPlace> receivedPlaces = new ArrayList<>();

        try {
            receivedPlaces = convertJSONtoGuruPlaces(places[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return receivedPlaces; //TODO check this null
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        //TODO setProgressPercent(progress[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<GuruPlace> result) {
        if(delegate != null){
            //TODO Check on weak references as this might produce memory leaks if the Main Acitivity
            //     is not there anymore.
            delegate.taskProcessPlacesCallback(result);
        }else{
           Log.e("@onPostExecute","Main activity is not available at the moment");
            //TODO Cache the results?
        }
    }

    private ArrayList<GuruPlace> convertJSONtoGuruPlaces(JSONArray receivedJSON) throws
            JSONException {

        /*Create an ArrayList full of our custom objects for GURUNAVI places, GuruPLace, for easier
        handling */
        ArrayList<GuruPlace> converted = new ArrayList<>();
        for (int i = 0; i < receivedJSON.length(); i++) {
            converted.add(new GuruPlace(
                    null, //Bitmap place_photo_1 used later while we don't have a cache
                    null, //Bitmap place_photo_2 used later while we don't have a cache
                    /* Using opt instead of get to prevent unnecessary NullExceptions, also divided
                     * the array fields into individual fields for convenience handling the JSON */
                    receivedJSON.getJSONObject(i).optString("id", ""),
                    receivedJSON.getJSONObject(i).optString("name", ""),
                    receivedJSON.getJSONObject(i).optString("name_kana", ""),
                    receivedJSON.getJSONObject(i).optDouble("latitude", -1),
                    receivedJSON.getJSONObject(i).optDouble("longitude", -1),
                    receivedJSON.getJSONObject(i).optString("category", ""),
                    receivedJSON.getJSONObject(i).optString("url", ""),
                    receivedJSON.getJSONObject(i).optString("url_mobile", ""),
                    //img_url
                    receivedJSON.getJSONObject(i).optJSONObject("image_url")
                            .optString("shop_image1", ""),
                    receivedJSON.getJSONObject(i).optJSONObject("image_url")
                            .optString("shop_image2", ""),
                    receivedJSON.getJSONObject(i).optJSONObject("image_url")
                            .optString("qrcode", ""),
                    receivedJSON.getJSONObject(i).optString("address", ""),
                    receivedJSON.getJSONObject(i).optString("tel", ""),
                    receivedJSON.getJSONObject(i).optString("tel_sub", ""),
                    receivedJSON.getJSONObject(i).optString("fax", ""),
                    receivedJSON.getJSONObject(i).optString("opentime", ""),
                    receivedJSON.getJSONObject(i).optString("holiday", ""),
                    //access
                    receivedJSON.getJSONObject(i).optJSONObject("access")
                            .optString("line", ""),
                    receivedJSON.getJSONObject(i).optJSONObject("access")
                            .optString("station", ""),
                    receivedJSON.getJSONObject(i).optJSONObject("access")
                            .optString("station_exit", ""),
                    receivedJSON.getJSONObject(i).optJSONObject("access")
                            .optInt("walk", -1),
                    receivedJSON.getJSONObject(i).optJSONObject("access")
                            .optString("note", ""),
                    receivedJSON.getJSONObject(i).optInt("parking_lots", -1),
                    //PR
                    receivedJSON.getJSONObject(i).optJSONObject("pr")
                            .optString("pr_short", ""),
                    receivedJSON.getJSONObject(i).optJSONObject("pr")
                            .optString("pr_long", ""),
                    receivedJSON.getJSONObject(i).optInt("budget", -1),
                    receivedJSON.getJSONObject(i).optInt("party", -1),
                    receivedJSON.getJSONObject(i).optInt("lunch", -1),
                    receivedJSON.getJSONObject(i).optString("credit_card", ""),
                    receivedJSON.getJSONObject(i).optString("e_money", ""),
                    //flags
                    receivedJSON.getJSONObject(i).optJSONObject("flags")
                            .optString("mobile_site", ""),
                    receivedJSON.getJSONObject(i).optJSONObject("flags")
                            .optString("mobile_coupon", ""),
                    receivedJSON.getJSONObject(i).optJSONObject("flags")
                            .optString("pc_coupon", "")
            ));
        }
        return converted;
    }

}
