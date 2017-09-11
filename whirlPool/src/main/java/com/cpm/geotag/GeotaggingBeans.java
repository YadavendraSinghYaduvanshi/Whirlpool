package com.cpm.geotag;

public class GeotaggingBeans {
    public String storeid;

    public String getGEO_TAG() {
        return GEO_TAG;
    }

    public void setGEO_TAG(String GEO_TAG) {
        this.GEO_TAG = GEO_TAG;
    }

    public String GEO_TAG;
    public String url1="";
    public double Latitude;
    public double Longitude;

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double d) {
        Latitude = d;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double d) {
        Longitude = d;
    }

    public void setStoreId(String storeid) {

        this.storeid = storeid;
    }

    public String getStoreId() {

        return storeid;
    }

    public void setUrl1(String url1) {

        this.url1 = url1;
    }

    public String getUrl1() {
        return url1;
    }

}
