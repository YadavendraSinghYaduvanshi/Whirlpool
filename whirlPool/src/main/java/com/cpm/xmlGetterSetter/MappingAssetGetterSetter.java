package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class MappingAssetGetterSetter {
	
	String mapping_asset_table;

	public String getWindows_cd() {
		return Windows_cd;
	}

	public void setWindows_cd(String windows_cd) {
		Windows_cd = windows_cd;
	}

	String Windows_cd;

	String Statuschecklist;

	String ChecklistWindows_cd;
	String ChecklistCHECKLIST_ID;
	String ChecklistCHECKLIST;

	public String getChecklistWindows_cd() {
		return ChecklistWindows_cd;
	}

	public void setChecklistWindows_cd(String checklistWindows_cd) {
		ChecklistWindows_cd = checklistWindows_cd;
	}

	public String getChecklistCHECKLIST_ID() {
		return ChecklistCHECKLIST_ID;
	}

	public void setChecklistCHECKLIST_ID(String checklistCHECKLIST_ID) {
		ChecklistCHECKLIST_ID = checklistCHECKLIST_ID;
	}

	public String getChecklistCHECKLIST() {
		return ChecklistCHECKLIST;
	}

	public void setChecklistCHECKLIST(String checklistCHECKLIST) {
		ChecklistCHECKLIST = checklistCHECKLIST;
	}

	public String getStatuschecklist() {
		return Statuschecklist;
	}

	public void setStatuschecklist(String statuschecklist) {
		Statuschecklist = statuschecklist;
	}





	public String getRefId() {
		return RefId;
	}

	public void setRefId(String refId) {
		RefId = refId;
	}

	public String getId_checklist() {
		return Id_checklist;
	}

	public void setId_checklist(String id_checklist) {
		Id_checklist = id_checklist;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	String RefId;
	String Id_checklist;
	String Status;

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}

	String Reason;

	public String getMapping_asset_table() {
		return mapping_asset_table;
	}


	public void setMapping_asset_table(String mapping_asset_table) {
		this.mapping_asset_table = mapping_asset_table;
	}


	ArrayList<String> store_cd = new ArrayList<String>();
	ArrayList<String> category_cd = new ArrayList<String>();
	ArrayList<String> asset_cd = new ArrayList<String>();




	ArrayList<String> CHECKLIST_ID = new ArrayList<String>();
	ArrayList<String> CHECKLIST = new ArrayList<String>();


	public ArrayList<String> getCHECKLIST() {
		return CHECKLIST;
	}

	public void setCHECKLIST(String CHECKLIST) {
		this.CHECKLIST.add(CHECKLIST);
	}

	public ArrayList<String> getCHECKLIST_ID() {
		return CHECKLIST_ID;
	}

	public void setCHECKLIST_ID(String CHECKLIST_ID) {
		this.CHECKLIST_ID.add(CHECKLIST_ID);
	}










	public ArrayList<String> getStore_cd() {
		return store_cd;
	}

	public void setStore_cd(String store_cd) {
		this.store_cd.add(store_cd);
	}



	
	public ArrayList<String> getAsset_cd() {
		return asset_cd;
	}


	public void setAsset_cd(String asset_cd) {
		this.asset_cd.add(asset_cd);
	}


	public ArrayList<String> getCategory_cd() {
		return category_cd;
	}

	public void setCategory_cd(String category_cd) {
		this.category_cd.add(category_cd);
	}
}
