package com.example.my.myapplication.IOT.server;

import com.example.my.myapplication.IOT.server.models.Device;

import java.util.ArrayList;

public class ConfigApp {
    /*private static final String HOST = "192.168.56.1";
    private static final String PORT = "3000";*/
    private static final String HOST = "cryptic-sea-66379.herokuapp.com";
    private static final String PORT = "";
    private static final String NAME_PROJECT = "api";

    private static final String FULLNAME = "FULLNAME";
    private static final String EMAIL = "EMAIL";
    private static final String ID_ACCOUNT = "ID_ACCOUNT";
    private static final String ACCOUNT = "ACCOUNT";
    private static final String LISTDEVICE = "listDevice";
    private static final String LISTTYPE = "listType";

    private static final String IDModelDevice = "IDModelDevice";
    private static final String DEVICEID = "DEVICEID";
    private static final String LISTPLACE = "LISTPLACE";
    private static final String DEVICEINFO = "DEVICEINFO";
    private static final String ACCOUNTINFO = "ACCOUNTINFO";

    private static final String LISTTHINGSPEAK = "LISTTHINGSPEAK";


    public static String getHost() {
        return HOST;
    }

    public static String getPort() {
        return PORT;
    }

    public static String getNameProject() {
        return NAME_PROJECT;
    }

    public static String getFULLNAME() {
        return FULLNAME;
    }

    public static String getEMAIL() {
        return EMAIL;
    }

    public static String getIdAccount() {
        return ID_ACCOUNT;
    }

    public static String getACCOUNT() {
        return ACCOUNT;
    }

    public static String getListDevice() {
        return LISTDEVICE;
    }

    public static String getDEVICEID() {
        return DEVICEID;
    }

    public static String getIDModelDevice() {
        return IDModelDevice;
    }

    public static String getLISTTYPE() {
        return LISTTYPE;
    }

    public static String getLISTPLACE() {
        return LISTPLACE;
    }

    public static String getDEVICEINFO() {
        return DEVICEINFO;
    }

    public static String getACCOUNTINFO() {
        return ACCOUNTINFO;
    }

    public static String getLISTDEVICE() {
        return LISTDEVICE;
    }

    public static String getLISTTHINGSPEAK() {
        return LISTTHINGSPEAK;
    }

    public static final String VITRIGPS = "VITRIGPS";

    public static String getVITRIGPS() {
        return VITRIGPS;
    }
}
