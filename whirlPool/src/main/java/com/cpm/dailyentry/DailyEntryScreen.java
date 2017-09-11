package com.cpm.dailyentry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.cpm.Constants.CommonString1;
import com.cpm.database.GSKDatabase;
import com.cpm.delegates.CoverageBean;
import com.cpm.whirlpool.R;
import com.cpm.xmlGetterSetter.JourneyPlanGetterSetter;
import com.cpm.xmlGetterSetter.MappingPromotionGetterSetter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyEntryScreen extends AppCompatActivity implements OnItemClickListener, LocationListener {
    GSKDatabase database;
    ArrayList<JourneyPlanGetterSetter> jcplist = new ArrayList<>();
    ArrayList<JourneyPlanGetterSetter> jcpFilterData;
    ArrayList<CoverageBean> coverage;
    private SharedPreferences preferences;
    private String date, store_intime, store_id;
    ListView lv;
    String store_cd;
    private SharedPreferences.Editor editor = null;
    private Dialog dialog;
    public static String currLatitude = "0.0";
    public static String currLongitude = "0.0";
    String user_type;
    boolean result_flag = false;
    LinearLayout parent_linear, nodata_linear, city_spin_layout;
    Spinner sp_filter;
    MyAdapter myAdapter;
    String cityItem = "", city = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storelistlayout);
        lv = (ListView) findViewById(R.id.list);
        nodata_linear = (LinearLayout) findViewById(R.id.no_data_lay);
        parent_linear = (LinearLayout) findViewById(R.id.parent_linear);
        city_spin_layout = (LinearLayout) findViewById(R.id.city_spin_layout);
        sp_filter = (Spinner) findViewById(R.id.sp_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        database = new GSKDatabase(this);
        database.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString1.KEY_DATE, null);
        store_intime = preferences.getString(CommonString1.KEY_STORE_IN_TIME, "");
        store_id = preferences.getString(CommonString1.KEY_STORE_CD, "");
        user_type = preferences.getString(CommonString1.KEY_USER_TYPE, null);
        editor = preferences.edit();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);
        //Spinner filter city
        jcpFilterData = database.getJCP_Filter_SpinnerData(date);
        ArrayAdapter<String> sp_cityFilterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for (int i = 0; i < jcpFilterData.size(); i++) {
            sp_cityFilterAdapter.add(jcpFilterData.get(i).getCity().get(0));
        }
        sp_filter.setAdapter(sp_cityFilterAdapter);
        sp_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityItem = parent.getItemAtPosition(position).toString();

                if (jcplist != null) {
                    jcplist.clear();

                    if (!cityItem.equals("Select")) {
                        jcplist = database.getFilter_JCPData(date, cityItem);
                    } else {
                        jcplist = database.getJCPData(date);
                    }

                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //check user visit the store or come back from the image activity without save
        if (!cityItem.equals("")) {
            city = cityItem;
        } else {
            city = database.getCity_CoverageStatusData(date, CommonString1.KEY_CHECK_IN);
        }

        if (!city.equals("")) {

            // check user visit the store
            if (!database.getCity_CoverageStatusData(date, CommonString1.KEY_CHECK_IN).equals("")) {
                sp_filter.setEnabled(false);
            }

            if (jcplist != null) {
                jcplist.clear();

                for (int i = 0; i < jcpFilterData.size(); i++) {
                    if (jcpFilterData.get(i).getCity().get(0).equals(city)) {
                        sp_filter.setSelection(i);
                        break;
                    }
                }

                if (city.equals("Select")) {
                    jcplist = database.getJCPData(date);
                } else {
                    jcplist = database.getFilter_JCPData(date, city);
                }
                myAdapter.notifyDataSetChanged();
            }

        } else {
            sp_filter.setEnabled(true);
            jcplist = database.getJCPData(date);
            myAdapter.notifyDataSetChanged();

        }
        coverage = database.getCoverageData(date);
        if (jcplist.size() > 0) {
            setCheckOutData();
        } else {
            city_spin_layout.setVisibility(View.GONE);
            lv.setVisibility(View.GONE);
            parent_linear.setBackgroundColor((getResources().getColor(R.color.grey_light)));
            nodata_linear.setVisibility(View.VISIBLE);
        }
        lv.setOnItemClickListener(this);
    }

    public void setCheckOutData() {

        for (int i = 0; i < jcplist.size(); i++) {
            String storeCd = jcplist.get(i).getStore_cd().get(0);
            //String FIRST_VISIT=jcplist.get(i).getFIRST_VISIT().get(0);
            String state_cd = jcplist.get(i).getSTATE_CD().get(0);

            if (!jcplist.get(i).getCheckOutStatus().get(0)
                    .equals(CommonString1.KEY_C) && !jcplist.get(i).getCheckOutStatus().get(0)
                    .equals(CommonString1.KEY_VALID)) {

                if (database.isOpeningDataFilled(storeCd) && database.getwindowsData(storeCd).size() > 0) {

                    boolean flag = true;
////////?????????
                    //getCompetitionPromotionData

                    if (flag) {
                        database.updateStoreStatusOnCheckout(storeCd, date, CommonString1.KEY_VALID);
                        jcplist = database.getJCPData(date);
                        sp_filter.setEnabled(true);
                    }

                }


            }

        }

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return jcplist.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.storelistrow, null);

                holder.storename = (TextView) convertView.findViewById(R.id.tvstorename);
                holder.city = (TextView) convertView.findViewById(R.id.tvcity);
                holder.keyaccount = (TextView) convertView.findViewById(R.id.tvkeyaccount);
                holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.checkout = (Button) convertView.findViewById(R.id.chkout);
                holder.checkinclose = (ImageView) convertView.findViewById(R.id.closechkin);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.checkout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DailyEntryScreen.this);
                    builder.setMessage("Are you sure you want to Checkout")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (CheckNetAvailability()) {
                                        Intent i = new Intent(DailyEntryScreen.this, CheckOutStoreActivity.class);
                                        i.putExtra(CommonString1.KEY_STORE_CD,jcplist.get(position).getStore_cd().get(0));
                                        startActivity(i);
                                    } else {
                                        Snackbar.make(lv, "No Network", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            String storecd = jcplist.get(position).getStore_cd().get(0);
            ArrayList<CoverageBean> coverageBean = database.getCoverageSpecificData(storecd);

            if (jcplist.get(position).getUploadStatus().get(0).equals(CommonString1.KEY_D)) {
                holder.img.setVisibility(View.VISIBLE);
                holder.img.setBackgroundResource(R.drawable.store_uploaded);
                holder.checkout.setVisibility(View.INVISIBLE);
                holder.checkinclose.setVisibility(View.INVISIBLE);
            } else if (preferences.getString(CommonString1.KEY_STOREVISITED_STATUS + storecd, "").equals("No")) {
                holder.img.setBackgroundResource(R.drawable.leave_tick);
                holder.checkout.setVisibility(View.INVISIBLE);
                holder.checkinclose.setVisibility(View.INVISIBLE);
            } else if ((jcplist.get(position).getCheckOutStatus().get(0).equals(CommonString1.KEY_C))) {
                holder.img.setVisibility(View.INVISIBLE);
                holder.checkinclose.setBackgroundResource(R.drawable.store_checkout);
                holder.checkinclose.setVisibility(View.VISIBLE);
                holder.checkout.setVisibility(View.INVISIBLE);
                //Gagan Comment Start here
            } else if (coverageBean.size() > 0 && (coverageBean.get(0).getStatus().equalsIgnoreCase(CommonString1.STORE_STATUS_LEAVE))) {
                holder.img.setBackgroundResource(R.drawable.store_leave);
                holder.checkout.setVisibility(View.INVISIBLE);
                holder.checkinclose.setVisibility(View.INVISIBLE);
            } else if (coverageBean.size() > 0 && (coverageBean.get(0).getStatus().equalsIgnoreCase(CommonString1.KEY_CHECK_IN))) {
                holder.img.setBackgroundResource(R.drawable.store_check_in);
                holder.checkout.setVisibility(View.INVISIBLE);
                holder.checkinclose.setVisibility(View.INVISIBLE);
            } else if (coverageBean.size() > 0 && (coverageBean.get(0).getStatus().equalsIgnoreCase(CommonString1.KEY_VALID))) {
                holder.checkout.setBackgroundResource(R.drawable.checkout);
                holder.checkout.setVisibility(View.VISIBLE);
                holder.checkout.setEnabled(true);
            } else if (coverageBean.size() > 0 && (coverageBean.get(0).getStatus().equalsIgnoreCase(CommonString1.KEY_INVALID))) {
                if (coverage.size() > 0) {
                    int i;
                    for (i = 0; i < coverage.size(); i++) {
                        if (coverage.get(i).getInTime() != null) {
                            if (coverage.get(i).getOutTime() == null) {
                                if (storecd.equals(coverage.get(i).getStoreId())) {
                                    holder.img.setVisibility(View.INVISIBLE);
                                    holder.checkout.setEnabled(false);
                                    holder.checkout.setVisibility(View.INVISIBLE);
                                    holder.checkinclose.setBackgroundResource(R.drawable.store_check_in);
                                    holder.checkinclose.setVisibility(View.VISIBLE);
                                }
                                break;
                            }
                        }
                    }
                }
            }
            //Gagan Comment End here
            else {
                holder.checkout.setEnabled(false);
                holder.checkout.setVisibility(View.INVISIBLE);
                holder.img.setVisibility(View.VISIBLE);
                holder.img.setBackgroundResource(R.drawable.store);
                holder.checkinclose.setEnabled(false);
                holder.checkinclose.setVisibility(View.INVISIBLE);
            }
            holder.storename.setText(jcplist.get(position).getStore_name().get(0));
            holder.city.setText(jcplist.get(position).getCity().get(0));
            holder.keyaccount.setText(jcplist.get(position).getKey_account().get(0));


            return convertView;
        }

        private class ViewHolder {
            TextView storename, city, keyaccount;
            ImageView img, checkinclose;

            Button checkout;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        store_cd = jcplist.get(position).getStore_cd().get(0);
        final String upload_status = jcplist.get(position).getUploadStatus().get(0);
        final String checkoutstatus = jcplist.get(position).getCheckOutStatus().get(0);
        final String STORETYPE_CD = jcplist.get(position).getSTORETYPE_CD().get(0);
        final String STATE_CD = jcplist.get(position).getSTATE_CD().get(0);
        final String GeoTag = jcplist.get(position).getGEO_TAG().get(0);
        editor = preferences.edit();
        editor.putString(CommonString1.KEY_GEO_TAG, GeoTag);
        editor.putString(CommonString1.KEY_STORE_TYPE_CD, STORETYPE_CD);
        editor.putString(CommonString1.KEY_STATE_CD, STATE_CD);
        editor.commit();
        if (upload_status.equals(CommonString1.KEY_D)) {
            Snackbar.make(lv, "All Data Uploaded", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        } else if (((checkoutstatus.equals(CommonString1.KEY_C)))) {
            Snackbar.make(lv, "Store already checked out", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        } else if (preferences.getString(CommonString1.KEY_STOREVISITED_STATUS + store_cd, "").equals("No")) {
            Snackbar.make(lv, "Store Already Closed", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

        } else {
            if (!setcheckedmenthod(store_cd)) {
                boolean enteryflag = true;

                if (coverage.size() > 0) {
                    int i;
                    for (i = 0; i < coverage.size(); i++) {
                        if (coverage.get(i).getInTime() != null) {

                            if (coverage.get(i).getOutTime() == null) {
                                if (!store_cd.equals(coverage.get(i).getStoreId())) {
                                    Snackbar.make(lv, "Please checkout from current store", Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                    enteryflag = false;
                                }
                                break;
                            }
                        }
                    }
                }

                if (enteryflag) {
                    showMyDialog(store_cd, jcplist.get(position).getStore_name().get(0), "Yes",
                            jcplist.get(position).getVISIT_DATE().get(0),
                            jcplist.get(position).getCheckOutStatus().get(0));
                }
            } else {
                Snackbar.make(lv, "Data already filled ", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        }


    }


    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());


        return cdate;

    }

    public boolean CheckNetAvailability() {

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            // we are connected to a network
            connected = true;
        }
        return connected;
    }

    void showMyDialog(final String storeCd, final String storeName, final String status,
                      final String visitDate, final String checkout_status) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogbox);
        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radiogrpvisit);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.yes) {
                    editor = preferences.edit();
                    editor.putString(CommonString1.KEY_STOREVISITED, storeCd);
                    editor.putString(CommonString1.KEY_STOREVISITED_STATUS, "Yes");
                    editor.putString(CommonString1.KEY_LATITUDE, currLatitude);
                    editor.putString(CommonString1.KEY_LONGITUDE, currLongitude);
                    editor.putString(CommonString1.KEY_STORE_NAME, storeName);
                    editor.putString(CommonString1.KEY_STORE_CD, storeCd);
                    database.updateStoreStatusOnCheckout(storeCd, date, CommonString1.KEY_INVALID);
                    editor.commit();
                    if (store_intime.equalsIgnoreCase("")) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(CommonString1.KEY_STORE_IN_TIME, getCurrentTime());
                        editor.putString(CommonString1.KEY_STOREVISITED_STATUS, "Yes");
                        editor.commit();
                    }
                    dialog.cancel();


                    boolean flag = true;

                    if (coverage.size() > 0) {
                        for (int i = 0; i < coverage.size(); i++) {
                            if (store_cd.equals(coverage.get(i).getStoreId())) {
                                flag = false;
                                break;
                            }
                        }
                    }

                    if (flag == true) {
                        Intent in = new Intent(DailyEntryScreen.this, StoreimageActivity.class);
                        in.putExtra("cityItem", cityItem);
                        startActivityForResult(in, 1);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    } else {
                        Intent in = new Intent(DailyEntryScreen.this, StoreEntry.class);
                        in.putExtra("cityItem", cityItem);
                        startActivityForResult(in, 1);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }
                } else if (checkedId == R.id.no) {
                    dialog.cancel();
                    if (checkout_status.equals(CommonString1.KEY_INVALID) || checkout_status.equals(CommonString1.KEY_VALID)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DailyEntryScreen.this);
                        builder.setMessage(CommonString1.DATA_DELETE_ALERT_MESSAGE)
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        UpdateData(storeCd);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString(CommonString1.KEY_STORE_CD, storeCd);
                                        editor.putString(CommonString1.KEY_STOREVISITED, "");
                                        editor.putString(CommonString1.KEY_STOREVISITED_STATUS, "");
                                        editor.commit();

                                        Intent in = new Intent(DailyEntryScreen.this, NonWorkingReason.class);
                                        startActivity(in);

                                        sp_filter.setEnabled(true);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        UpdateData(storeCd);

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(CommonString1.KEY_STORE_CD, storeCd);
                        editor.putString(CommonString1.KEY_STOREVISITED, "");
                        editor.putString(CommonString1.KEY_STOREVISITED_STATUS, "");
                        editor.commit();
                        Intent in = new Intent(DailyEntryScreen.this, NonWorkingReason.class);
                        startActivity(in);
                    }
                }
            }

        });


        dialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        currLatitude = Double.toString(location.getLatitude());
        currLongitude = Double.toString(location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }

    public void UpdateData(String storeCd) {
        database.open();
        database.deleteSpecificStoreData(storeCd);
        database.updateStoreStatusOnCheckout(storeCd, jcplist.get(0).getVISIT_DATE().get(0), "N");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

        }

        return super.onOptionsItemSelected(item);
    }

    public boolean setcheckedmenthod(String store_cd) {


        for (int i = 0; i < coverage.size(); i++) {


            if (store_cd.equals(coverage.get(i).getStoreId())) {

                if (coverage.get(i).getOutTime() != null) {
                    result_flag = true;

                    break;
                }
            } else {
                result_flag = false;

            }


        }


        return result_flag;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 10) {
            if (!data.getStringExtra("cityItem").equals("Select")) {
                cityItem = data.getStringExtra("cityItem");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
