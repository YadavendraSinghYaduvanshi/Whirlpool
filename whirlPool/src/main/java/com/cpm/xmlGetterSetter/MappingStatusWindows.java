package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

/**
 * Created by ashishc on 06-06-2016.
 */
public class MappingStatusWindows {



    ArrayList<String> STATUS_CD = new ArrayList<String>();
    ArrayList<String> STATUS = new ArrayList<String>();

    public ArrayList<String> getSTATUS_CD() {
        return STATUS_CD;
    }

    public void setSTATUS_CD(String STATUS_CD) {
        this.STATUS_CD.add(STATUS_CD);
    }

    public ArrayList<String> getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS.add(STATUS);
    }



    public String getMapping_status_windows() {
        return mapping_status_windows;
    }

    public void setMapping_status_windows(String mapping_status_windows) {
        this.mapping_status_windows = mapping_status_windows;
    }

    String mapping_status_windows;


}
