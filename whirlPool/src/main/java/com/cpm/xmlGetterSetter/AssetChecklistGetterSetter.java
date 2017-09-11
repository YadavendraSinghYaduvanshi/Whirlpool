package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

/**
 * Created by yadavendras on 11-03-2016.
 */
public class AssetChecklistGetterSetter {

    String assetchecklist_insert_table;

    ArrayList<String> CHECKLIST_ID = new ArrayList<String>();
    ArrayList<String> CHECKLIST = new ArrayList<String>();
    ArrayList<String> TYPE = new ArrayList<String>();


    public ArrayList<String> getCHECKLIST_ID() {
        return CHECKLIST_ID;
    }

    public void setCHECKLIST_ID(String CHECKLIST_ID) {
        this.CHECKLIST_ID.add(CHECKLIST_ID);
    }

    public ArrayList<String> getCHECKLIST() {
        return CHECKLIST;
    }

    public void setCHECKLIST(String CHECKLIST) {
        this.CHECKLIST.add(CHECKLIST);
    }

    public ArrayList<String> getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE.add(TYPE);
    }

    public String getAssetchecklist_insert_table() {
        return assetchecklist_insert_table;
    }

    public void setAssetchecklist_insert_table(String assetchecklist_insert_table) {
        this.assetchecklist_insert_table = assetchecklist_insert_table;
    }
}
