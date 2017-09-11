package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

/**
 * Created by ashishc on 14-06-2016.
 */
public class ChecklistDataGetterSetters {

String RefId;
    String checklist_id;
    String Status;

    public ArrayList<String> getStatus_ID() {
        return Status_ID;
    }

    public void setStatus_ID(String status_ID) {
        Status_ID.add(status_ID);
    }

    ArrayList<String> Status_ID = new ArrayList<String>();





    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getChecklist_id() {
        return checklist_id;
    }

    public void setChecklist_id(String checklist_id) {
        this.checklist_id = checklist_id;
    }

    public String getRefId() {
        return RefId;
    }

    public void setRefId(String refId) {
        RefId = refId;
    }





}
