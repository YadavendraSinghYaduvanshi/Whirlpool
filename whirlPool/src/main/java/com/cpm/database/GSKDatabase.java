package com.cpm.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cpm.Constants.CommonString;
import com.cpm.Constants.CommonString1;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.TableBean;
import com.cpm.geotag.GeotaggingBeans;
import com.cpm.xmlGetterSetter.AssetInsertdataGetterSetter;
import com.cpm.xmlGetterSetter.CallsGetterSetter;
import com.cpm.xmlGetterSetter.ChecklistInsertDataGetterSetter;
import com.cpm.xmlGetterSetter.CompetitionPromotionGetterSetter;
import com.cpm.xmlGetterSetter.DeepFreezerTypeGetterSetter;
import com.cpm.xmlGetterSetter.DeploymentFormGetterSetter;
import com.cpm.xmlGetterSetter.DeploymentXmlGetterSetter;
import com.cpm.xmlGetterSetter.FacingCompetitorGetterSetter;
import com.cpm.xmlGetterSetter.FoodStoreInsertDataGetterSetter;
import com.cpm.xmlGetterSetter.HeaderGetterSetter;
import com.cpm.xmlGetterSetter.JourneyPlanGetterSetter;
import com.cpm.xmlGetterSetter.MappingAssetChecklistGetterSetter;
import com.cpm.xmlGetterSetter.MappingAssetGetterSetter;
import com.cpm.xmlGetterSetter.MappingStatusWindows;
import com.cpm.xmlGetterSetter.NonWorkingReasonGetterSetter;
import com.cpm.xmlGetterSetter.POSM_MASTERGetterSetter;
import com.cpm.xmlGetterSetter.POSM_MASTER_DataGetterSetter;
import com.cpm.xmlGetterSetter.PerformanceGetterSetter;
import com.cpm.xmlGetterSetter.STOREFIRSTIMEGetterSetter;
import com.cpm.xmlGetterSetter.StockGetterSetter;
import com.cpm.xmlGetterSetter.StockNewGetterSetter;
import com.cpm.xmlGetterSetter.windowsChildData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GSKDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "WHIRLPOOL_DATABASE_4";
    public static final int DATABASE_VERSION = 5;
    private SQLiteDatabase db;

    public GSKDatabase(Context completeDownloadActivity) {
        super(completeDownloadActivity, DATABASE_NAME, null, DATABASE_VERSION);
    }// TODO Auto-generated constructor stub }

    public void open() {
        try {
            db = this.getWritableDatabase();
        } catch (Exception e) {
        }
    }

    public void close() {
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(TableBean.getjcptable());
        db.execSQL(TableBean.getNonworkingtable());
        db.execSQL(TableBean.getPosm_master_table_Bean());
        db.execSQL(TableBean.getDeloymentTable());
        db.execSQL(CommonString1.CREATE_TABLE_DEEPFREEZER_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_OPENING_STOCK_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_CLOSING_STOCK_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_MIDDAY_STOCK_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_insert_OPENINGHEADER_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_insert_OPENINGHEADER_CLOSING_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_insert_HEADER_MIDDAY_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_insert_HEADER_PROMOTION_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_PROMOTION_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_insert_HEADER_ASSET_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_ASSET_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_FACING_COMPETITOR_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_FOOD_STORE_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_insert_HEADER_FOOD_STORE_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_COVERAGE_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_STOCK_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_CALLS_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_COMPETITION_POI);
        db.execSQL(CommonString1.CREATE_TABLE_STORE_FIRST_TIME);
        db.execSQL(CommonString1.CREATE_TABLE_COMPETITION_PROMOTION);
        db.execSQL(CommonString1.CREATE_TABLE_STOCK_IMAGE);
        db.execSQL(CommonString1.CREATE_TABLE_INSERT_POSM_DATA);

        db.execSQL(CommonString1.CREATE_TABLE_ASSET_CHECKLIST_INSERT);


        db.execSQL(CommonString1.CREATE_TABLE_CHECK_LIST_DATA);

        //Gagan Goel
        db.execSQL(CommonString1.CREATE_TABLE_INSERT_STORE_CAMERA);
        db.execSQL(CommonString1.CREATE_TABLE_INSERT_POSM_TRACKING_DATA);
        db.execSQL(CommonString1.CREATE_TABLE_INSERT_DEPLOYMENT_FORM_DATA);
        db.execSQL(CommonString.CREATE_TABLE_STORE_GEOTAGGING);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        onCreate(db);
    }

    public void deleteSpecificStoreData(String storeid) {
        db.delete(CommonString1.TABLE_COVERAGE_DATA, CommonString1.KEY_STORE_ID + "='" + storeid + "'", null);
        db.delete(CommonString.TABLE_STORE_GEOTAGGING, "STORE_ID='" + storeid + "'", null);
        //Gagan
        db.delete(CommonString1.TABLE_INSERT_STORE_CAMERA, "STORE_CD ='" + storeid + "'", null);
        db.delete(CommonString1.TABLE_INSERT_POSM_TRACKING_DATA, "STORE_CD ='" + storeid + "'", null);
//Jeevan
        db.delete(CommonString1.TABLE_DEPLOYMENT_FORM, "STORE_CD ='" + storeid + "'", null);
    }

    public void deleteAllTables() {
        // DELETING TABLES GTGSK
        db.delete(CommonString1.TABLE_COVERAGE_DATA, null, null);
        db.delete(CommonString1.TABLE_DEPLOYMENT_FORM, null, null);
        //Gagan
        db.delete(CommonString1.TABLE_INSERT_STORE_CAMERA, null, null);
        db.delete(CommonString1.TABLE_INSERT_POSM_TRACKING_DATA, null, null);
        db.delete(CommonString.TABLE_STORE_GEOTAGGING, null, null);
    }

    public void deletePreviousUploadedData(String visit_date) {
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  * from COVERAGE_DATA where VISIT_DATE < '" + visit_date + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getCount();
                dbcursor.close();
                if (icount > 0) {
                    db.delete(CommonString1.TABLE_COVERAGE_DATA, null, null);
                    db.delete(CommonString1.TABLE_DEPLOYMENT_FORM, null, null);
                    db.delete(CommonString1.TABLE_INSERT_STORE_CAMERA, null, null);
                    db.delete(CommonString1.TABLE_INSERT_POSM_TRACKING_DATA, null, null);
                    db.delete(CommonString.TABLE_STORE_GEOTAGGING, null, null);
                }
                dbcursor.close();
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Coverage Data!!!!!!!!!!!!!!!!!!!!!", e.toString());

        }
    }


    public void deleteStockHeaderData() {
        db.delete("openingHeader_data", null, null);
    }

    //JCP data
    public void insertJCPData(JourneyPlanGetterSetter data) {
        db.delete("JOURNEY_PLAN", null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getStore_cd().size(); i++) {
                values.put("STORE_CD", Integer.parseInt(data.getStore_cd().get(i)));
                values.put("EMP_CD", Integer.parseInt(data.getEmp_cd().get(i)));
                values.put("VISIT_DATE", data.getVISIT_DATE().get(i));
                values.put("KEYACCOUNT", data.getKey_account().get(i));
                values.put("STORENAME", data.getStore_name().get(i));
                values.put("CITY", data.getCity().get(i));
                values.put("STORETYPE", data.getStore_type().get(i));
                values.put("STATE_CD", data.getSTATE_CD().get(i));
                values.put("UPLOAD_STATUS", data.getUploadStatus().get(i));
                values.put("CHECKOUT_STATUS", data.getCheckOutStatus().get(i));
                values.put("STORETYPE_CD", data.getSTORETYPE_CD().get(i));
                values.put("GEO_TAG", data.getGEO_TAG().get(i));
                db.insert("JOURNEY_PLAN", null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert JCP Data ",
                    ex.toString());
        }

    }

    public ArrayList<MappingStatusWindows> getStatuswindows() {

        //Log.d("FetchingStoredata--------------->Start<------------", "------------------");
        ArrayList<MappingStatusWindows> list1 = new ArrayList<MappingStatusWindows>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  distinct * from WINDOW_STATUS ORDER BY STATUS_CD", null);

            if (dbcursor != null) {


                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingStatusWindows crowndata = new MappingStatusWindows();
                    crowndata.setSTATUS_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STATUS_CD")));
                    crowndata.setSTATUS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STATUS")));
                    list1.add(crowndata);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list1;
            }

        } catch (Exception e) {
            return list1;
        }
        //Log.d("FetchingStoredat---------------------->Stop<-----------", "-------------------");
        return list1;

    }

    public ArrayList<MappingAssetGetterSetter> getCheckList() {
        Log.d("Fetching checklist data--------------->Start<------------",
                "------------------");
        ArrayList<MappingAssetGetterSetter> list = new ArrayList<MappingAssetGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT CHECKLIST, CHECKLIST_ID FROM CHECKLIST_MASTER  ", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingAssetGetterSetter sb = new MappingAssetGetterSetter();


                    sb.setCHECKLIST(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKLIST")));

                    sb.setCHECKLIST_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKLIST_ID")));

                    sb.setStatus("0");

                    sb.setWindows_cd("");


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching checklist data!!!!!!!!!!!", e.toString());
            return list;
        }

        Log.d("Fetching checklist data---------------------->Stop<-----------", "-------------------");
        return list;
    }

    public ArrayList<POSM_MASTERGetterSetter> getPosmList() {
        ArrayList<POSM_MASTERGetterSetter> list = new ArrayList<POSM_MASTERGetterSetter>();
        Cursor dbcursor = null;
        try {
            dbcursor = db
                    .rawQuery("Select POSM_CD,POSM from POSM_MASTER order by POSM", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    POSM_MASTERGetterSetter sb = new POSM_MASTERGetterSetter();

                    sb.setPOSM_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("POSM_CD")));

                    sb.setPOSM(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("POSM")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching getPosmList data!!!!!!!!!!!", e.toString());
            return list;
        }

        Log.d("Fetching getPosmList data---------------------->Stop<-----------", "-------------------");
        return list;
    }

    //Non Working data
    public void insertNonWorkingReasonData(NonWorkingReasonGetterSetter data) {

        db.delete("NON_WORKING_REASON", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getReason_cd().size(); i++) {

                values.put("REASON_CD", Integer.parseInt(data.getReason_cd().get(i)));
                values.put("REASON", data.getReason().get(i));
                values.put("ENTRY_ALLOW", data.getEntry_allow().get(i));
                values.put("IMAGE_ALLOW", data.getIMAGE_ALLOW().get(i));
                values.put("SUB_REASON", data.getSUB_REASON().get(i));
                values.put("INFORMED_TO", data.getINFORMED_TO().get(i));

                db.insert("NON_WORKING_REASON", null, values);

            }

        } catch (Exception ex) {

        }

    }

    //POSM_MASTER data
    public void insertPOSM_MASTERData(POSM_MASTERGetterSetter data) {

        db.delete("POSM_MASTER", null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getPOSM_CD().size(); i++) {
                values.put("POSM_CD", Integer.parseInt(data.getPOSM_CD().get(i)));
                values.put("POSM", data.getPOSM().get(i));
                values.put("PCATEGORY_CD", data.getPCATEGORY_CD().get(i));
                values.put("POSM_CATEGORY", data.getPOSM_CATEGORY().get(i));
                values.put("PSUB_CATEGORY_CD", data.getPSUB_CATEGORY_CD().get(i));
                values.put("PSUB_CATEGORY", data.getPSUB_CATEGORY().get(i));

                db.insert("POSM_MASTER", null, values);
            }
        } catch (Exception ex) {

        }
    }

    public long insertPOSMData(ArrayList<POSM_MASTERGetterSetter> data, String store_cd) {

        long id = 0;
        db.delete("INSERT_POSM_DATA", null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.size(); i++) {

                values.put("STORE_CD", store_cd);
                values.put("POSM_CD", Integer.parseInt(data.get(i).getPOSM_CD().get(0)));
                values.put("POSM", (data.get(i).getPOSM().get(0)));
                values.put("QUANTITY", (data.get(i).getQuantity()));
                values.put("IMAGE", (data.get(i).getImage()));

                id = db.insert("INSERT_POSM_DATA", null, values);
            }

        } catch (Exception ex) {

        }
        return id;

    }

    //get JCP Data
    public ArrayList<JourneyPlanGetterSetter> getJCPData(String date) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<JourneyPlanGetterSetter> list = new ArrayList<JourneyPlanGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from JOURNEY_PLAN where VISIT_DATE = '" + date + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    JourneyPlanGetterSetter sb = new JourneyPlanGetterSetter();

                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_CD")));

                    sb.setKey_account(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("KEYACCOUNT")));

                    sb.setStore_name((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORENAME"))));


                    sb.setCity((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CITY"))));

                    sb.setSTATE_CD((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STATE_CD"))));

                    sb.setSTORETYPE_CD((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORETYPE_CD"))));

                    sb.setUploadStatus((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("UPLOAD_STATUS"))));
                    sb.setCheckOutStatus((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKOUT_STATUS"))));
                    sb.setVISIT_DATE((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("VISIT_DATE"))));

                    sb.setGEO_TAG((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("GEO_TAG"))));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching JCP!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingJCP data---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<POSM_MASTERGetterSetter> getPOSMData(String storecd) {
        ArrayList<POSM_MASTERGetterSetter> list = new ArrayList<POSM_MASTERGetterSetter>();
        POSM_MASTERGetterSetter addLineEntry = null;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("select * from INSERT_POSM_DATA where STORE_CD = '" + storecd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    addLineEntry = new POSM_MASTERGetterSetter();
                    addLineEntry.setPOSM_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM_CD")));
                    addLineEntry.setPOSM(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM")));
                    addLineEntry.setQuantity(dbcursor.getString(dbcursor.getColumnIndexOrThrow("QUANTITY")));
                    addLineEntry.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE")));
                    list.add(addLineEntry);

                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list;
            }

        } catch (Exception e) {
            Log.d("Exception !!", "  getPOSMData" + e.toString());
        }

        Log.d("Fetch Data stop ", " getPOSMData ->Stop<--");
        return null;
    }

    //get JCP Data
    public ArrayList<JourneyPlanGetterSetter> getAllJCPData() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<JourneyPlanGetterSetter> list = new ArrayList<JourneyPlanGetterSetter>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT * from JOURNEY_PLAN "
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    JourneyPlanGetterSetter sb = new JourneyPlanGetterSetter();

                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_CD")));

                    sb.setKey_account(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("KEYACCOUNT")));

                    sb.setStore_name((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORENAME"))));


                    sb.setCity((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CITY"))));


                    sb.setUploadStatus((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("UPLOAD_STATUS"))));
                    sb.setCheckOutStatus((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKOUT_STATUS"))));
                    sb.setVISIT_DATE((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("VISIT_DATE"))));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching JCP!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingJCP data---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    //get DeepFreezerType Data from Master
    public ArrayList<DeepFreezerTypeGetterSetter> getDFMasterData(String dftype) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<DeepFreezerTypeGetterSetter> list = new ArrayList<DeepFreezerTypeGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from DEEPFREEZER_MASTER where FREEZER_TYPE = '" + dftype + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    DeepFreezerTypeGetterSetter df = new DeepFreezerTypeGetterSetter();


                    df.setDeep_freezer(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("DEEP_FREEZER")));
                    df.setFid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("FID")));
                    df.setFreezer_type(dftype);
                    df.setRemark("");
                    df.setStatus("NO");

                    list.add(df);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Deepfreezer!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching Deep Freezer data---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    //Insert Deepfreezer Type Data
    public void insertDeepFreezerTypeData(ArrayList<DeepFreezerTypeGetterSetter> data, String dfType, String store_cd) {

        db.delete(CommonString1.TABLE_DEEPFREEZER_DATA, "FREEZER_TYPE" + "='" + dfType + "'", null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.size(); i++) {

                values.put("FID", Integer.parseInt(data.get(i).getFid()));
                values.put("STORE_CD", store_cd);
                values.put("DEEP_FREEZER", data.get(i).getDeep_freezer());
                values.put("FREEZER_TYPE", data.get(i).getFreezer_type());
                values.put("STATUS", data.get(i).getStatus());
                values.put("REMARK", data.get(i).getRemark());

                db.insert(CommonString1.TABLE_DEEPFREEZER_DATA, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert DeepFreezer Data ",
                    ex.toString());
        }

    }

    //Insert Facing Competitor Data
    public void insertFscingCompetitorData(String store_cd, HashMap<FacingCompetitorGetterSetter,
            List<FacingCompetitorGetterSetter>> data, List<FacingCompetitorGetterSetter> save_listDataHeader) {

        db.delete(CommonString1.TABLE_FACING_COMPETITOR_DATA, "STORE_CD" + "='" + store_cd + "'", null);
        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();
        String category, category_cd;

        try {

            for (int i = 0; i < save_listDataHeader.size(); i++) {

                category = save_listDataHeader.get(i).getCategory();
                category_cd = save_listDataHeader.get(i).getCategory_cd();


                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values.put("CATEGORY_CD", Integer.parseInt(category_cd));
                    values.put("CATEGORY", category);
                    values.put("FACING", data.get(save_listDataHeader.get(i)).get(j).getMccaindf());
                    values.put("BRAND", data.get(save_listDataHeader.get(i)).get(j).getBrand());
                    values.put("BRAND_CD", Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j).getBrand_cd()));
                    //values.put("STORE_DF", data.get(i).getStoredf());
                    values.put("STORE_CD", store_cd);

                    db.insert(CommonString1.TABLE_FACING_COMPETITOR_DATA, null, values);

                }
            }


        } catch (Exception ex) {
            Log.d("Database Exception while Insert Facing Competition Data ",
                    ex.toString());
        }

    }

    //get DeepFreezerType Data
    public ArrayList<DeepFreezerTypeGetterSetter> getDFTypeData(String dftype, String storecd) {

        Log.d("FetchingDeepFreezerType--------------->Start<------------",
                "------------------");
        ArrayList<DeepFreezerTypeGetterSetter> list = new ArrayList<DeepFreezerTypeGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from DEEPFREEZER_DATA where FREEZER_TYPE = '" + dftype + "'  AND STORE_CD = '" + storecd + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    DeepFreezerTypeGetterSetter df = new DeepFreezerTypeGetterSetter();


                    df.setDeep_freezer(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("DEEP_FREEZER")));
                    df.setFid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("FID")));
                    df.setFreezer_type(dftype);
                    df.setRemark(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REMARK")));
                    df.setStatus(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STATUS")));

                    list.add(df);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Deepfreezer!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingJCP data---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    //get Facing Competitor Data
    public ArrayList<FacingCompetitorGetterSetter> getFacingCompetitorData(String store_cd) {

        Log.d("Fetching facing competitor--------------->Start<------------",
                "------------------");
        ArrayList<FacingCompetitorGetterSetter> list = new ArrayList<FacingCompetitorGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from FACING_COMPETITOR_DATA where STORE_CD = '" + store_cd + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    FacingCompetitorGetterSetter fc = new FacingCompetitorGetterSetter();


                    fc.setCategory(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY")));
                    fc.setCategory_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_CD")));
                    fc.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND")));
                    fc.setBrand_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND_CD")));
                    fc.setMccaindf(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("FACING")));


                    list.add(fc);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Facing Competitor!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching facing Competitor---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    //get Facing Competitor Data Categoty wise
    public ArrayList<FacingCompetitorGetterSetter> getFacingCompetitorCategotywiseData(String store_cd, String category_cd) {

        Log.d("Fetching brand>Start<--",
                "--");
        ArrayList<FacingCompetitorGetterSetter> list = new ArrayList<FacingCompetitorGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from FACING_COMPETITOR_DATA where STORE_CD = '" + store_cd + "' AND CATEGORY_CD" + " = '" + category_cd + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    FacingCompetitorGetterSetter fc = new FacingCompetitorGetterSetter();


                    fc.setCategory(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY")));
                    fc.setCategory_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_CD")));
                    fc.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND")));
                    fc.setBrand_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND_CD")));
                    fc.setMccaindf(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("FACING")));
                        /*	fc.setStoredf(dbcursor.getString(dbcursor
                                    .getColumnIndexOrThrow("STORE_DF")));*/


                    list.add(fc);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Facing Competitor!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching facing Competitor---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    //get Facing Competitor Data
    public ArrayList<PerformanceGetterSetter> getPerformrmance(String store_cd) {

        Log.d("Fetching facing competitor--------------->Start<------------",
                "------------------");
        ArrayList<PerformanceGetterSetter> list = new ArrayList<PerformanceGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from MY_PERFORMANCE where STORE_CD = '" + store_cd + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    PerformanceGetterSetter fc = new PerformanceGetterSetter();


                    fc.setMonthly_target(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("TARGET")));
                    fc.setMtd_sales(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SALE")));
                    fc.setAchievement(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ACH")));

                    list.add(fc);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Facing Competitor!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching facing Competitor---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    //check if table is empty

    //Stock data
    public ArrayList<StockNewGetterSetter> getStockAvailabilityData(String STATE_CD, String STORE_TYPE_CD) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT DISTINCT BRAND_CD,BRAND FROM openingHeader_data SD WHERE SD.STATE_CD ='" + STATE_CD + "' AND SD.STORE_TYPE_CD ='" + STORE_TYPE_CD + "' ", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();


                    sb.setBrand_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND_CD")));

                    sb.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching opening stock!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching opening stock---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    //Header with cam data
    public ArrayList<StockNewGetterSetter> getHeaderStockImageData(String store_cd, String visit_date) {
        //Log.d("FetchingStoredata--------------->Start<------------", "------------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT DISTINCT BRAND_CD, BRAND, CATEGORY_CD, COMPANY_CD, BRAND_SEQUENCE FROM BRAND_MASTER ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();


                    sb.setBrand_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND_CD")));

                    sb.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND")));

                    sb.setCATEGORY_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_CD")));


                    sb.setCOMPANY_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("COMPANY_CD")));

                    sb.setBRAND_SEQUENCE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND_SEQUENCE")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }

        //Log.d("Fetching Header stock---------------------->Stop<-----------", "-------------------");
        return list;
    }

    //Stock data
    public ArrayList<StockNewGetterSetter> getFoodStoreAvailabilityData(String store_cd) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {

            String food = "Food";

            dbcursor = db
                    .rawQuery(
                            "SELECT DISTINCT SD.BRAND_CD, SD.BRAND FROM MAPPING_AVAILABILITY CD INNER JOIN SKU_MASTER SD ON CD.SKU_CD = SD.SKU_CD WHERE CD.STORE_CD ='" + store_cd + "' AND SD.CATEGORY_TYPE ='" + food + "' ORDER BY CD.BRAND_SEQUENCE"
                            , null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();


                    sb.setBrand_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND_CD")));

                    sb.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching opening stock!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching opening stock---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    //Stock Sku data
    public ArrayList<StockNewGetterSetter> getStockSkuData(String brand_cd, String STATE_CD) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT DISTINCT SD.SKU_CD, SD.SKU FROM MAPPING_AVAILABILITY CD INNER JOIN SKU_MASTER SD ON CD.SKU_CD = SD.SKU_CD WHERE CD.STATE_CD= '"
                    + STATE_CD + "' AND SD.BRAND_CD ='" + brand_cd + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();


                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU_CD")));

                    sb.setSku(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU")));


                    sb.setOpenning_total_stock("");
                    sb.setOpening_facing("");
                    sb.setStock_under45days("");
                    sb.setDate("");
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching opening stock!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching opening stock---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    //Food Stock data
    public ArrayList<FoodStoreInsertDataGetterSetter> getFoodSkuData(String brand_cd, String store_cd) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<FoodStoreInsertDataGetterSetter> list = new ArrayList<FoodStoreInsertDataGetterSetter>();
        Cursor dbcursor = null;

        try {

            String food = "Food";
            dbcursor = db
                    .rawQuery(
                            "SELECT SD.SKU_CD, SD.SKU, SD.PACKING FROM MAPPING_AVAILABILITY CD INNER JOIN SKU_MASTER SD ON CD.SKU_CD = SD.SKU_CD WHERE SD.BRAND_CD ='" + brand_cd + "' AND CD.STORE_CD ='" + store_cd + "' AND SD.CATEGORY_TYPE ='" + food + "' ORDER BY CD.SKU_SEQUENCE"
                            , null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    FoodStoreInsertDataGetterSetter sb = new FoodStoreInsertDataGetterSetter();


                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU_CD")));

                    sb.setSku(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU")));

                    sb.setActual_listed("NO");
                    sb.setAs_per_meccain("");
                    sb.setPacking_size(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("PACKING")));
                    sb.setMccain_df("");
                    sb.setStore_df("");
                    sb.setMtd_sales("");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching opening stock!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching opening stock---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    //Stock Sku Closing data
    public ArrayList<StockNewGetterSetter> getStockSkuClosingData(String brand_cd, String store_cd) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db
                    .rawQuery(
                            "SELECT DISTINCT SD.SKU_CD, SD.SKU FROM MAPPING_AVAILABILITY CD INNER JOIN SKU_MASTER SD ON CD.SKU_CD = SD.SKU_CD WHERE CD.STORE_CD= '" + store_cd + "' AND SD.BRAND_CD ='" + brand_cd + "' ORDER BY SD.SKU_SEQUENCE"
                            , null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();


                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU_CD")));

                    sb.setSku(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU")));

                    sb.setClosing_stock("");


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching opening stock!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching opening stock---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    //Stock Midday Sku data
    public ArrayList<StockNewGetterSetter> getStockSkuMiddayData(String brand_cd, String store_cd) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT DISTINCT SD.SKU_CD, SD.SKU FROM MAPPING_AVAILABILITY CD INNER JOIN SKU_MASTER SD ON CD.SKU_CD = SD.SKU_CD WHERE CD.STORE_CD= '" + store_cd + "' AND SD.BRAND_CD ='" + brand_cd + "' ORDER BY SD.SKU_SEQUENCE"
                            , null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();


                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU_CD")));

                    sb.setSku(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU")));

                    sb.setTotal_mid_stock_received("");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching opening stock!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching opening stock---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


//Promotion brand data


    public ArrayList<windowsChildData> getPromotionBrandData(String store_cd, String STATE_CD, String STORE_TYPE_CD) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<windowsChildData> list = new ArrayList<windowsChildData>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT DISTINCT WM.WINDOW, WM.WINDOW_CD FROM WINDOW_MASTER WM INNER JOIN WINDOW_MAPPING WMM ON WM.WINDOW_CD = WMM.WINDOW_CD WHERE WMM.STORETYPE_CD ='" + STORE_TYPE_CD + "' AND WMM.STATE_CD ='" + STATE_CD + "'   ", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    windowsChildData sb = new windowsChildData();


                    sb.setWINDOW(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("WINDOW")));

                    sb.setWINDOW_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("WINDOW_CD")));


                    sb.setSTATUS("");
                    sb.setSTATUS_CD("");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching opening stock!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching opening stock---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    public ArrayList<windowsChildData> getWindowsHeaderData(String store_cd) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<windowsChildData> list = new ArrayList<windowsChildData>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT DISTINCT WINDOW_CD, WINDOW, KEY_ID FROM openingHeader_Windows_data WHERE STORE_CD ='" + store_cd + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    windowsChildData sb = new windowsChildData();


                    sb.setWINDOW_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("WINDOW_CD")));

                    sb.setWINDOW(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("WINDOW")));

                    sb.setHeaderRefId(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("KEY_ID")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching opening stock!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching Asset brand---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


//Promotion brand data


    public ArrayList<AssetInsertdataGetterSetter> getAssetCategoryData(String store_cd) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<AssetInsertdataGetterSetter> list = new ArrayList<AssetInsertdataGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT DISTINCT CATEGORY_CD, CATEGORY FROM CATEGORY_MASTER BD WHERE CATEGORY_CD IN( SELECT DISTINCT CATEGORY_CD FROM MAPPING_ASSET WHERE STORE_CD ='" + store_cd + "' ) "
                            , null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetInsertdataGetterSetter sb = new AssetInsertdataGetterSetter();


                    sb.setCategory_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_CD")));

                    sb.setCategory(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching opening stock!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching Asset brand---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get promotion Sku data


    // get Asset data
    public ArrayList<AssetInsertdataGetterSetter> getAssetData(String category_cd, String store_cd) {
        Log.d("FetchingAssetdata--------------->Start<------------",
                "------------------");
        ArrayList<AssetInsertdataGetterSetter> list = new ArrayList<AssetInsertdataGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT AM.ASSET_CD, AM.ASSET, M.CATEGORY_CD FROM MAPPING_ASSET M INNER JOIN ASSET_MASTER AM ON M.ASSET_CD = AM.ASSET_CD WHERE M.STORE_CD ='" + store_cd + "' AND M.CATEGORY_CD ='" + category_cd + "'"
                            , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetInsertdataGetterSetter sb = new AssetInsertdataGetterSetter();


                    sb.setAsset(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET")));

                    sb.setAsset_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET_CD")));


                    sb.setPresent("NO");
                    sb.setRemark("");
                    sb.setImg("");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Asset!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching asset data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get Asset data
    public ArrayList<AssetInsertdataGetterSetter> getAllAssetData() {
        Log.d("FetchingAssetdata--------------->Start<------------",
                "------------------");
        ArrayList<AssetInsertdataGetterSetter> list = new ArrayList<AssetInsertdataGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM ASSET_MASTER"
                            , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetInsertdataGetterSetter sb = new AssetInsertdataGetterSetter();


                    sb.setAsset(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET")));

                    sb.setAsset_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET_CD")));


                    sb.setPresent("NO");
                    sb.setRemark("");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Asset!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching asset data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    // get Asset data


    //Insert Opening Stock Data


//Insert Opening Data with Brand

    public void InsertOpeningStocklistData(String storeid, String STATE_CD, String STORE_TYPE_CD,
                                           HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> data, List<StockNewGetterSetter> save_listDataHeader) {

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {

            db.beginTransaction();
            for (int i = 0; i < save_listDataHeader.size(); i++) {

                values.put("STORE_CD", storeid);
                values.put("STATE_CD", STATE_CD);
                values.put("STORE_TYPE_CD", STORE_TYPE_CD);

                values.put("BRAND_CD", save_listDataHeader.get(i)
                        .getBrand_cd());
                values.put("BRAND", save_listDataHeader
                        .get(i).getBrand());

                long l = db.insert(CommonString1.TABLE_INSERT_OPENINGHEADER_DATA, null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put("Common_Id", (int) l);
                    values1.put("SKU", data.get(save_listDataHeader.get(i)).get(j).getSku());
                    values1.put("SKU_CD", data.get(save_listDataHeader.get(i)).get(j).getSku_cd());
                    values1.put("BRAND_CD", save_listDataHeader.get(i).getBrand_cd());
                    values1.put("BRAND", save_listDataHeader.get(i).getBrand());
                    values1.put("STORE_CD", storeid);
                    values1.put("STATE_CD", STATE_CD);
                    values1.put("STORE_TYPE_CD", STORE_TYPE_CD);


                    values1.put("OPENING_TOTAL_STOCK", data.get(save_listDataHeader.get(i)).get(j).getOpenning_total_stock());
                    values1.put("FACING", data.get(save_listDataHeader.get(i)).get(j).getOpening_facing());
                    values1.put("STOCK_UNDER_DAYS", data.get(save_listDataHeader.get(i)).get(j).getStock_under45days());
                    values1.put("EXPRIY_DATE", data.get(save_listDataHeader.get(i)).get(j).getDate());

                    db.insert(CommonString1.TABLE_STOCK_DATA, null, values1);

                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }

    //Insert Header Data with img


//Update Opening Data with Brand

    public void UpdateOpeningStocklistData(String storeid,
                                           HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> data, List<StockNewGetterSetter> save_listDataHeader) {

        //	ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {

            ArrayList<HeaderGetterSetter> list = new ArrayList<HeaderGetterSetter>();

            list = getHeaderStock(storeid);

            db.beginTransaction();

            for (int i = 0; i < list.size(); i++) {


                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {


                    values1.put("OPENING_TOTAL_STOCK", data.get(save_listDataHeader.get(i)).get(j).getOpenning_total_stock());
                    values1.put("FACING", data.get(save_listDataHeader.get(i)).get(j).getOpening_facing());
                    values1.put("STOCK_UNDER_DAYS", data.get(save_listDataHeader.get(i)).get(j).getStock_under45days());
                    values1.put("EXPRIY_DATE", data.get(save_listDataHeader.get(i)).get(j).getDate());

                    db.update(CommonString1.TABLE_STOCK_DATA, values1, "Common_Id" + "='" + Integer.parseInt(list.get(i).getKeyId()) + "' AND SKU_CD " + "='" + Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j).getSku_cd()) + "'", null);

                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }

    //Update Opening Data with Brand


//Insert Closing Data with Brand

    public void InsertClosingStocklistData(String storeid,
                                           HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> data, List<StockNewGetterSetter> save_listDataHeader) {

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {

            db.beginTransaction();
            for (int i = 0; i < save_listDataHeader.size(); i++) {

                values.put("STORE_CD", storeid);

                values.put("BRAND_CD", save_listDataHeader.get(i)
                        .getBrand_cd());
                values.put("BRAND", save_listDataHeader
                        .get(i).getBrand());

                long l = db.insert(CommonString1.TABLE_INSERT_OPENINGHEADER_DATA,
                        null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put("Common_Id", (int) l);

                    values1.put("BRAND_CD", save_listDataHeader.get(i).getBrand_cd());
                    values1.put("BRAND", save_listDataHeader.get(i).getBrand());

                    values1.put("STORE_CD", storeid);
                    values1.put("SKU", data.get(save_listDataHeader.get(i)).get(j).getSku());
                    values1.put("SKU_CD", Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j)
                            .getSku_cd()));
                    values1.put("CLOSING_STOCK", data.get(save_listDataHeader.get(i)).get(j).getClosing_stock());

                    db.insert(CommonString1.TABLE_STOCK_DATA, null, values1);

                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }

//Update Closing Data with Brand

    public void UpdateClosingStocklistData(String storeid,
                                           HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> data, List<StockNewGetterSetter> save_listDataHeader) {

        //ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {

            ArrayList<HeaderGetterSetter> list = new ArrayList<HeaderGetterSetter>();

            list = getHeaderStock(storeid);

            db.beginTransaction();

            for (int i = 0; i < list.size(); i++) {


                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put("CLOSING_STOCK", data.get(save_listDataHeader.get(i)).get(j).getClosing_stock());


                    db.update(CommonString1.TABLE_STOCK_DATA, values1, "Common_Id" + "='" + Integer.parseInt(list.get(i).getKeyId()) + "' AND SKU_CD " + "='" + Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j).getSku_cd()) + "'", null);
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }


//Insert Midday Data with Brand

    public void InsertMiddayStocklistData(String storeid,
                                          HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> data, List<StockNewGetterSetter> save_listDataHeader) {

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {

            db.beginTransaction();
            for (int i = 0; i < save_listDataHeader.size(); i++) {

                values.put("STORE_CD", storeid);

                values.put("BRAND_CD", save_listDataHeader.get(i)
                        .getBrand_cd());
                values.put("BRAND", save_listDataHeader
                        .get(i).getBrand());

                long l = db.insert(CommonString1.TABLE_INSERT_OPENINGHEADER_DATA,
                        null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put("Common_Id", (int) l);

                    values1.put("BRAND_CD", save_listDataHeader.get(i).getBrand_cd());
                    values1.put("BRAND", save_listDataHeader.get(i).getBrand());

                    values1.put("STORE_CD", storeid);
                    values1.put("SKU", data.get(save_listDataHeader.get(i)).get(j).getSku());
                    values1.put("SKU_CD", Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j)
                            .getSku_cd()));
                    values1.put("MIDDAY_TOTAL_STOCK", data.get(save_listDataHeader.get(i)).get(j).getTotal_mid_stock_received());

                    db.insert(CommonString1.TABLE_STOCK_DATA, null, values1);

                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }

//Update Midday Data with Brand

    public void UpdateMiddayStocklistData(String storeid,
                                          HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> data, List<StockNewGetterSetter> save_listDataHeader) {

        ContentValues values1 = new ContentValues();

        try {

            ArrayList<HeaderGetterSetter> list = new ArrayList<HeaderGetterSetter>();

            list = getHeaderStock(storeid);

            //db.beginTransaction();
            for (int i = 0; i < list.size(); i++) {


                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put("MIDDAY_TOTAL_STOCK", data.get(save_listDataHeader.get(i)).get(j).getTotal_mid_stock_received());

                    db.update(CommonString1.TABLE_STOCK_DATA, values1, "Common_Id" + "='" + Integer.parseInt(list.get(i).getKeyId()) + "' AND SKU_CD " + "='" + Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j).getSku_cd()) + "'", null);

                }
            }
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }


//Insert Food Store List data

    public void InsertFoodStorelistData(String storeid,
                                        HashMap<StockNewGetterSetter, List<FoodStoreInsertDataGetterSetter>> data, List<StockNewGetterSetter> save_listDataHeader) {

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {

            db.beginTransaction();
            for (int i = 0; i < save_listDataHeader.size(); i++) {

                values.put("STORE_CD", storeid);

                values.put("BRAND_CD", save_listDataHeader.get(i)
                        .getBrand_cd());
                values.put("BRAND", save_listDataHeader
                        .get(i).getBrand());

                long l = db.insert(CommonString1.TABLE_INSERT_HEADER_FOOD_STORE_DATA,
                        null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put("Common_Id", (int) l);
                    values1.put("STORE_CD", storeid);
                    values1.put("SKU_CD", Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j)
                            .getSku_cd()));
                    values1.put("SKU", data.get(save_listDataHeader.get(i)).get(j)
                            .getSku());
                    values1.put("ACTUAL_LISTED", data.get(save_listDataHeader.get(i)).get(j).getActual_listed());
                    values1.put("AS_PER_MCCAIN", data.get(save_listDataHeader.get(i)).get(j).getAs_per_meccain());
                    values1.put("MCCAIN_DF", data.get(save_listDataHeader.get(i)).get(j).getMccain_df());
                    values1.put("STORE_DF", data.get(save_listDataHeader.get(i)).get(j).getStore_df());
                    values1.put("PACKING_SIZE", data.get(save_listDataHeader.get(i)).get(j).getPacking_size());
                    values1.put("MTD_SALES", data.get(save_listDataHeader.get(i)).get(j).getMtd_sales());

                    db.insert(CommonString1.TABLE_FOOD_STORE_DATA, null, values1);

                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }

//Insert Promotion Data with Brand

    public void InsertwindowsData(String storeid, String STATE_CD, String STORE_TYPE_CD, String _path,
                                  HashMap<windowsChildData, List<windowsChildData>> data, List<windowsChildData> save_listDataHeader, List<MappingAssetGetterSetter> checklistInsertDataGetterSetters) {

        db.delete(CommonString1.TABLE_INSERT_PROMOTION_HEADER_DATA, CommonString1.KEY_STORE_CD + "='" + storeid + "'", null);

        db.delete(CommonString1.TABLE_PROMOTION_DATA, CommonString1.KEY_STORE_CD + "='" + storeid + "'", null);
        db.delete(CommonString1.TABLE_CHECK_LIST_DATA, CommonString1.KEY_STORE_CD + "='" + storeid + "'", null);


        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();
        ContentValues values2 = new ContentValues();

        try {

            db.beginTransaction();
            for (int i = 0; i < save_listDataHeader.size(); i++) {

                values.put("STORE_CD", storeid);

                values.put("STATE_CD", STATE_CD);
                values.put("STORE_TYPE_CD", STORE_TYPE_CD);

                values.put("WINDOW_CD", save_listDataHeader.get(i).getWINDOW_CD());


                values.put("WINDOW", save_listDataHeader
                        .get(i).getWINDOW());

                long l = db.insert(CommonString1.TABLE_INSERT_PROMOTION_HEADER_DATA,
                        null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put("Common_Id", (int) l);
                    values1.put("STORE_CD", storeid);
                    values1.put("STATE_CD", STATE_CD);
                    values1.put("STORE_TYPE_CD", STORE_TYPE_CD);


                    values1.put("IMAGE", data.get(save_listDataHeader.get(i)).get(j).getImage());
                    values1.put("STATUS_CD", data.get(save_listDataHeader.get(i)).get(j).getSTATUS_CD());
                    values1.put("REASON", data.get(save_listDataHeader.get(i)).get(j).getRemarks());


                    db.insert(CommonString1.TABLE_PROMOTION_DATA, null, values1);

                }
                for (int k = 0; k < checklistInsertDataGetterSetters.size(); k++) {


                    if (checklistInsertDataGetterSetters.get(k).getWindows_cd().equals(save_listDataHeader.get(i).getWINDOW_CD())) {

                        values2.put("Common_Id", (int) l);
                        values2.put("STORE_CD", storeid);

                        values2.put("Status", checklistInsertDataGetterSetters.get(k).getStatus());
                        values2.put("CHECKLIST", checklistInsertDataGetterSetters.get(k).getCHECKLIST().get(0));
                        values2.put("CHECKLIST_ID", checklistInsertDataGetterSetters.get(k).getCHECKLIST_ID().get(0));

                        values2.put("Windows_cd", checklistInsertDataGetterSetters.get(k).getWindows_cd());


                        db.insert(CommonString1.TABLE_CHECK_LIST_DATA, null, values2);

                    }


                }


            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }


//Insert Asset Data with Brand

    public void InsertAssetData(String storeid,
                                HashMap<AssetInsertdataGetterSetter, List<AssetInsertdataGetterSetter>> data, List<AssetInsertdataGetterSetter> save_listDataHeader) {

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {

            db.beginTransaction();
            for (int i = 0; i < save_listDataHeader.size(); i++) {

                values.put("STORE_CD", storeid);

                values.put("CATEGORY_CD", save_listDataHeader.get(i)
                        .getCategory_cd());
                values.put("CATEGORY", save_listDataHeader
                        .get(i).getCategory());

                long l = db.insert(CommonString1.TABLE_INSERT_ASSET_HEADER_DATA,
                        null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put("Common_Id", (int) l);
                    values1.put("STORE_CD", storeid);
                    values1.put("ASSET_CD", Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j)
                            .getAsset_cd()));
                    values1.put("ASSET", data.get(save_listDataHeader.get(i)).get(j).getAsset());
                    values1.put("REMARK", data.get(save_listDataHeader.get(i)).get(j).getRemark());
                    values1.put("PRESENT", data.get(save_listDataHeader.get(i)).get(j).getPresent());
                    values1.put("IMAGE", data.get(save_listDataHeader.get(i)).get(j).getImg());

                    db.insert(CommonString1.TABLE_ASSET_DATA, null, values1);

                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }


    //Get Asset Upload Data

    public ArrayList<AssetInsertdataGetterSetter> getAssetDataFromdatabase(String storeId, String category_cd) {
        Log.d("FetchingAssetuploaddata--------------->Start<------------",
                "------------------");
        ArrayList<AssetInsertdataGetterSetter> list = new ArrayList<AssetInsertdataGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.ASSET_CD, SD.ASSET, SD.PRESENT, SD.REMARK, SD.IMAGE, CD.CATEGORY_CD, CD.CATEGORY " +
                                    "FROM openingHeader_Asset_data CD INNER JOIN ASSET_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '"
                                    + storeId + "' AND CD.CATEGORY_CD = '" + category_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetInsertdataGetterSetter sb = new AssetInsertdataGetterSetter();

                    sb.setAsset_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET_CD")));

                    sb.setAsset(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET")));

                    sb.setPresent(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("PRESENT")));
                    sb.setRemark(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REMARK")));
                    sb.setImg(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("IMAGE")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_CD")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    //check if table is empty
    public boolean isAssetDataFilled(String storeId) {
        boolean filled = false;

        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM ASSET_DATA WHERE STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getInt(0);
                dbcursor.close();
                filled = icount > 0;

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return filled;
        }

        return filled;
    }


//Get Asset Upload Data

    public ArrayList<AssetInsertdataGetterSetter> getAssetUpload(String storeId) {
        Log.d("FetchingAssetuploaddata--------------->Start<------------",
                "------------------");
        ArrayList<AssetInsertdataGetterSetter> list = new ArrayList<AssetInsertdataGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.ASSET_CD,SD.ASSET,SD.PRESENT,SD.REMARK, SD.IMAGE,CD.CATEGORY_CD, CD.CATEGORY " +
                                    "FROM openingHeader_Asset_data CD INNER JOIN ASSET_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '"
                                    + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetInsertdataGetterSetter sb = new AssetInsertdataGetterSetter();

                    sb.setAsset_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET_CD")));

                    sb.setAsset(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET")));
                    sb.setImg(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("IMAGE")));

                    sb.setPresent(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("PRESENT")));
                    sb.setRemark(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REMARK")));
                    sb.setCategory_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_CD")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    //Get Promotion Upload Data


    //Get Promotion Upload Data

    public ArrayList<windowsChildData> getWindowsFromDatabase(String storeId, String key_id) {
        Log.d("FetchingPromotionuploaddata--------------->Start<------------",
                "------------------");
        ArrayList<windowsChildData> list = new ArrayList<windowsChildData>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.REASON, SD.STATUS_CD, SD.IMAGE" +
                                    " FROM openingHeader_Windows_data CD INNER JOIN WINDOWS_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '" + storeId + "' AND SD.Common_Id = '" + key_id + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    windowsChildData sb = new windowsChildData();

                    sb.setRemarks(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REASON")));
                    sb.setSTATUS_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STATUS_CD")));

                    sb.setImage(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("IMAGE")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    public ArrayList<MappingAssetGetterSetter> getCheckListFromDatabase(String key_id) {
        Log.d("FetchingPromotionuploaddata--------------->Start<------------",
                "------------------");
        ArrayList<MappingAssetGetterSetter> list = new ArrayList<MappingAssetGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM CheckList_DATA WHERE Common_Id= '" + key_id + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingAssetGetterSetter sb = new MappingAssetGetterSetter();

                    sb.setStatus(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("Status")));
                    sb.setCHECKLIST(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKLIST")));

                    sb.setCHECKLIST_ID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKLIST_ID")));

                    sb.setWindows_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("Windows_cd")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    //check if table is empty
    public boolean isPromotionDataFilled(String storeId) {
        boolean filled = false;

        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM WINDOWS_DATA WHERE STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getInt(0);
                dbcursor.close();
                filled = icount > 0;

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return filled;
        }

        return filled;
    }

//Get Promotion Upload Data


//Get Promotion Database Data

    public ArrayList<FoodStoreInsertDataGetterSetter> getFoodStoreDataFromDatabase(String storeId, String brand_cd) {
        Log.d("FetchingPromotionuploaddata--------------->Start<------------",
                "------------------");
        ArrayList<FoodStoreInsertDataGetterSetter> list = new ArrayList<FoodStoreInsertDataGetterSetter>();
        Cursor dbcursor = null;
        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.SKU_CD, SD.SKU,SD.AS_PER_MCCAIN,SD.ACTUAL_LISTED,SD.MCCAIN_DF,SD.STORE_DF, SD.MTD_SALES, SD.PACKING_SIZE, CD.BRAND_CD,CD.BRAND " +
                                    "FROM openingHeader_FOOD_STORE_data CD INNER JOIN FOOD_STORE_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '"
                                    + storeId + "' AND CD.BRAND_CD = '" + brand_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    FoodStoreInsertDataGetterSetter sb = new FoodStoreInsertDataGetterSetter();

                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU_CD")));
                    sb.setSku(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU")));

                    sb.setAs_per_meccain(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("AS_PER_MCCAIN")));

                    sb.setActual_listed(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ACTUAL_LISTED")));
                    sb.setMccain_df(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("MCCAIN_DF")));
                    sb.setStore_df(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_DF")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND")));
                    sb.setMtd_sales(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("MTD_SALES")));
                    sb.setPacking_size(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("PACKING_SIZE")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingFoodStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    //check if table is empty


//Get Stock Upload Data


    //Get Opening and Midday Stock for validation


    //opening stock

    public boolean checkStock(String STORE_CD) {
        Log.d("FetchingOpening Stock data--------------->Start<------------",
                "------------------");
        ArrayList<StockGetterSetter> list = new ArrayList<StockGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT DISTINCT SD.SKU_CD FROM openingHeader_data CD INNER JOIN STOCK_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '"
                                    + STORE_CD + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockGetterSetter sb = new StockGetterSetter();

                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU_CD")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();

                return list.size() > 0;

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return false;
        }

        Log.d("FetchingOPening midday---------------------->Stop<-----------",
                "-------------------");
        return false;
    }


    //opening stock

    public ArrayList<StockGetterSetter> getOpeningStock(String storeId) {
        Log.d("FetchingOpening Stock data--------------->Start<------------",
                "------------------");
        ArrayList<StockGetterSetter> list = new ArrayList<StockGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.OPENING_STOCK_COLD_ROOM FROM openingHeader_data CD INNER JOIN STOCK_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '"
                                    + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockGetterSetter sb = new StockGetterSetter();

                    sb.setOpen_stock_cold_room(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("OPENING_STOCK_COLD_ROOM")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingOPening midday---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


//Header Key data

    public ArrayList<HeaderGetterSetter> getHeaderStock(String storeId) {
        Log.d("FetchingOpening Stock data--------------->Start<------------",
                "------------------");
        ArrayList<HeaderGetterSetter> list = new ArrayList<HeaderGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT KEY_ID FROM openingHeader_data WHERE STORE_CD= '"
                                    + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    HeaderGetterSetter sb = new HeaderGetterSetter();

                    sb.setKeyId(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("KEY_ID")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingOPening midday---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


//get opening stock data from database

    public ArrayList<StockNewGetterSetter> getOpeningStockDataFromDatabase(String store_cd, String brand_cd) {
        Log.d("FetchingOpening Stock data--------------->Start<------------",
                "------------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT SKU_CD,SKU, OPENING_TOTAL_STOCK, FACING ,STOCK_UNDER_DAYS,EXPRIY_DATE FROM  STOCK_DATA  WHERE STORE_CD= '" + store_cd + "' AND BRAND_CD = '" + brand_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();


                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU_CD")));

                    sb.setSku(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU")));

                    sb.setOpenning_total_stock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("OPENING_TOTAL_STOCK")));

                    sb.setOpening_facing(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("FACING")));
                    sb.setStock_under45days(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STOCK_UNDER_DAYS")));

                    sb.setDate(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("EXPRIY_DATE")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingOPening midday---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    //check if table is empty
    public boolean isOpeningDataFilled(String store_cd) {
        boolean filled = false;

        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT OPENING_TOTAL_STOCK, FACING,STOCK_UNDER_DAYS FROM STOCK_DATA WHERE STORE_CD= '" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    if (dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_TOTAL_STOCK")).equals("") || dbcursor.getString(dbcursor.getColumnIndexOrThrow("FACING")).equals("")) {
                        filled = false;
                        break;
                    } else {
                        filled = true;
                    }

                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return filled;
        }

        return filled;
    }


    //check if table is empty
    public boolean isPOSMDataFilled(String store_cd) {
        boolean filled = false;
        Cursor dbcursor = null;
        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM INSERT_POSM_DATA where STORE_CD = '" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    if (!dbcursor.getString(dbcursor.getColumnIndexOrThrow("QUANTITY")).equalsIgnoreCase("") || !dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE")).equalsIgnoreCase("")) {
                        filled = true;
                        break;
                    } else {
                        filled = false;
                    }

                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return filled;
        }

        return filled;
    }


//get Opening and Midday stock data for comparison from database

    public ArrayList<StockNewGetterSetter> getOpeningNMiddayStockDataFromDatabase(String storeId, String brand_cd) {
        Log.d("FetchingOpening Stock data--------------->Start<------------",
                "------------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.SKU_CD, SD.SKU, SD.OPENING_TOTAL_STOCK, SD.MIDDAY_TOTAL_STOCK FROM openingHeader_data CD INNER JOIN STOCK_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '"
                                    + storeId + "' AND CD.BRAND_CD = '" + brand_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();


                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU_CD")));

                    sb.setSku(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU")));

                    sb.setOpenning_total_stock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("OPENING_TOTAL_STOCK")));

                    sb.setTotal_mid_stock_received(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("MIDDAY_TOTAL_STOCK")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingOPening midday---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


//get Closing and Midday stock data for comparison from database


//get Closing stock data from database

    public ArrayList<StockNewGetterSetter> getClosingStockDataFromDatabase(String storeId, String brand_cd) {
        Log.d("FetchingOpening Stock data--------------->Start<------------",
                "------------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.SKU_CD, SD.SKU, SD.CLOSING_STOCK FROM openingHeader_data CD INNER JOIN STOCK_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '"
                                    + storeId + "' AND CD.BRAND_CD = '" + brand_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();


                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU_CD")));

                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU_CD")));

                    sb.setSku(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU")));

                    sb.setClosing_stock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CLOSING_STOCK")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingOPening midday---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


//get Closing stock data from database


    //check if table is empty
    public boolean isClosingDataFilled(String storeId) {
        boolean filled = false;

        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM INSERT_POSM_DATA WHERE STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    if (dbcursor.getString(dbcursor.getColumnIndexOrThrow("QUANTITY")).equals("")) {
                        filled = false;
                        break;
                    } else {
                        filled = true;
                    }

                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return filled;
        }

        return filled;


    }


//get Midday stock data from database

    public ArrayList<StockNewGetterSetter> getMiddayStockDataFromDatabase(String storeId, String brand_cd) {
        Log.d("FetchingOpening Stock data--------------->Start<------------",
                "------------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.SKU_CD, SD.SKU, SD.MIDDAY_TOTAL_STOCK FROM openingHeader_data CD INNER JOIN STOCK_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '"
                                    + storeId + "' AND CD.BRAND_CD = '" + brand_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();


                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU_CD")));

                    sb.setSku(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SKU")));


                    sb.setTotal_mid_stock_received(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("MIDDAY_TOTAL_STOCK")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingOPening midday---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    //check if table is empty
    public boolean isMiddayDataFilled(String storeId) {
        boolean filled = false;

        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT MIDDAY_TOTAL_STOCK FROM STOCK_DATA WHERE STORE_CD= '" + storeId + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    if (dbcursor.getString(dbcursor.getColumnIndexOrThrow("MIDDAY_TOTAL_STOCK")).equals("")) {
                        filled = false;
                        break;
                    } else {
                        filled = true;
                    }

                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return filled;
        }

        return filled;
    }


//Middaystock stock

    public ArrayList<StockGetterSetter> getMiddayStock(String storeId) {
        Log.d("FetchingOpening Stock data--------------->Start<------------",
                "------------------");
        ArrayList<StockGetterSetter> list = new ArrayList<StockGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.MIDDAY_STOCK FROM openingHeader_data CD INNER JOIN STOCK_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '"
                                    + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockGetterSetter sb = new StockGetterSetter();

                    sb.setMidday_stock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("MIDDAY_STOCK")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingOPening midday---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


//deletecross Stock Data


//deletecross Stock Data closing


    public void deleteFoodStoreData(String storeid) {

        try {

            db.delete(CommonString1.TABLE_FOOD_STORE_DATA,
                    CommonString1.KEY_STORE_CD + "='" + storeid + "'", null);

            db.delete(CommonString1.TABLE_INSERT_HEADER_FOOD_STORE_DATA,
                    CommonString1.KEY_STORE_CD + "='" + storeid + "'", null);

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }
    }


//deletecross Promotion Data


//deletecross Asset Data

    public void deleteAssetData(String storeid) {

        try {

            db.delete(CommonString1.TABLE_ASSET_DATA,
                    CommonString1.KEY_STORE_CD + "='" + storeid + "'", null);

            db.delete(CommonString1.TABLE_INSERT_ASSET_HEADER_DATA,
                    CommonString1.KEY_STORE_CD + "='" + storeid + "'", null);

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }
    }


//Insert Closing Stock Data


//Insert Closing Stock Data

    public void insertMiddayStockData(String midday_stock, String sku_cd) {

        db.delete(CommonString1.TABLE_MIDDAY_STOCK_DATA, "SKU_CD" + "='" + sku_cd + "'", null);
        ContentValues values = new ContentValues();

        try {


            values.put("SKU_CD", Integer.parseInt(sku_cd));
            values.put("MIDDAY_STOCK", midday_stock);


            db.insert(CommonString1.TABLE_MIDDAY_STOCK_DATA, null, values);


        } catch (Exception ex) {
            Log.d("Database Exception while Insert OPENING STOCK Data ",
                    ex.toString());
        }

    }


//get if category type is food


//get DeepFreezerType Data

    public ArrayList<FacingCompetitorGetterSetter> getCategoryData() {

        Log.d("FetchingCategoryType--------------->Start<------------",
                "------------------");
        ArrayList<FacingCompetitorGetterSetter> list = new ArrayList<FacingCompetitorGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT CATEGORY_CD, CATEGORY from CATEGORY_MASTER"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    FacingCompetitorGetterSetter df = new FacingCompetitorGetterSetter();


                    df.setCategory_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_CD")));
                    df.setCategory(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY")));
                    df.setBrand("");
                    df.setBrand_cd("");
                    df.setMccaindf("");
                    //df.setStoredf("");

                    list.add(df);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Category!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingCategory data---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

//category for competition

    public ArrayList<FacingCompetitorGetterSetter> getCategoryCompetionData() {

        Log.d("FetchingCategoryType--------------->Start<------------",
                "------------------");
        ArrayList<FacingCompetitorGetterSetter> list = new ArrayList<FacingCompetitorGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT CATEGORY_CD, CATEGORY from CATEGORY_MASTER WHERE CATEGORY_CD IN(SELECT DISTINCT CATEGORY_CD FROM BRAND_MASTER WHERE COMPANY_CD <>" + "'1'" + ")"
                    , null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    FacingCompetitorGetterSetter df = new FacingCompetitorGetterSetter();


                    df.setCategory_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_CD")));
                    df.setCategory(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY")));
                    df.setBrand("");
                    df.setBrand_cd("");
                    df.setMccaindf("");
                    //df.setStoredf("");

                    list.add(df);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Category!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingCategory data---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    //category for competition faceup

    public ArrayList<FacingCompetitorGetterSetter> getCategoryCompetionFaceupData(String store_cd) {

        Log.d("FetchingCategoryType--------------->Start<------------",
                "------------------");
        ArrayList<FacingCompetitorGetterSetter> list = new ArrayList<FacingCompetitorGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT CATEGORY_CD, CATEGORY FROM CATEGORY_MASTER WHERE CATEGORY_CD IN(SELECT DISTINCT CATEGORY_CD FROM SKU_MASTER WHERE SKU_CD IN( SELECT  SKU_CD FROM MAPPING_AVAILABILITY  WHERE STORE_CD = '" + store_cd + "'))"
                    , null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    FacingCompetitorGetterSetter df = new FacingCompetitorGetterSetter();


                    df.setCategory_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_CD")));
                    df.setCategory(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY")));
                    df.setBrand("");
                    df.setBrand_cd("");
                    df.setMccaindf("");
                    //df.setStoredf("");

                    list.add(df);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Category!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingCategory data---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

//get Brand Data


    //get Brand Data for competion faceup

    public ArrayList<FacingCompetitorGetterSetter> getBrandCompetitionData(String category_id) {

        Log.d("FetchingBrand-->Start<-",
                "------------------");
        ArrayList<FacingCompetitorGetterSetter> list = new ArrayList<FacingCompetitorGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT BRAND_CD, BRAND from BRAND_MASTER  WHERE CATEGORY_CD" + " = '" + category_id +
                            "' AND COMPANY_CD <> " + "'1'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    FacingCompetitorGetterSetter df = new FacingCompetitorGetterSetter();


                    df.setBrand_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND_CD")));
                    df.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND")));
                    df.setMccaindf("");

                    list.add(df);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception fetch Brand",
                    e.toString());
            return list;
        }

        Log.d("Brand data-->Stop<-",
                "-------------------");
        return list;

    }


//get Company Data


    public int CheckMid(String currdate, String storeid) {

        Cursor dbcursor = null;
        int mid = 0;
        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString1.TABLE_COVERAGE_DATA + "  WHERE "
                    + CommonString1.KEY_VISIT_DATE + " = '" + currdate
                    + "' AND " + CommonString1.KEY_STORE_ID + " ='" + storeid
                    + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();

                mid = dbcursor.getInt(dbcursor
                        .getColumnIndexOrThrow(CommonString1.KEY_ID));

                dbcursor.close();

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
        }

        return mid;
    }


    public long InsertCoverageData(CoverageBean data) {
        db.delete(CommonString1.TABLE_COVERAGE_DATA, CommonString1.KEY_STORE_ID + "='" + data.getStoreId() + "'", null);

        ContentValues values = new ContentValues();
        try {
            values.put(CommonString1.KEY_STORE_ID, data.getStoreId());
            values.put(CommonString1.KEY_USER_ID, data.getUserId());
            values.put(CommonString1.KEY_IN_TIME, data.getInTime());
            values.put(CommonString1.KEY_OUT_TIME, data.getOutTime());
            values.put(CommonString1.KEY_VISIT_DATE, data.getVisitDate());
            values.put(CommonString1.KEY_LATITUDE, data.getLatitude());
            values.put(CommonString1.KEY_LONGITUDE, data.getLongitude());
            values.put(CommonString1.KEY_REASON_ID, data.getReasonid());
            values.put(CommonString1.KEY_REASON, data.getReason());
            values.put(CommonString1.KEY_COVERAGE_STATUS, data.getStatus());
            values.put(CommonString1.KEY_IMAGE, data.getImage());
            values.put(CommonString1.KEY_COVERAGE_REMARK, data.getRemark());
            values.put(CommonString1.KEY_REASON_ID, data.getReasonid());
            values.put(CommonString1.KEY_REASON, data.getReason());
            values.put(CommonString1.KEY_GEO_TAG, data.getGEO_TAG());
            values.put(CommonString1.KEY_CITY, data.getCity());
            return db.insert(CommonString1.TABLE_COVERAGE_DATA, null, values);
        } catch (Exception ex) {
            Log.d("Database ", "Exception while Insert Closes Data " + ex.toString());
        }
        return 0;
    }

    // getCoverageData
    public CoverageBean getCoverageWhirlpoolSkuData(String visitdate, String store_cd) {
        CoverageBean sb = new CoverageBean();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString1.TABLE_COVERAGE_DATA +
                    " where " + CommonString1.KEY_VISIT_DATE + "='" + visitdate + "' and " + CommonString1.KEY_STORE_ID + "='" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    sb.setDeployment_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_DEPLOYMENT_CD)));
                    sb.setWhirlpool_sku((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_WHIRLPOOL_SKU))));
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Coverage Data!!!!!!!!!!!!!!!!!!!!!" + e.toString());
        }
        return sb;
    }

    // getCoverageData
    public ArrayList<CoverageBean> getCoverageData(String visitdate) {
        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString1.TABLE_COVERAGE_DATA +
                    " where " + CommonString1.KEY_VISIT_DATE + "='" + visitdate + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();

                while (!dbcursor.isAfterLast()) {
                    CoverageBean sb = new CoverageBean();
                    sb.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_STORE_ID)));
                    sb.setUserId((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_USER_ID))));
                    sb.setInTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_IN_TIME)))));
                    sb.setOutTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_OUT_TIME)))));
                    sb.setVisitDate((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_VISIT_DATE))))));
                    sb.setLatitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_LATITUDE)))));
                    sb.setLongitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_LONGITUDE)))));
                    sb.setStatus((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_COVERAGE_STATUS))))));
                    sb.setDeployment_cd((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_DEPLOYMENT_CD))))));
                    sb.setImage((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_IMAGE))))));
                    sb.setReason((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_REASON))))));
                    sb.setReasonid((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_REASON_ID))))));
                    sb.setMID(Integer.parseInt(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_ID))))));
                    sb.setRemark((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_COVERAGE_REMARK))))));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Coverage Data!!!!!!!!!!!!!!!!!!!!!" + e.toString());
        }
        return list;
    }


    // getCoverageData
    public ArrayList<CoverageBean> getCoverageSpecificData(String store_id) {
        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString1.TABLE_COVERAGE_DATA + " where " + CommonString1.KEY_STORE_ID + "='" + store_id + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    CoverageBean sb = new CoverageBean();
                    sb.setUserId((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_USER_ID))));
                    sb.setInTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_IN_TIME)))));
                    sb.setOutTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_OUT_TIME)))));
                    sb.setVisitDate((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_VISIT_DATE))))));
                    sb.setLatitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_LATITUDE)))));
                    sb.setLongitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_LONGITUDE)))));
                    sb.setStatus((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_COVERAGE_STATUS))))));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Coverage Data!!!!!!!!!!!!!!!!!!!!!" + e.toString());
        }
        return list;
    }

    //check if table is empty
    public boolean isCoverageDataFilled(String visit_date) {
        boolean filled = false;

        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM COVERAGE_DATA " + "where " + CommonString1.KEY_VISIT_DATE + "<>'" + visit_date + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getInt(0);
                dbcursor.close();
                filled = icount > 0;

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return filled;
        }

        return filled;
    }

    public long updateCoverageStatus(int mid, String status) {
        long l = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString1.KEY_COVERAGE_STATUS, status);
            l = db.update(CommonString1.TABLE_COVERAGE_DATA, values, CommonString1.KEY_STORE_ID + "=" + mid, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public long updateCoverageStoreOutTime(String StoreId, String VisitDate, String outtime, String status) {
        long l = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString1.KEY_OUT_TIME, outtime);
            values.put(CommonString1.KEY_COVERAGE_STATUS, status);
            l = db.update(CommonString1.TABLE_COVERAGE_DATA, values, CommonString1.KEY_STORE_ID + "='" + StoreId + "' AND " + CommonString1.KEY_VISIT_DATE + "='" + VisitDate + "'", null);
        } catch (Exception e) {

        }
        return l;
    }

    public void updateStoreStatusOnLeave(String storeid, String visitdate, String status) {

        try {
            ContentValues values = new ContentValues();
            values.put("UPLOAD_STATUS", status);
            db.update("JOURNEY_PLAN", values, CommonString1.KEY_STORE_CD + "='" + storeid + "' AND " + CommonString1.KEY_VISIT_DATE + "='" + visitdate + "'", null);
        } catch (Exception e) {

        }
    }

    public long updateStoreStatusOnCheckout(String storeid, String visitdate, String status) {
        long l = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString1.KEY_CHECKOUT_STATUS, status);
            l = db.update("JOURNEY_PLAN", values, CommonString1.KEY_STORE_CD + "='" + storeid + "' AND " + CommonString1.KEY_VISIT_DATE + "='" + visitdate + "'", null);
        } catch (Exception e) {

        }
        return l;
    }

    //Insert Calls Data
    public void insertCallsData(String store_cd, String total_calls, String productive_calls) {

        db.delete("CALLS_DATA", "STORE_CD" + "='" + store_cd + "'", null);
        ContentValues values = new ContentValues();

        try {
            values.put("STORE_CD", store_cd);
            values.put("TOTAL_CALLS", total_calls);
            values.put("PRODUCTIVE_CALLS", productive_calls);

            db.insert(CommonString1.TABLE_CALLS_DATA, null, values);


        } catch (Exception ex) {
            Log.d("Database Exception while Insert Calls Data ",
                    ex.toString());
        }

    }

    //get Calls Data
    public ArrayList<CallsGetterSetter> getCallsData(String store_cd) {

        Log.d("Fetching calls--------------->Start<------------",
                "------------------");
        ArrayList<CallsGetterSetter> list = new ArrayList<CallsGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from CALLS_DATA where STORE_CD = '" + store_cd + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    CallsGetterSetter fc = new CallsGetterSetter();


                    fc.setStore_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STORE_CD")));
                    fc.setTotal_calls(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("TOTAL_CALLS")));
                    fc.setProductive_calls(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("PRODUCTIVE_CALLS")));
                    fc.setKey_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("Key_Id")));

                    list.add(fc);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Calls data!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching facing Calls---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    //check if table is empty


    public void deleteStockRow(int id, String store_cd) {

        try {
            db.delete(CommonString1.TABLE_CALLS_DATA, CommonString1.KEY_STORE_CD + "='" + store_cd + "' AND "
                            + "Key_Id" + "='" + id + "'"
                    , null);
        } catch (Exception e) {

        }

    }


    //Insert Calls Data


    //get Calls Data

    public ArrayList<STOREFIRSTIMEGetterSetter> getCompetitionPOIData(String store_cd) {

        Log.d("Fetching calls--------------->Start<------------",
                "------------------");
        ArrayList<STOREFIRSTIMEGetterSetter> list = new ArrayList<STOREFIRSTIMEGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from COMPETITION_POI WHERE STORE_CD= '" + store_cd + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    STOREFIRSTIMEGetterSetter fc = new STOREFIRSTIMEGetterSetter();


                    fc.setId(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("Key_Id")));

                    list.add(fc);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching comp poi data!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching facing Calls---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public void deleteCompetitionPOIRow(int id) {

        try {
            db.delete(CommonString1.TABLE_COMPETITION_POI, "Key_Id " + "='" + id + "'"
                    , null);
        } catch (Exception e) {

        }

    }


    //Insert Calls Data

    public void insertCompetitionPromotionData(CompetitionPromotionGetterSetter poiGetterSetter, String store_cd) {

        //db.delete("CALLS_DATA", "STORE_CD" + "='" + store_cd + "'", null);
        ContentValues values = new ContentValues();

        try {

            values.put("STORE_CD", store_cd);
            values.put("CATEGORY_CD", poiGetterSetter.getCategory_cd());
            values.put("CATEGORY", poiGetterSetter.getCategory());
            values.put("BRAND_CD", poiGetterSetter.getBrand_cd());
            values.put("BRAND", poiGetterSetter.getBrand());
            values.put("REMARK", poiGetterSetter.getRemark());
            values.put("PROMOTION", poiGetterSetter.getPromotion());

            db.insert(CommonString1.TABLE_COMPETITION_PROMOTION, null, values);


        } catch (Exception ex) {
            Log.d("Database Exception while Insert Calls Data ",
                    ex.toString());
        }

    }

    //get Competition Promotion Data

    public ArrayList<windowsChildData> getwindowsData(String store_cd) {

        Log.d("Fetching calls--------------->Start<------------",
                "------------------");
        ArrayList<windowsChildData> list = new ArrayList<windowsChildData>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from WINDOWS_DATA WHERE STORE_CD= '" + store_cd + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    windowsChildData fc = new windowsChildData();


                    fc.setImage(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("IMAGE")));

                    fc.setRemarks(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REASON")));
                    fc.setSTATUS_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("STATUS_CD")));


                    list.add(fc);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching comp poi data!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching facing Calls---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public void deleteCompetitionPromotionRow(int id) {

        try {
            db.delete(CommonString1.TABLE_COMPETITION_PROMOTION, "Key_Id " + "='" + id + "'"
                    , null);
        } catch (Exception e) {

        }

    }


//Insert Calls Data

    public void insertSTOREFIRSTIMEData(STOREFIRSTIMEGetterSetter SFTGetterSetter, String store_cd) {

        db.delete("STORE_FIRST_TIME", "STORE_CD" + "='" + store_cd + "'", null);
        ContentValues values = new ContentValues();

        try {

            values.put("STORE_CD", store_cd);
            values.put("NAME", SFTGetterSetter.getName());
            values.put("CONTACT", SFTGetterSetter.getContact());
            values.put("ADDRESS", SFTGetterSetter.getAddress());

            db.insert(CommonString1.TABLE_STORE_FIRST_TIME, null, values);


        } catch (Exception ex) {
            Log.d("Database Exception while Insert Calls Data ",
                    ex.toString());
        }

    }


    //get Calls Data

    public ArrayList<STOREFIRSTIMEGetterSetter> getSFTData(String store_cd) {

        Log.d("Fetching calls--------------->Start<------------",
                "------------------");
        ArrayList<STOREFIRSTIMEGetterSetter> list = new ArrayList<STOREFIRSTIMEGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from STORE_FIRST_TIME WHERE STORE_CD= '" + store_cd + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    STOREFIRSTIMEGetterSetter fc = new STOREFIRSTIMEGetterSetter();

                    fc.setId(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("Key_Id")));
                    fc.setName(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("NAME")));
                    fc.setContact(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CONTACT")));
                    fc.setAddress(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ADDRESS")));

                    list.add(fc);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching comp poi data!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching facing Calls---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    /// get store Status
    public JourneyPlanGetterSetter getStoreStatus(String id) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        JourneyPlanGetterSetter sb = new JourneyPlanGetterSetter();

        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from  JOURNEY_PLAN"
                    + "  WHERE STORE_CD = '"
                    + id + "'", null);

            if (dbcursor != null) {
                int numrows = dbcursor.getCount();

                dbcursor.moveToFirst();
                for (int i = 0; i < numrows; i++) {

                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString1.KEY_STORE_CD)));

                    sb.setCheckOutStatus((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKOUT_STATUS"))));

                    sb.setUploadStatus(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("UPLOAD_STATUS")));

                    dbcursor.moveToNext();

                }

                dbcursor.close();

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return sb;

    }


//Checklist data

    public ArrayList<ChecklistInsertDataGetterSetter> getCheckListData(String asset_cd) {
        Log.d("Fetching checklist data--------------->Start<------------",
                "------------------");
        ArrayList<ChecklistInsertDataGetterSetter> list = new ArrayList<ChecklistInsertDataGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db
                    .rawQuery(
                            "SELECT DISTINCT AC.CHECKLIST, AC.CHECKLIST_ID,  AC.CHECKLIST_TYPE FROM MAPPING_ASSET_CHECKLIST MA INNER JOIN ASSET_CHECKLIST AC ON MA.CHECKLIST_ID = AC.CHECKLIST_ID WHERE MA.ASSET_CD= '" + asset_cd + "'"
                            , null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ChecklistInsertDataGetterSetter sb = new ChecklistInsertDataGetterSetter();


                    sb.setChecklist(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKLIST")));

                    sb.setChecklist_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKLIST_ID")));

                    sb.setChecklist_type(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKLIST_TYPE")));

                    sb.setChecklist_text("");


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching checklist data!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching checklist data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    //Get Checklist Insert data

    public ArrayList<ChecklistInsertDataGetterSetter> getCheckListInsertData(String asset_cd, String store_cd, String visitdate) {
        Log.d("Fetching checklist data--------------->Start<------------",
                "------------------");
        ArrayList<ChecklistInsertDataGetterSetter> list = new ArrayList<ChecklistInsertDataGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM ASSET_CHECKLIST_INSERT WHERE ASSET_CD = '" + asset_cd + "' AND STORE_CD = '" + store_cd + "' AND VISIT_DATE = '" + visitdate + "'"
                            , null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ChecklistInsertDataGetterSetter sb = new ChecklistInsertDataGetterSetter();


                    sb.setChecklist(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString1.CHECK_LIST)));

                    sb.setChecklist_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString1.CHECK_LIST_ID)));

                    sb.setChecklist_type(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString1.CHECK_LIST_TYPE)));

                    sb.setChecklist_text(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString1.CHECK_LIST_TEXT)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching checklist data!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching checklist data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    //dlete specific Checklist Insert data

    public void deleteCheckListInsertData(String asset_cd, String store_cd, String visitdate) {
        Log.d("Fetching checklist data--------------->Start<------------",
                "------------------");

        db.delete("ASSET_CHECKLIST_INSERT", "ASSET_CD = '" + asset_cd + "' AND STORE_CD = '" + store_cd + "' AND VISIT_DATE = '" + visitdate + "'", null);

    }


    //Get All Checklist Insert data


//Get mapping Checklist data

    public ArrayList<MappingAssetChecklistGetterSetter> getMapingCheckListData() {
        Log.d("Fetching mapping checklist data--------------->Start<------------",
                "------------------");
        ArrayList<MappingAssetChecklistGetterSetter> list = new ArrayList<MappingAssetChecklistGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM MAPPING_ASSET_CHECKLIST"
                            , null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingAssetChecklistGetterSetter sb = new MappingAssetChecklistGetterSetter();

                    sb.setCheck_list_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKLIST_ID")));

                    sb.setAsset_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET_CD")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching mapping checklist data!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("Fetching mapping checklist data---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    //Insert Asset Checklist Data

    public void insertAssetCheckListData(ArrayList<ChecklistInsertDataGetterSetter> data, String asset_cd, String visit_date, String store_cd) {

        db.delete(CommonString1.TABLE_ASSET_CHECKLIST_INSERT, "ASSET_CD" + "='" + asset_cd + "' AND STORE_CD" + "='" + store_cd + "'", null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.size(); i++) {

                values.put(CommonString1.CHECK_LIST_ID, data.get(i).getChecklist_id());
                values.put(CommonString1.KEY_STORE_CD, store_cd);
                values.put(CommonString1.ASSET_CD, asset_cd);
                values.put(CommonString1.KEY_VISIT_DATE, visit_date);
                values.put(CommonString1.CHECK_LIST_TEXT, data.get(i).getChecklist_text());
                values.put(CommonString1.CHECK_LIST_TYPE, data.get(i).getChecklist_type());
                values.put(CommonString1.CHECK_LIST, data.get(i).getChecklist());

                db.insert(CommonString1.TABLE_ASSET_CHECKLIST_INSERT, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Asset checklist insert Data ",
                    ex.toString());
        }

    }

    //----------------------------------------------------


    public ArrayList<GeotaggingBeans> getGeotaggingData(String STORE_ID) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<GeotaggingBeans> geodata = new ArrayList<GeotaggingBeans>();

        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT STORE_ID, LATITUDE, LONGITUDE, FRONT_IMAGE, GEO_TAG FROM STORE_GEOTAGGING WHERE STORE_ID= '" + STORE_ID + "' ", null);

            if (dbcursor != null) {
                int numrows = dbcursor.getCount();

                dbcursor.moveToFirst();
                for (int i = 1; i <= numrows; ++i) {

                    GeotaggingBeans data = new GeotaggingBeans();
                    data.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_ID")));
                    data.setLatitude(Double.parseDouble(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LATITUDE"))));
                    data.setLongitude(Double.parseDouble(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LONGITUDE"))));
                    data.setUrl1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("FRONT_IMAGE")));
                    data.setGEO_TAG(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GEO_TAG")));

                    //data.setUrl2(dbcursor.getString(4));
                    //data.setUrl3(dbcursor.getString(5));
                    geodata.add(data);
                    dbcursor.moveToNext();

                }

                dbcursor.close();

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.getMessage());
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return geodata;

    }


    public void updateOutTime(String status, String StoreId, String VisitDate) {

        try {
            ContentValues values = new ContentValues();


            values.put(CommonString1.KEY_GEO_TAG, status);


            db.update(CommonString1.TABLE_COVERAGE_DATA, values, CommonString1.KEY_STORE_CD + "='" + StoreId + "' AND "
                    + CommonString1.KEY_VISIT_DATE + "='" + VisitDate
                    + "'", null);
        } catch (Exception e) {

        }
    }

    @SuppressLint("LongLogTag")
    public void InsertStoregeotagging(String storeid, double lat, double longitude, String path, String status) {
        ContentValues values = new ContentValues();

        try {
            values.put("STORE_ID", storeid);
            values.put("LATITUDE", Double.toString(lat));
            values.put("LONGITUDE", Double.toString(longitude));
            values.put("FRONT_IMAGE", path);
            values.put("GEO_TAG", status);

            db.insert(CommonString.TABLE_STORE_GEOTAGGING, null, values);
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Closes Data ", ex.getMessage());
        }
    }


    //Gagan goel code start

    //POSM Category List
    public ArrayList<POSM_MASTER_DataGetterSetter> getPOSMCategoryData() {
        Log.d("FetchingCagtegorydata", "--------------->Start<------------");

        ArrayList<POSM_MASTER_DataGetterSetter> categoryData = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select DISTINCT PCATEGORY_CD, POSM_CATEGORY " +
                    "FROM POSM_MASTER", null);

            if (dbcursor != null) {
                int numrows = dbcursor.getCount();
                dbcursor.moveToFirst();

                for (int i = 1; i <= numrows; ++i) {
                    POSM_MASTER_DataGetterSetter data = new POSM_MASTER_DataGetterSetter();

                    data.setpCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PCATEGORY_CD")));
                    data.setPosm_category(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM_CATEGORY")));

                    categoryData.add(data);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching Records!!!!!!!!!!!!!!!!!!!!! " + e.getMessage());
        }

        Log.d("FetchingStoredat", "---------------------->Stop<-----------");
        return categoryData;
    }

    //POSM Tracking Default Data
    public ArrayList<POSM_MASTER_DataGetterSetter> getPOSMCategoryHeaderData(String pCategory_cd) {
        Log.d("Fetching", "POSM Tracking--------------->Start<------------");

        ArrayList<POSM_MASTER_DataGetterSetter> categoryData = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select DISTINCT PSUB_CATEGORY_CD,PSUB_CATEGORY " +
                    "from POSM_MASTER " +
                    "where  PCATEGORY_CD= '" + pCategory_cd + "'", null);

            if (dbcursor != null) {
                int numrows = dbcursor.getCount();
                dbcursor.moveToFirst();

                for (int i = 1; i <= numrows; ++i) {
                    POSM_MASTER_DataGetterSetter data = new POSM_MASTER_DataGetterSetter();

                    data.setpSub_Category_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PSUB_CATEGORY_CD")));
                    data.setpSub_Category(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PSUB_CATEGORY")));

                    categoryData.add(data);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching Records!!!!!!!!!!!!!!!!!!!!! " + e.getMessage());
        }

        Log.d("Fetching", "POSM Tracking---------------------->Stop<-----------");
        return categoryData;
    }


    //POSM Tracking Default Data
    public ArrayList<POSM_MASTER_DataGetterSetter> getPOSMCategoryHeaderWithCompetitorData(String pCategory_cd) {
        Log.d("Fetching", "POSM Tracking--------------->Start<------------");

        ArrayList<POSM_MASTER_DataGetterSetter> categoryData = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select DISTINCT PSUB_CATEGORY_CD,PSUB_CATEGORY " +
                    "from POSM_MASTER " +
                    "where  PCATEGORY_CD= '" + pCategory_cd + "' AND PSUB_CATEGORY LIKE '"+"%Competition%"+"'", null);


            if (dbcursor != null) {
                int numrows = dbcursor.getCount();
                dbcursor.moveToFirst();

                for (int i = 1; i <= numrows; ++i) {
                    POSM_MASTER_DataGetterSetter data = new POSM_MASTER_DataGetterSetter();

                    data.setpSub_Category_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PSUB_CATEGORY_CD")));
                    data.setpSub_Category(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PSUB_CATEGORY")));

                    categoryData.add(data);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching Records!!!!!!!!!!!!!!!!!!!!! " + e.getMessage());
        }

        Log.d("Fetching", "POSM Tracking---------------------->Stop<-----------");
        return categoryData;
    }


    public ArrayList<POSM_MASTER_DataGetterSetter> getPOSMCategoryChildData(String pCategory_cd, String pSub_Category_cd) {
        Log.d("FetchingCagtegorydata", "--------------->Start<------------");

        ArrayList<POSM_MASTER_DataGetterSetter> categoryData = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select POSM_CD,POSM,PSUB_CATEGORY_CD,PSUB_CATEGORY " +
                    "from POSM_MASTER " +
                    "where  PCATEGORY_CD= '" + pCategory_cd + "' and PSUB_CATEGORY_CD='" + pSub_Category_cd + "'", null);

            if (dbcursor != null) {
                int numrows = dbcursor.getCount();
                dbcursor.moveToFirst();

                for (int i = 1; i <= numrows; ++i) {
                    POSM_MASTER_DataGetterSetter data = new POSM_MASTER_DataGetterSetter();
                    data.setPosm_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM_CD")));
                    data.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM")));
                    data.setpSub_Category_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PSUB_CATEGORY_CD")));
                    data.setpSub_Category(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PSUB_CATEGORY")));
                    data.setOldValue("");
                    data.setNewValue("");

                    categoryData.add(data);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching Records!!!!!!!!!!!!!!!!!!!!! " + e.getMessage());
        }

        Log.d("FetchingStoredat", "---------------------->Stop<-----------");
        return categoryData;
    }

    public ArrayList<POSM_MASTER_DataGetterSetter> getPOSMCategoryData_AfterSaveData(
            String store_cd, String pCategory_cd, String pSub_Category) {

        ArrayList<POSM_MASTER_DataGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select STORE_CD,POSM_CD,POSM,PCATEGORY_CD,POSM_CATEGORY,PSUB_CATEGORY_CD,PSUB_CATEGORY," +
                    "case " +
                    "when OLD_VALUE = 0  THEN  ''" +
                    "Else OLD_VALUE " +
                    "END OLD_VALUE, " +
                    "case " +
                    "when NEW_VALUE = 0  THEN  '' " +
                    "Else NEW_VALUE " +
                    "END NEW_VALUE " +
                    "from " + CommonString1.TABLE_INSERT_POSM_TRACKING_DATA +
                    " where STORE_CD='" + store_cd + "' and PCATEGORY_CD= '" + pCategory_cd +
                    "' and PSUB_CATEGORY_CD='" + pSub_Category + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    POSM_MASTER_DataGetterSetter data = new POSM_MASTER_DataGetterSetter();

                    data.setStore_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    data.setPosm_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM_CD")));
                    data.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM")));
                    data.setpCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PCATEGORY_CD")));
                    data.setPosm_category(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM_CATEGORY")));
                    data.setpSub_Category_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PSUB_CATEGORY_CD")));
                    data.setpSub_Category(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PSUB_CATEGORY")));
                    data.setOldValue(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OLD_VALUE")));
                    data.setNewValue(dbcursor.getString(dbcursor.getColumnIndexOrThrow("NEW_VALUE")));

                    list.add(data);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "get MSL_Availability Sku After Save Data!" + e.toString());
            return list;
        }
        return list;
    }

    public void InsertPOSMCategoryData(String store_cd, String category_cd, List<POSM_MASTER_DataGetterSetter> hashMapListHeaderData,
                                       HashMap<POSM_MASTER_DataGetterSetter, List<POSM_MASTER_DataGetterSetter>> hashMapListChildData) {

        db.delete(CommonString1.TABLE_INSERT_POSM_TRACKING_DATA, "STORE_CD" + "='" + store_cd + "'AND PCATEGORY_CD ='" + category_cd + "'", null);

        ContentValues values = new ContentValues();
        try {
            db.beginTransaction();
            for (int i = 0; i < hashMapListHeaderData.size(); i++) {
                for (int j = 0; j < hashMapListChildData.get(hashMapListHeaderData.get(i)).size(); j++) {
                    POSM_MASTER_DataGetterSetter data = hashMapListChildData.get(hashMapListHeaderData.get(i)).get(j);
                    values.put("STORE_CD", store_cd);
                    values.put("POSM_CD", data.getPosm_cd());
                    values.put("POSM", data.getPosm());
                    values.put("PCATEGORY_CD", category_cd);
                    values.put("POSM_CATEGORY", hashMapListHeaderData.get(i).getPosm_category());
                    values.put("PSUB_CATEGORY_CD", data.getpSub_Category_cd());
                    values.put("PSUB_CATEGORY", data.getpSub_Category());
                    if (!data.getOldValue().equals("")) {
                        values.put("OLD_VALUE", data.getOldValue());
                    } else {
                        values.put("OLD_VALUE", "0");
                    }
                    if (!data.getNewValue().equals("")) {
                        values.put("NEW_VALUE", data.getNewValue());
                    } else {
                        values.put("NEW_VALUE", "0");
                    }
                    db.insert(CommonString1.TABLE_INSERT_POSM_TRACKING_DATA, null, values);
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Exception ", " in Insert POSM Tracking " + ex.toString());
        }
    }


    public boolean checkPOSMCategoryData(String store_cd, String pCategory_cd) {
        Log.d("POSM Tracking Data ", "Posm Tracking data--------------->Start<------------");

        ArrayList<POSM_MASTER_DataGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select * from " + CommonString1.TABLE_INSERT_POSM_TRACKING_DATA +
                    " where PCATEGORY_CD='" + pCategory_cd + "' and STORE_CD='" + store_cd + "'", null);

            if (dbcursor != null) {
                if (dbcursor.moveToFirst()) {
                    do {
                        POSM_MASTER_DataGetterSetter sb = new POSM_MASTER_DataGetterSetter();

                        sb.setpCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PCATEGORY_CD")));
                        list.add(sb);

                    } while (dbcursor.moveToNext());
                }
                dbcursor.close();

                return list.size() > 0;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return false;
        }

        Log.d("POSM Tracking ", "midday---------------------->Stop<-----------");
        return false;
    }

    public ArrayList<POSM_MASTER_DataGetterSetter> getPOSMCategoryData_UploadData(String store_cd) {

        ArrayList<POSM_MASTER_DataGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select * from " + CommonString1.TABLE_INSERT_POSM_TRACKING_DATA +
                    " where STORE_CD='" + store_cd + "' AND NEW_VALUE<>0 ", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    POSM_MASTER_DataGetterSetter data = new POSM_MASTER_DataGetterSetter();

                    data.setStore_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    data.setPosm_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM_CD")));
                    data.setPosm(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM")));
                    data.setpCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PCATEGORY_CD")));
                    data.setPosm_category(dbcursor.getString(dbcursor.getColumnIndexOrThrow("POSM_CATEGORY")));
                    data.setpSub_Category_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PSUB_CATEGORY_CD")));
                    data.setpSub_Category(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PSUB_CATEGORY")));
                    data.setOldValue(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OLD_VALUE")));
                    data.setNewValue(dbcursor.getString(dbcursor.getColumnIndexOrThrow("NEW_VALUE")));

                    list.add(data);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "get POSM TRACKING Upload Data!" + e.toString());
            return list;
        }
        return list;
    }

    //POSM Tracking images
    public void InsertStore_wise_camera(POSM_MASTER_DataGetterSetter data) {
        db.delete(CommonString1.TABLE_INSERT_STORE_CAMERA, "STORE_CD" + "='" + data.getStore_cd() + "' AND PCATEGORY_CD ='" + data.getpCategory_cd() + "'", null);
        ContentValues values = new ContentValues();
        try {
            values.put("STORE_CD", data.getStore_cd());
            values.put("PCATEGORY_CD", data.getpCategory_cd());
            values.put("IMAGE1", data.getImage1());
            values.put("IMAGE2", data.getImage2());
            values.put("IMAGE3", data.getImage3());
            values.put("CHECK_SAVE_STATUS", data.getCheckSaveStatus());

            db.insert(CommonString1.TABLE_INSERT_STORE_CAMERA, null, values);
        } catch (Exception ex) {
            Log.d("Exception ", " Store_wise_camera " + ex.toString());
        }
    }

    public POSM_MASTER_DataGetterSetter getStore_wise_camera(String store_cd, String pCategory_cd) {
        POSM_MASTER_DataGetterSetter data = new POSM_MASTER_DataGetterSetter();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select * from " + CommonString1.TABLE_INSERT_STORE_CAMERA +
                    " where STORE_CD='" + store_cd + "' and PCATEGORY_CD='" + pCategory_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    data.setStore_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    data.setpCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PCATEGORY_CD")));
                    data.setImage1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE1")));
                    data.setImage2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE2")));
                    data.setImage3(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE3")));
                    data.setCheckSaveStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECK_SAVE_STATUS")));

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return data;
            }
        } catch (Exception e) {
            Log.d("Exception ", "get Stock Facing Planogram server upload !" + e.toString());
            return data;
        }
        return data;
    }

    public boolean isStorewiseCameraSave(String store_cd, String pCategory_cd) {
        boolean filled = false;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select CHECK_SAVE_STATUS from " + CommonString1.TABLE_INSERT_STORE_CAMERA +
                    " where STORE_CD='" + store_cd + "' and PCATEGORY_CD='" + pCategory_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                String value = dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECK_SAVE_STATUS"));

                filled = value.equals("1");
                //dbcursor.close();
            }
        } catch (Exception e) {
            Log.d("Exception ", " when fetching Records!!!!!!!!!!!!!!!!!!!!! " + e.toString());
            return filled;
        }
        return filled;
    }

    public void updateStore_wise_camera(POSM_MASTER_DataGetterSetter data) {
        ContentValues values = new ContentValues();

        try {
            values.put("IMAGE1", data.getImage1());
            values.put("IMAGE2", data.getImage2());
            values.put("IMAGE3", data.getImage3());

            db.update(CommonString1.TABLE_INSERT_STORE_CAMERA, values,
                    " STORE_CD='" + data.getStore_cd() + "' and PCATEGORY_CD='" + data.getpCategory_cd() + "'", null);
        } catch (Exception ex) {
            Log.d("Exception ", " Store_wise_camera " + ex.toString());
        }
    }

    public ArrayList<POSM_MASTER_DataGetterSetter> getPOSMImage_UploadData(String store_cd) {
        ArrayList<POSM_MASTER_DataGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select * from " + CommonString1.TABLE_INSERT_STORE_CAMERA +
                    " where STORE_CD='" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    POSM_MASTER_DataGetterSetter data = new POSM_MASTER_DataGetterSetter();

                    data.setStore_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    data.setpCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PCATEGORY_CD")));
                    data.setImage1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE1")));
                    data.setImage2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE2")));
                    data.setImage3(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE3")));

                    list.add(data);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "get POSM TRACKING Upload Data!" + e.toString());
            return list;
        }
        return list;
    }

    public boolean isCheckPOSM_CategoryFill(String store_cd, ArrayList<POSM_MASTER_DataGetterSetter> categoryList, String date) {
        Log.d("POSM Tracking Data ", "Posm Tracking data--------------->Start<------------");
        CoverageBean coverage;
        ArrayList<POSM_MASTER_DataGetterSetter> headerDataList;
        Cursor dbcursor = null;
        boolean flag = true;

        try {
            for (int i = 0; i < categoryList.size(); i++) {

                coverage = getCoverageWhirlpoolSkuData(date,store_cd);

                if(coverage.getWhirlpool_sku().equalsIgnoreCase("1")){
                    headerDataList = getPOSMCategoryHeaderData(categoryList.get(i).getpCategory_cd());
                }else{
                    headerDataList = getPOSMCategoryHeaderWithCompetitorData(categoryList.get(i).getpCategory_cd());
                }

                if(headerDataList.size() >0){
                    dbcursor = db.rawQuery("Select * from Store_wise_camera " +
                            "where STORE_CD='" + store_cd +
                            "' and PCATEGORY_CD='" + categoryList.get(i).getpCategory_cd() + "'", null);

                    if (dbcursor != null) {
                        dbcursor.moveToFirst();
                        int icount = dbcursor.getCount();
                        dbcursor.close();

                        if (icount <= 0) {
                            flag = false;
                            break;
                        }
                    }
                }
            }

            if (flag) {
                return flag;
            } else {
                return flag;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return flag;
        }
    }

    //get JCP Data Filter
    public ArrayList<JourneyPlanGetterSetter> getJCP_Filter_SpinnerData(String date) {
        Log.d("Fetching", "Store Filter Data--------------->Start<------------");

        ArrayList<JourneyPlanGetterSetter> list = new ArrayList<JourneyPlanGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT CITY from JOURNEY_PLAN " +
                    "where VISIT_DATE = '" + date + "'", null);

            JourneyPlanGetterSetter sb1 = new JourneyPlanGetterSetter();
            sb1.setCity("Select");
            list.add(0, sb1);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    JourneyPlanGetterSetter sb = new JourneyPlanGetterSetter();
                    sb.setCity((dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY"))));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching JCP!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching", "JCP data---------------------->Stop<-----------");
        return list;
    }

    //get Filter JCP Data
    public ArrayList<JourneyPlanGetterSetter> getFilter_JCPData(String date, String city) {
        Log.d("Fetching", "Storedata--------------->Start<------------");

        ArrayList<JourneyPlanGetterSetter> list = new ArrayList<JourneyPlanGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from JOURNEY_PLAN " + "where VISIT_DATE = '" + date + "' and CITY ='" + city + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    JourneyPlanGetterSetter sb = new JourneyPlanGetterSetter();

                    sb.setStore_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    sb.setKey_account(dbcursor.getString(dbcursor.getColumnIndexOrThrow("KEYACCOUNT")));
                    sb.setStore_name((dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORENAME"))));
                    sb.setCity((dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY"))));
                    sb.setSTATE_CD((dbcursor.getString(dbcursor.getColumnIndexOrThrow("STATE_CD"))));
                    sb.setSTORETYPE_CD((dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORETYPE_CD"))));
                    sb.setUploadStatus((dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS"))));
                    sb.setCheckOutStatus((dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKOUT_STATUS"))));
                    sb.setVISIT_DATE((dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISIT_DATE"))));
                    sb.setGEO_TAG((dbcursor.getString(dbcursor.getColumnIndexOrThrow("GEO_TAG"))));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching JCP!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching", "JCP data---------------------->Stop<-----------");
        return list;
    }

    public String getCity_CoverageStatusData(String visitdate, String status) {
        Cursor dbcursor = null;
        String city = "";

        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString1.TABLE_COVERAGE_DATA +
                    " where " + CommonString1.KEY_VISIT_DATE + "='" + visitdate +
                    "' and " + CommonString1.KEY_COVERAGE_STATUS + "='" + status + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                city = dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString1.KEY_CITY));
                dbcursor.close();
                return city;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Coverage Data!!!!!!!!!!!!!!!!!!!!!" + e.toString());
        }
        return city;
    }


    public ArrayList<NonWorkingReasonGetterSetter> getNonWorkingData(int status) {
        Log.d("Fetching", "Assetdata--------------->Start<------------");
        ArrayList<NonWorkingReasonGetterSetter> list = new ArrayList<NonWorkingReasonGetterSetter>();
        Cursor dbcursor = null;

        try {

            if (status == 1) {
                dbcursor = db.rawQuery("SELECT * FROM NON_WORKING_REASON", null);
            } else {
                dbcursor = db.rawQuery("SELECT * FROM NON_WORKING_REASON " +
                        "where ENTRY_ALLOW <> 0", null);
            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    NonWorkingReasonGetterSetter sb = new NonWorkingReasonGetterSetter();

                    sb.setIMAGE_ALLOW(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_ALLOW")));
                    sb.setReason_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("REASON_CD")));
                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("REASON")));
                    sb.setEntry_allow(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ENTRY_ALLOW")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }
        Log.d("Fetching ", "non working data---------------------->Stop<-----------");
        return list;
    }

    public boolean isCheckUploadStatus() {
        boolean filled = true;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select UPLOAD_STATUS from JOURNEY_PLAN", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    JourneyPlanGetterSetter sb = new JourneyPlanGetterSetter();

                    String status = dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS"));
                    if (status.equals(CommonString1.KEY_D)) {
                        filled = false;
                        break;
                    }

                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }
        } catch (Exception e) {
            Log.d("Exception ", "Fecting upload status" + e.toString());
            return filled;
        }
        return filled;
    }

    public long InsertDeploymentFormData(String store_cd, String username, String visit_date, String img_str) {
        db.delete(CommonString1.TABLE_DEPLOYMENT_FORM, "STORE_CD" + "='" + store_cd + "'AND VISIT_DATE ='" + visit_date + "'", null);
        ContentValues values = new ContentValues();
        long l = 0;
        try {
            values.put("STORE_CD", store_cd);
            values.put("VISIT_DATE", visit_date);
            values.put("USER", username);
            values.put("DEPLOY_IMG", img_str);

            l = db.insert(CommonString1.TABLE_DEPLOYMENT_FORM, null, values);


        } catch (Exception ex) {
            Log.d("Database Exception while Insert CLOSING STOCK Data ",
                    ex.toString());
        }
        return l;
    }

    public boolean isDeploymentFormFilled(String storeId) {
        boolean filled = false;

        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DEPLOY_IMG FROM DEPLOYMENT_FORM WHERE STORE_CD= '" + storeId + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    if (dbcursor.getString(dbcursor.getColumnIndexOrThrow("DEPLOY_IMG")).equals("")) {
                        filled = false;
                        break;
                    } else {
                        filled = true;
                    }

                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return filled;
        }

        return filled;
    }

    public DeploymentFormGetterSetter getdeploymentInsertedData(String store_cd) {
        DeploymentFormGetterSetter df = new DeploymentFormGetterSetter();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT * from DEPLOYMENT_FORM WHERE STORE_CD= '" + store_cd + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    df.setDf_img(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DEPLOY_IMG")));
                    df.setStore_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    dbcursor.moveToNext();

                }

                dbcursor.close();
                return df;
            }
        } catch (Exception e) {
            return df;
        }
        return df;
    }

    public void insetDeploymenyData(DeploymentXmlGetterSetter data) {

        db.delete("DEPLOYMENT_STATUS", null, null);
        ContentValues values = new ContentValues();
        try {
            values.put("DEPLOY_STATUS_CD", "");
            values.put("DEPLOY_STATUS", "Select");
            values.put("WHIRLPOOL_SKU", "");
            db.insert("DEPLOYMENT_STATUS", null, values);
            for (int i = 0; i < data.getDEPLOY_STATUS_CD().size(); i++) {
                values.put("DEPLOY_STATUS_CD", Integer.parseInt(data.getDEPLOY_STATUS_CD().get(i)));
                values.put("DEPLOY_STATUS", data.getDEPLOY_STATUS().get(i));
                values.put("WHIRLPOOL_SKU", Integer.parseInt(data.getWHIRLPOOL_SKU().get(i)));
                db.insert("DEPLOYMENT_STATUS", null, values);
            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Deployment Data ",
                    ex.toString());
        }
    }

    public ArrayList<DeploymentXmlGetterSetter> getDeloymentData() {

        ArrayList<DeploymentXmlGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT * from DEPLOYMENT_STATUS", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    DeploymentXmlGetterSetter dx = new DeploymentXmlGetterSetter();
                    dx.setDEPLOY_STATUS_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DEPLOY_STATUS_CD")));
                    dx.setWHIRLPOOL_SKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow("WHIRLPOOL_SKU")));
                    dx.setDEPLOY_STATUS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DEPLOY_STATUS")));
                    list.add(dx);
                    dbcursor.moveToNext();

                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public long updateCoverageDeploymentStatus(String date, String store_cd, String selectedItemCd, String whirlpool_sku) {
        long l = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString1.KEY_DEPLOYMENT_CD, selectedItemCd);
            values.put(CommonString1.KEY_WHIRLPOOL_SKU, whirlpool_sku);
            l = db.update(CommonString1.TABLE_COVERAGE_DATA, values, CommonString1.KEY_STORE_ID + "='" + store_cd + "' AND " + CommonString1.KEY_VISIT_DATE + "='" + date + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
}
