package com.example.my.myapplication.IOT.server.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by MY on 6/11/2017.
 */
public class ListPlace {

    public ArrayList<PlaceInfo> arraylistAdd;
    public boolean checkPlace;

    public ListPlace() {
        arraylistAdd = new ArrayList<PlaceInfo>();
    }

    public ListPlace(ArrayList<PlaceInfo> arraylistAdd) {
        this.arraylistAdd = arraylistAdd;
    }

    public boolean addPlace(PlaceInfo worldPopulation) {
        Log.i("size", "================" + arraylistAdd.size() + worldPopulation.getDeviceInfo().getNameDevice());
        checkPlace = false;
        if (arraylistAdd.size() == 0) {
            arraylistAdd.add(worldPopulation);
            return true;
        } else {
            for (PlaceInfo wp : arraylistAdd) {
                if (wp.getDeviceInfo().getNameDevice().toLowerCase(Locale.getDefault())
                        .equalsIgnoreCase(worldPopulation.getDeviceInfo().getNameDevice())) {
                    checkPlace = true;
                    break;
                }
            }
            if (!checkPlace) {
                arraylistAdd.add(worldPopulation);
                return true;
            } else return false;
        }

    }

    public boolean removePlace(int index) {
        if (arraylistAdd == null) {
            return false;
        } else if (arraylistAdd.size() == 0) {
            return false;
        } else {
            arraylistAdd.remove(index);
            return true;
        }
    }

    public ArrayList<PlaceInfo> getArraylist() {
        return arraylistAdd;
    }
}
