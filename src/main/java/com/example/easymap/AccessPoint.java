package com.example.easymap;

import java.io.Serializable;

public class AccessPoint implements Serializable{
    private String _id;
    private String SSID;
    private String BSSID;

    public AccessPoint() {}

    public AccessPoint(String SSID, String BSSID) {
        this.SSID = SSID;
        this.BSSID = BSSID;
    }

    public String getSSID() {
        return SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setSSID(String SSID){this.SSID = SSID;}

    public void setBSSID(String BSSID){this.BSSID = BSSID;}

    // Return SSID
    @Override
    public String toString(){
        return SSID;
    }

    // See if the AP is the same or not
    @Override
    public boolean equals(Object arg){
        //Compare if two AP is the same
        return ((AccessPoint) arg).getBSSID().equals(this.getBSSID());
    }

    // Get hashCode
    @Override
    public int hashCode(){
        //Get Hash ID
        return  this.BSSID.hashCode();
    }
}
