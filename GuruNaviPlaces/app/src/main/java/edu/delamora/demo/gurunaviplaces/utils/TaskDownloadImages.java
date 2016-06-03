package edu.delamora.demo.gurunaviplaces.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;

import edu.delamora.demo.gurunaviplaces.GuruPlaceInformationScrollingActivity;
import edu.delamora.demo.gurunaviplaces.MainActivityTests;
import edu.delamora.demo.gurunaviplaces.interfaces.TaskCallbacks;
import edu.delamora.demo.gurunaviplaces.models.GuruPlace;

/**
 * Created by delamora on 16/06/02.
 */
public class TaskDownloadImages extends AsyncTask< ArrayList<GuruPlace>, Void,
        ArrayList<GuruPlace>> {
    //Call back interface
    public TaskCallbacks delegate;
    private boolean downloadAllImagesFlag;

     /*Creating the constructors for each type of activity that uses this Task so the interface
       can be called later from the delegate*/
    public TaskDownloadImages(MainActivityTests activity) {
        delegate =  activity;
        downloadAllImagesFlag = false;
    }
    public TaskDownloadImages(GuruPlaceInformationScrollingActivity activity) {
        delegate = activity;
        downloadAllImagesFlag = true;
    }

    protected ArrayList<GuruPlace> doInBackground( ArrayList<GuruPlace>... places) {



        Bitmap tmpBitmap;
        //Look for the online image, skip if nothing received to keep the default image.
        //As we may work with multiple urls at once, we stick to use the ArrayList
       // for (int i = 0; i < places[0].size(); i++) {
        for (GuruPlace singlePlace: places[0]) {

            //first image download
            tmpBitmap = getOnlineImage(singlePlace.getFormattedImage_url__shop_image(
                    singlePlace.getImage_url__shop_image1()));
            if (tmpBitmap != null) {
                singlePlace.setBitmap_image_url__shop_image1(tmpBitmap);
            }

            if(downloadAllImagesFlag){ //only download when neccesary
                tmpBitmap = getOnlineImage(singlePlace.getFormattedImage_url__shop_image(
                        singlePlace.getImage_url__shop_image2()));
                if (tmpBitmap != null) {
                    singlePlace.setBitmap_image_url__shop_image2(tmpBitmap);
                }
            }

       }
        return places[0];
    }

    private Bitmap getOnlineImage(String url) {
        Bitmap tmpImage;
        if ( url != null  ) {
            try {
                InputStream in = new java.net.URL(url).openStream();
                tmpImage = BitmapFactory.decodeStream(in);
                return tmpImage;
            } catch (Exception e) {
                Log.e("@TaskDownloadImages", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }

    protected void onPostExecute(ArrayList<GuruPlace> result) {
        delegate.taskDownloadImagesCallback(result);
    }
}
