package com.example.my.myapplication.IOT.server.models;

/**
 * Created by MY on 6/20/2017.
 */
public class EnviromentCurrent {
    private int pa;//áp suất
    private int height;//độ cao so với mặt nước biển
    private int la;//cuong do anh san analog
    private int ld;//cuong do anh san digital
    private int h;//do am
    private int t;//độ c
    private int f;//độ f
    private int k;//độ K
    private int hi;//heat index
    private int dp;//điểm sương

    public EnviromentCurrent() {

    }

    public int getPa() {
        return pa;
    }

    public int getHeight() {
        return height;
    }

    public int getLa() {
        return la;
    }

    public int getLd() {
        return ld;
    }

    public int getH() {
        return h;
    }

    public int getT() {
        return t;
    }

    public int getF() {
        return f;
    }

    public int getK() {
        return k;
    }

    public int getHi() {
        return hi;
    }

    public int getDp() {
        return dp;
    }
}
