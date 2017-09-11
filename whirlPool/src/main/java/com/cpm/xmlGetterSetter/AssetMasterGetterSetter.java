package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class AssetMasterGetterSetter {
	
	String asset_master_table;
	
	ArrayList<String> asset_cd=new ArrayList<String>();
	ArrayList<String> asset=new ArrayList<String>();


	ArrayList<String> STATE_CD=new ArrayList<String>();

	public ArrayList<String> getSTORETYPE_CD() {
		return STORETYPE_CD;
	}

	public void setSTORETYPE_CD(String STORETYPE_CD) {
		this.STORETYPE_CD.add(STORETYPE_CD);
	}

	public ArrayList<String> getWINDOW_CD() {
		return WINDOW_CD;
	}

	public void setWINDOW_CD(String WINDOW_CD) {
		this.WINDOW_CD.add(WINDOW_CD);
	}

	public ArrayList<String> getSTATE_CD() {
		return STATE_CD;
	}

	public void setSTATE_CD(String STATE_CD) {
		this.STATE_CD.add(STATE_CD);
	}

	ArrayList<String> STORETYPE_CD=new ArrayList<String>();
	ArrayList<String> WINDOW_CD=new ArrayList<String>();



	public ArrayList<String> getAsset_cd() {
		return asset_cd;
	}

	public void setAsset_cd(String asset_cd) {
		this.asset_cd.add(asset_cd);
	}

	public ArrayList<String> getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset.add(asset);
	}
	
	public String getAsset_master_table() {
		return asset_master_table;
	}

	public void setAsset_master_table(String asset_master_table) {
		this.asset_master_table = asset_master_table;
	}

}
