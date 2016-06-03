package edu.delamora.demo.gurunaviplaces.interfaces;

import java.util.ArrayList;

import edu.delamora.demo.gurunaviplaces.models.GuruPlace;

/**
 * Created by delamora on 16/05/27.
 */
public interface TaskCallbacks {
    void taskError();
    void taskProcessPlacesCallback(ArrayList<GuruPlace> places);
    void taskDownloadImagesCallback(ArrayList<GuruPlace> placesImages);
}

