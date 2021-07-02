package com.example.easymap;

import java.io.Serializable;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import org.bson.types.ObjectId;

public class AccessPoint extends RealmObject{
    @PrimaryKey private String _id;
    @Required private String SSID;
    @Required private String BSSID;

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
