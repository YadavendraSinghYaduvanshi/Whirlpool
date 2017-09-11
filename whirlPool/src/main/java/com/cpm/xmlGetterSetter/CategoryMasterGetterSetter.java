package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class CategoryMasterGetterSetter {
	
	public String getCategory_master_table() {
		return category_master_table;
	}
	public void setCategory_master_table(String category_master_table) {
		this.category_master_table = category_master_table;
	}
	public ArrayList<String> getCategory_cd() {
		return category_cd;
	}
	public void setCategory_cd(String category_cd) {
		this.category_cd.add(category_cd);
	}
	public ArrayList<String> getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category.add(category);
	}
	String category_master_table;
	
	ArrayList<String> category_cd=new ArrayList<>();
	ArrayList<String> category=new ArrayList<>();

	public ArrayList<String> getCATEGORY_SEQUENCE() {
		return CATEGORY_SEQUENCE;
	}

	public void setCATEGORY_SEQUENCE(String CATEGORY_SEQUENCE) {
		this.CATEGORY_SEQUENCE.add(CATEGORY_SEQUENCE);
	}

	ArrayList<String> CATEGORY_SEQUENCE=new ArrayList<>();




}
