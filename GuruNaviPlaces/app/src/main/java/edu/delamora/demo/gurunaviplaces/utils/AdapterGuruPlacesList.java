package edu.delamora.demo.gurunaviplaces.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import edu.delamora.demo.gurunaviplaces.R;
import edu.delamora.demo.gurunaviplaces.models.GuruPlace;

/**
 * Created by delamora on 16/05/26.
 */
public class AdapterGuruPlacesList extends ArrayAdapter<GuruPlace> {

    private boolean lockImage = false;
    // This adapter transforms the GURUNAVI places(restaurants, bars, etc) into rows
    public AdapterGuruPlacesList(Context context, ArrayList<GuruPlace> places) {
        super(context, 0, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        GuruPlace place = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.sample_places_list_row_view, parent, false);
            }
            // Lookup view for data population
            ImageView image1 = (ImageView) convertView.findViewById(R.id.places_row_image1);

            TextView name    = (TextView)  convertView.findViewById(R.id.places_row_name);
            TextView access  = (TextView)  convertView.findViewById(R.id.places_row_access);
            TextView budget  = (TextView)  convertView.findViewById(R.id.places_row_budget);

            //Populate the data
            name.setText(place.getName());
            access.setText(place.getFormattedAccess(true, true, true, true));
            budget.setText(place.getBudget() > 0 ?    //Format 3000 as 3,000, etc
                    "" + NumberFormat.getNumberInstance(Locale.JAPANESE)
                            .format(place.getBudget()) :
                    getContext().getResources().getString(R.string.no_information));

            if (place.getBitmap_image_url__shop_image1() != null) {
                image1.setImageBitmap(place.getBitmap_image_url__shop_image1());
            }





        return convertView;
    }

}