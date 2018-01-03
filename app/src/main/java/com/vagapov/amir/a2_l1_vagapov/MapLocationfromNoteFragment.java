package com.vagapov.amir.a2_l1_vagapov;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapLocationfromNoteFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    GoogleMap map;
    ArrayList<Note> notes;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);


        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(map != null) {
            map.clear();
        }
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMinZoomPreference(4);
        notes = (ArrayList<Note>) NoteList.getNoteList(getContext()).getNotes();


        Geocoder geocoder = new Geocoder(getContext());
        ArrayList<Address> addresses;
        try {
            for (Note note : notes) {
                addresses = (ArrayList<Address>) geocoder.getFromLocationName(note.getAddress(), 1);
                if (!note.getAddress().equals("")) {
                    map.addMarker(new MarkerOptions().position(new LatLng(addresses.get(0).getLatitude(),
                            addresses.get(0).getLongitude())).title(note.getTitle()));
                    map.moveCamera(CameraUpdateFactory.newLatLng
                            (new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude())));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
