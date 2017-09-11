package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

/**
 * Created by ashishc on 06-03-2017.
 */

public class POSM_MASTERGetterSetter {

    String POSM_MASTER_table;
    ArrayList<String> POSM_CD = new ArrayList<String>();
    ArrayList<String> POSM = new ArrayList<String>();
    ArrayList<String> PCATEGORY_CD = new ArrayList<String>();
    ArrayList<String> POSM_CATEGORY = new ArrayList<String>();
    ArrayList<String> PSUB_CATEGORY_CD = new ArrayList<String>();
    ArrayList<String> PSUB_CATEGORY = new ArrayList<String>();


    public String getPOSM_MASTER_table() {
        return POSM_MASTER_table;
    }

    public void setPOSM_MASTER_table(String POSM_MASTER_table) {
        this.POSM_MASTER_table = POSM_MASTER_table;
    }

    public ArrayList<String> getPOSM() {
        return POSM;
    }

    public void setPOSM(String POSM) {
        this.POSM.add(POSM);
    }

    public ArrayList<String> getPOSM_CD() {
        return POSM_CD;
    }

    public void setPOSM_CD(String POSM_CD) {
        this.POSM_CD.add(POSM_CD);
    }

    public ArrayList<String> getPCATEGORY_CD() {
        return PCATEGORY_CD;
    }

    public void setPCATEGORY_CD(String PCATEGORY_CD) {
        this.PCATEGORY_CD.add(PCATEGORY_CD);
    }

    public ArrayList<String> getPOSM_CATEGORY() {
        return POSM_CATEGORY;
    }

    public void setPOSM_CATEGORY(String POSM_CATEGORY) {
        this.POSM_CATEGORY.add(POSM_CATEGORY);
    }

    public ArrayList<String> getPSUB_CATEGORY_CD() {
        return PSUB_CATEGORY_CD;
    }

    public void setPSUB_CATEGORY_CD(String PSUB_CATEGORY_CD) {
        this.PSUB_CATEGORY_CD.add(PSUB_CATEGORY_CD);
    }

    public ArrayList<String> getPSUB_CATEGORY() {
        return PSUB_CATEGORY;
    }

    public void setPSUB_CATEGORY(String PSUB_CATEGORY) {
        this.PSUB_CATEGORY.add(PSUB_CATEGORY);
    }

    String quantity = "";
    String image = "";

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
