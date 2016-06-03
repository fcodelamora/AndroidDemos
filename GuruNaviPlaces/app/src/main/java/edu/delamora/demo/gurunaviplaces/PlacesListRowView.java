package edu.delamora.demo.gurunaviplaces;

import android.content.Context;
import android.view.View;

import edu.delamora.demo.gurunaviplaces.models.GuruPlace;

public class PlacesListRowView extends View {

    private GuruPlace place;

    public PlacesListRowView(Context context, GuruPlace rcvPlace) {
        super(context);
//        this.place = rcvPlace;
//        init();
    }

    public void init(){
    }

    public GuruPlace getPlace() {
        return place;
    }

    public void setPlace(GuruPlace place) {
        this.place = place;
    }
}
