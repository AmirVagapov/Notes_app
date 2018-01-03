package com.vagapov.amir.a2_l1_vagapov;


import android.location.Location;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.UUID;

public class Note implements Serializable {

    private String title;
    private String description;
    private String address;
    private UUID mUUID;
    private Location mLocation;

    private Note() {
        this(UUID.randomUUID());
    }
    private Note(UUID uuid){
        mUUID = uuid;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    @NonNull
    static Note createNote(UUID id){
        return new Note(id);
    }

    @NonNull
    static Note createNote(){
        return new Note();
    }
}
