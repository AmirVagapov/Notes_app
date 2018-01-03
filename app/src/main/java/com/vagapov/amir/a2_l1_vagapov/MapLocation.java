package com.vagapov.amir.a2_l1_vagapov;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.List;

public class MapLocation extends AsyncTask<Activity, Void, String>{

    private static final int REQUEST_PERMISSIONS = 0;
    private static Location mLocation;
    private static final String GET_LOCATION_FAILED = "Ваше местоположение не определено";
    /*private final Context context;
    private final Activity activity;


    MapLocation(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }*/

    private String getLocation(Activity activity) {

        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity.getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS);
            getLocation(activity);
        }
        LocationManager locationManager = (LocationManager) activity.getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            return GET_LOCATION_FAILED ;
        }
            mLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        return getAddress(activity);
    }

    private  String getAddress(Activity activity) {
        final Geocoder geocoder = new Geocoder(activity.getApplicationContext());
        List<Address> addressList;

        try {
            if (geocoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(),
                    1) == null) {
                return GET_LOCATION_FAILED;
            }
            addressList = geocoder.getFromLocation(mLocation.getLatitude(),
                    mLocation.getLongitude(), 1);
        } catch (Exception e) {
            e.printStackTrace();
            return GET_LOCATION_FAILED ;
        }
        if (addressList.isEmpty()) return GET_LOCATION_FAILED;
        Address address = addressList.get(0);

        StringBuilder builder = new StringBuilder();
        final String sep = ", ";
        builder.append(address.getCountryName()).append(sep).append(address.getAdminArea())
                .append(sep).append(address.getThoroughfare()).append(sep)
                .append(address.getSubThoroughfare());
        return builder.toString();
    }

    @Override
    protected void onPreExecute() {
        EditNoteFragment.progressBarSetVisible(true);
    }

    @Override
    protected String doInBackground(Activity... activities) {
        Activity activity = null;
        for (Activity activityArray: activities) {
            activity = activityArray;
        }
        if(activity == null){
            return GET_LOCATION_FAILED;
        }else {
            return getLocation(activity);
        }
    }


    @Override
    protected void onPostExecute(String s) {
        EditNoteFragment.progressBarSetVisible(false);
        try {
            if (s.equals(MapLocation.GET_LOCATION_FAILED) || (s.equals(""))) {
                //Toast.makeText(context, MapLocation.GET_LOCATION_FAILED, Toast.LENGTH_SHORT).show();
                EditNoteFragment.setAddressEditText(s);
            } else {
                EditNoteFragment.setAddressEditText(s);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //Toast.makeText(context, MapLocation.GET_LOCATION_FAILED, Toast.LENGTH_SHORT).show();
            EditNoteFragment.progressBarSetVisible(false);
        }
    }

}
