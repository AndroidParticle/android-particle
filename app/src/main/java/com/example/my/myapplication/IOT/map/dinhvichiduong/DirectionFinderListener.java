package com.example.my.myapplication.IOT.map.dinhvichiduong;

import java.util.List;

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
