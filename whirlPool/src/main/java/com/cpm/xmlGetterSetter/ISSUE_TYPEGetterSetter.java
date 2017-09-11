package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

/**
 * Created by ashishc on 14-07-2016.
 */
public class ISSUE_TYPEGetterSetter {

    public String getISSUETYPE_CD() {
        return ISSUETYPE_CD;
    }

    public void setISSUETYPE_CD(String ISSUETYPE_CD) {
        this.ISSUETYPE_CD = ISSUETYPE_CD;
    }

    String ISSUETYPE_CD;

    public String getKey_ID() {
        return Key_ID;
    }

    public void setKey_ID(String key_ID) {
        Key_ID = key_ID;
    }

    String Key_ID;

    public String getISSUE_TYPE() {
        return ISSUE_TYPE;
    }

    public void setISSUE_TYPE(String ISSUE_TYPE) {
        this.ISSUE_TYPE = ISSUE_TYPE;
    }

    String ISSUE_TYPE;



    public String getIssuedetail() {
        return Issuedetail;
    }

    public void setIssuedetail(String issuedetail) {
        Issuedetail = issuedetail;
    }

    String Issuedetail;


    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    String Brand;



    String brand_master_table;

    public String getBrand_cd() {
        return brand_cd;
    }

    public void setBrand_cd(String brand_cd) {
        this.brand_cd = brand_cd;
    }

    String brand_cd;
   // ArrayList<String> brand=new ArrayList<String>();
    ArrayList<String> brand_sequence=new ArrayList<String>();

    ArrayList<String> company_cd=new ArrayList<String>();
    ArrayList<String> category_cd=new ArrayList<String>();

    public String getBrand_master_table() {
        return brand_master_table;
    }

    public void setBrand_master_table(String brand_master_table) {
        this.brand_master_table = brand_master_table;
    }

    public ArrayList<String> getBrand_sequence() {
        return brand_sequence;
    }

    public void setBrand_sequence(String brand_sequence) {
        this.brand_sequence.add(brand_sequence);
    }



    public ArrayList<String> getCompany_cd() {
        return company_cd;
    }

    public void setCompany_cd(String company_cd) {
        this.company_cd.add(company_cd);
    }

    public ArrayList<String> getCategory_cd() {
        return category_cd;
    }

    public void setCategory_cd(String category_cd) {
        this.category_cd.add(category_cd);
    }



}
