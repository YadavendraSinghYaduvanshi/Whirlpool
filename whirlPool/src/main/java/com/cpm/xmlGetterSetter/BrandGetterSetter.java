package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

/**
 * Created by yadavendras on 29-01-2016.
 */
public class BrandGetterSetter {

    public ArrayList<String> getBrand_cd() {
        return brand_cd;
    }

    public void setBrand_cd(String brand_cd) {
        this.brand_cd.add(brand_cd);
    }

    public ArrayList<String> getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.add(brand);
    }

    String brand_master_table;

    ArrayList<String> brand_cd=new ArrayList<String>();
    ArrayList<String> brand=new ArrayList<String>();
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

    public void setBrand_cd(ArrayList<String> brand_cd) {
        this.brand_cd = brand_cd;
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
