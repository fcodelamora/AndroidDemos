package edu.delamora.demo.gurunaviplaces.utils;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;

import edu.delamora.demo.gurunaviplaces.models.GuruPlace;

/**
 * Created by delamora on 16/05/27.
 */
public class FragmentPlacesListViewRetained extends Fragment {

    // Retaining the already downloaded list of places
    private ArrayList<GuruPlace> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setData(ArrayList<GuruPlace> data) {
        this.data = data;
    }

    public ArrayList<GuruPlace> getData() {
        return data;
    }
}
