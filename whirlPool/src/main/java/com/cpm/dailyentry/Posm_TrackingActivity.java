package com.cpm.dailyentry;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonFunctions;
import com.cpm.Constants.CommonString1;
import com.cpm.database.GSKDatabase;
import com.cpm.delegates.CoverageBean;
import com.cpm.whirlpool.R;
import com.cpm.xmlGetterSetter.POSM_MASTER_DataGetterSetter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Posm_TrackingActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ArrayList<POSM_MASTER_DataGetterSetter> headerDataList;
    ArrayList<POSM_MASTER_DataGetterSetter> childDataList;
    List<POSM_MASTER_DataGetterSetter> hashMapListHeaderData;
    HashMap<POSM_MASTER_DataGetterSetter, List<POSM_MASTER_DataGetterSetter>> hashMapListChildData;
    List<Integer> checkHeaderArray = new ArrayList<>();
    boolean checkflag = true;
    ExpandableListAdapter adapter;
    ImageView camera1, camera2, camera3;
    LinearLayout lin_camera1, lin_camera2, lin_camera3;
    String path = "", str = "", _pathforcheck = "", img1 = "", img2 = "", img3 = "";
    Uri outputFileUri = null;
    String gallery_package = "";
    GSKDatabase db;
    String posm_category, category_cd, Error_Message = "";
    String store_cd, visit_date, username;
    private SharedPreferences preferences;
    POSM_MASTER_DataGetterSetter cameraData;
    CoverageBean coverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_posm_tracking);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
            camera1 = (ImageView) findViewById(R.id.img_camera1);
            camera2 = (ImageView) findViewById(R.id.img_camera2);
            camera3 = (ImageView) findViewById(R.id.img_camera3);
            lin_camera1 = (LinearLayout) findViewById(R.id.lin_camera1);
            lin_camera2 = (LinearLayout) findViewById(R.id.lin_camera2);
            lin_camera3 = (LinearLayout) findViewById(R.id.lin_camera3);
            db = new GSKDatabase(this);
            db.open();
            //preference data
            preferences = PreferenceManager.getDefaultSharedPreferences(this);
            store_cd = preferences.getString(CommonString1.KEY_STORE_CD, null);
            visit_date = preferences.getString(CommonString1.KEY_DATE, null);
            //Intent data
            category_cd = getIntent().getStringExtra("category_cd");
            posm_category = getIntent().getStringExtra("posm_category");
            toolbar.setTitle(getResources().getString(R.string.title_activity_category_tracking) + " - " + posm_category);
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            prepareList();
            str = CommonString1.FILE_PATH + _pathforcheck;

            cameraMethod();

            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    expandableListView.clearFocus();
                    if (validateData(hashMapListHeaderData, hashMapListChildData, cameraData)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Posm_TrackingActivity.this);
                        builder.setMessage(getResources().getString(R.string.check_save_message))
                                .setCancelable(false)
                                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        db.open();
                                        cameraData.setCheckSaveStatus("1");
                                        db.InsertStore_wise_camera(cameraData);
                                        db.InsertPOSMCategoryData(store_cd, category_cd, hashMapListHeaderData, hashMapListChildData);
                                        Snackbar.make(view, getResources().getString(R.string.save_message), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                        // }
                                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                        finish();
                                    }
                                })
                                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Posm_TrackingActivity.this);
                        builder.setMessage(Error_Message)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            });

            expandableListView.clearFocus();

            expandableListView.setOnScrollListener(
                    new AbsListView.OnScrollListener() {
                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem,
                                             int visibleItemCount, int totalItemCount) {
                            int lastItem = firstVisibleItem + visibleItemCount;

                            if (firstVisibleItem == 0) {
                                fab.setVisibility(View.VISIBLE);
                            } else if (lastItem == totalItemCount) {
                                fab.setVisibility(View.INVISIBLE);
                            } else {
                                fab.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onScrollStateChanged(AbsListView arg0, int arg1) {
                            InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (getCurrentFocus() != null) {
                                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                getCurrentFocus().clearFocus();
                            }
                        }
                    }
            );
            // Listview Group click listener
            expandableListView.setOnGroupClickListener(
                    new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            return false;
                        }
                    }

            );
            // Listview Group expanded listener
            expandableListView.setOnGroupExpandListener(
                    new ExpandableListView.OnGroupExpandListener() {
                        @Override
                        public void onGroupExpand(int groupPosition) {
                            InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (getWindow().getCurrentFocus() != null) {
                                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                getCurrentFocus().clearFocus();
                            }
                        }
                    }

            );

            // Listview Group collasped listener
            expandableListView.setOnGroupCollapseListener(
                    new ExpandableListView.OnGroupCollapseListener() {
                        @Override
                        public void onGroupCollapse(int groupPosition) {
                            InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (getWindow().getCurrentFocus() != null) {
                                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                getCurrentFocus().clearFocus();
                            }
                        }
                    }

            );

            // Listview on child click listener
            expandableListView.setOnChildClickListener(
                    new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                                    int childPosition, long id) {
                            return false;
                        }
                    }
            );

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void cameraMethod() {
        cameraData = new POSM_MASTER_DataGetterSetter();
        if (db.isStorewiseCameraSave(store_cd, category_cd)) {
            cameraData = db.getStore_wise_camera(store_cd, category_cd);
        } else {
            cameraData.setStore_cd(store_cd);
            cameraData.setpCategory_cd(category_cd);
            cameraData.setImage1("");
            cameraData.setImage2("");
            cameraData.setImage3("");
            cameraData.setCheckSaveStatus("0");
        }


        if (cameraData.getImage1().equals("")) {
            camera1.setBackgroundResource(R.mipmap.camera_orange);
        } else {
            camera1.setBackgroundResource(R.mipmap.camera_green);
        }

        if (cameraData.getImage2().equals("")) {
            camera2.setBackgroundResource(R.mipmap.camera_orange);
        } else {
            camera2.setBackgroundResource(R.mipmap.camera_green);
        }
        if (cameraData.getImage3().equals("")) {
            camera3.setBackgroundResource(R.mipmap.camera_orange);
        } else {
            camera3.setBackgroundResource(R.mipmap.camera_green);
        }

        lin_camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _pathforcheck = store_cd + "_Stock_Camera1_" + category_cd + "_" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                path = str + _pathforcheck;
                CommonFunctions.startCameraActivity1(Posm_TrackingActivity.this, path, 1);
            }
        });
        lin_camera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _pathforcheck = store_cd + "_Stock_Camera2_" + category_cd + "_" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                path = str + _pathforcheck;
                CommonFunctions.startCameraActivity1(Posm_TrackingActivity.this, path, 2);
            }
        });
        lin_camera3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _pathforcheck = store_cd + "_Stock_Camera3_" + category_cd + "_" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                path = str + _pathforcheck;
                CommonFunctions.startCameraActivity1(Posm_TrackingActivity.this, path, 3);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void prepareList() {
        try {
            hashMapListHeaderData = new ArrayList<>();
            hashMapListChildData = new HashMap<>();
            //Header
            coverage = db.getCoverageWhirlpoolSkuData(visit_date,store_cd);

            // here whirlpool_sku used for decided to show competition data
            // if whirlpool_sku = 1 then  show competition data
            // if whirlpool_sku = 0 then  hide competition data

            if(coverage.getWhirlpool_sku().equalsIgnoreCase("1")){
                headerDataList = db.getPOSMCategoryHeaderData(category_cd);
            }else{
                headerDataList = db.getPOSMCategoryHeaderWithCompetitorData(category_cd);
            }

            if (headerDataList.size() > 0) {
                for (int i = 0; i < headerDataList.size(); i++) {
                    hashMapListHeaderData.add(headerDataList.get(i));
                    //childDataList = new ArrayList<>();
                    childDataList = db.getPOSMCategoryData_AfterSaveData(store_cd, category_cd, headerDataList.get(i).getpSub_Category_cd());
                    if (!(childDataList.size() > 0)) {
                        childDataList = db.getPOSMCategoryChildData(category_cd, headerDataList.get(i).getpSub_Category_cd());
                    }
                    hashMapListChildData.put(hashMapListHeaderData.get(i), childDataList);
                }
            }
            adapter = new ExpandableListAdapter(this, hashMapListHeaderData, hashMapListChildData);
            expandableListView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean validateData(List<POSM_MASTER_DataGetterSetter> listDataHeader,
                         HashMap<POSM_MASTER_DataGetterSetter, List<POSM_MASTER_DataGetterSetter>> listDataChild,
                         POSM_MASTER_DataGetterSetter cameraData) {

        boolean flag = true;
        checkHeaderArray.clear();

        if (cameraData.getImage1().equals("") && cameraData.getImage2().equals("") && cameraData.getImage3().equals("")) {
            flag = false;
            Error_Message = "Please click all camera image";
        } else if (cameraData.getImage1().equals("")) {
            flag = false;
            Error_Message = "Please click camera 1 image";
        } else if (cameraData.getImage2().equals("")) {
            flag = false;
            Error_Message = "Please click camera 2 image";
        } else if (cameraData.getImage3().equals("")) {
            flag = false;
            Error_Message = "Please click camera 3 image";
        }
        checkflag = flag != false;
        adapter.notifyDataSetChanged();
        return checkflag;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Posm_TrackingActivity.this);
            builder.setTitle(getResources().getString(R.string.dialog_title));
            builder.setMessage(getResources().getString(R.string.data_will_be_lost)).setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                            finish();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(Posm_TrackingActivity.this);
        builder.setTitle(getResources().getString(R.string.dialog_title));
        builder.setMessage(getResources().getString(R.string.data_will_be_lost)).setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<POSM_MASTER_DataGetterSetter> _listDataHeader;
        private HashMap<POSM_MASTER_DataGetterSetter, List<POSM_MASTER_DataGetterSetter>> _listDataChild;

        public ExpandableListAdapter(Context context, List<POSM_MASTER_DataGetterSetter> listDataHeader, HashMap<POSM_MASTER_DataGetterSetter, List<POSM_MASTER_DataGetterSetter>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            POSM_MASTER_DataGetterSetter headerTitle = (POSM_MASTER_DataGetterSetter) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.item_posm_tracking_header, null, false);
            }
            TextView txt_categoryHeader = (TextView) convertView.findViewById(R.id.txt_categoryHeader);
            txt_categoryHeader.setTypeface(null, Typeface.BOLD);
            txt_categoryHeader.setText(headerTitle.getpSub_Category());
            return convertView;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {

            final POSM_MASTER_DataGetterSetter childData = (POSM_MASTER_DataGetterSetter) getChild(groupPosition, childPosition);
            ViewHolder holder = null;

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.item_posm_tracking_child, null, false);

                holder = new ViewHolder();
                holder.cardView = (CardView) convertView.findViewById(R.id.card_view);
                holder.lin_category = (LinearLayout) convertView.findViewById(R.id.lin_category);
                holder.txt_posmName = (TextView) convertView.findViewById(R.id.txt_posmName);
                // holder.ed_old = (EditText) convertView.findViewById(R.id.ed_old);
                holder.ed_new = (EditText) convertView.findViewById(R.id.ed_new);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txt_posmName.setText(childData.getPosm() + " - " + childData.getpSub_Category());

            holder.ed_new.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    final EditText caption = (EditText) v;
                    final String ed_New = caption.getText().toString().replaceFirst("^0+(?!$)", "");

                    if (!ed_New.equals("")) {
                        String newValue = ed_New.replaceFirst("^0+(?!$)", "");

                        childData.setNewValue(newValue);
                    } else {
                        childData.setNewValue("");
                    }

                }
            });
            childData.setOldValue("0");
            holder.ed_new.setText(childData.getNewValue());
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public class ViewHolder {
        CardView cardView;
        TextView txt_posmName;
        LinearLayout lin_category;
        EditText /*ed_old,*/ ed_new;
    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());

        return cdate;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("POSM Tracking", "resultCode: " + resultCode);

        switch (requestCode) {
            case 1:
                if (resultCode == -1) {
                    if (_pathforcheck != null && !_pathforcheck.equals("")) {
                        if (new File(str + _pathforcheck).exists()) {
                            img1 = _pathforcheck;
                            adapter.notifyDataSetChanged();
                            _pathforcheck = "";
                            if (!img1.equalsIgnoreCase("")) {
                                cameraData.setImage1(img1);
                                img1 = "";
                            }
                            if (cameraData.getImage1().equals("")) {
                                camera1.setBackgroundResource(R.mipmap.camera_orange);
                            } else {
                                camera1.setBackgroundResource(R.mipmap.camera_green);
                            }
                        }
                    }
                } else {
                    Log.e("POSM Tracking", "User cancelled Image 1");
                }
                break;

            case 2:
                if (resultCode == -1) {
                    if (_pathforcheck != null && !_pathforcheck.equals("")) {
                        if (new File(str + _pathforcheck).exists()) {
                            img2 = _pathforcheck;
                            adapter.notifyDataSetChanged();
                            _pathforcheck = "";

                            if (!img2.equalsIgnoreCase("")) {
                                cameraData.setImage2(img2);
                                img2 = "";
                            }

                            if (cameraData.getImage2().equals("")) {
                                camera2.setBackgroundResource(R.mipmap.camera_orange);
                            } else {
                                camera2.setBackgroundResource(R.mipmap.camera_green);
                            }
                        }
                    }
                } else {
                    Log.e("POSM Tracking", "User cancelled Image 2");
                }
                break;

            case 3:
                if (resultCode == -1) {
                    if (_pathforcheck != null && !_pathforcheck.equals("")) {
                        if (new File(str + _pathforcheck).exists()) {
                            img3 = _pathforcheck;
                            _pathforcheck = "";

                            if (!img3.equalsIgnoreCase("")) {
                                cameraData.setImage3(img3);
                                img3 = "";
                            }
                            if (cameraData.getImage3().equals("")) {
                                camera3.setBackgroundResource(R.mipmap.camera_orange);
                            } else {
                                camera3.setBackgroundResource(R.mipmap.camera_green);
                            }
                        }
                    }
                } else {
                    Log.e("POSM Tracking", "User cancelled Image 3");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
