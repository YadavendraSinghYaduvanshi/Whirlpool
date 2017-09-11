package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

/**
 * Created by yadavendras on 14-03-2016.
 */
public class MappingAssetChecklistGetterSetter {

    ArrayList<String> asset_cd = new ArrayList<>();
    ArrayList<String> check_list_id = new ArrayList<>();

    String mapping_asset_checklist_table;

    public ArrayList<String> getAsset_cd() {
        return asset_cd;
    }

    public void setAsset_cd(String asset_cd) {
        this.asset_cd.add(asset_cd);
    }

    public ArrayList<String> getCheck_list_id() {
        return check_list_id;
    }

    public void setCheck_list_id(String check_list_id) {
        this.check_list_id.add(check_list_id);
    }

    public String getMapping_asset_checklist_table() {
        return mapping_asset_checklist_table;
    }

    public void setMapping_asset_checklist_table(String mapping_asset_checklist_table) {
        this.mapping_asset_checklist_table = mapping_asset_checklist_table;
    }
}
