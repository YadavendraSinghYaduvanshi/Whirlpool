package com.cpm.dailyentry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpm.Constants.CommonString1;
import com.cpm.database.GSKDatabase;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.StoreBean;
import com.cpm.whirlpool.R;
import com.cpm.xmlGetterSetter.JourneyPlanGetterSetter;
import com.cpm.xmlGetterSetter.NonWorkingReasonGetterSetter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class NonWorkingReason extends AppCompatActivity implements
        OnItemSelectedListener, OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    ArrayList<NonWorkingReasonGetterSetter> reasondata = new ArrayList<NonWorkingReasonGetterSetter>();
    private Spinner reasonspinner;
    private GSKDatabase database;
    String reasonname, reasonid, entry_allow, image, reason_reamrk, intime, image_allow;
    Button save;
    private ArrayAdapter<CharSequence> reason_adapter;
    protected String _path, str;
    protected String _pathforcheck = "";
    private String image1 = "";
    private SharedPreferences preferences;
    String _UserId, visit_date, store_id;
    protected boolean status = true;
    EditText text;
    AlertDialog alert;
    ImageButton camera;
    RelativeLayout reason_lay, rel_cam;
    ArrayList<JourneyPlanGetterSetter> jcp;
    GoogleApiClient mGoogleApiClient;
    String lat = "0.0", lon = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonworking);
        reasonspinner = (Spinner) findViewById(R.id.spinner2);
        camera = (ImageButton) findViewById(R.id.imgcam);
        save = (Button) findViewById(R.id.save);
        text = (EditText) findViewById(R.id.reasontxt);
        reason_lay = (RelativeLayout) findViewById(R.id.layout_reason);
        rel_cam = (RelativeLayout) findViewById(R.id.relimgcam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _UserId = preferences.getString(CommonString1.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString1.KEY_DATE, null);
        store_id = preferences.getString(CommonString1.KEY_STORE_CD, "");
        database = new GSKDatabase(this);
        database.open();
        str = CommonString1.FILE_PATH;
        if (database.isCheckUploadStatus()) {
            reasondata = database.getNonWorkingData(1);
        } else {
            reasondata = database.getNonWorkingData(0);
        }
        intime = getCurrentTime();
        camera.setOnClickListener(this);
        save.setOnClickListener(this);
        reason_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        reason_adapter.add("Select Reason");
        for (int i = 0; i < reasondata.size(); i++) {
            reason_adapter.add(reasondata.get(i).getReason().get(0));
        }
        reasonspinner.setAdapter(reason_adapter);
        reason_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reasonspinner.setOnItemSelectedListener(this);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
        switch (arg0.getId()) {

            case R.id.spinner2:
                if (position != 0) {
                    reasonname = reasondata.get(position - 1).getReason().get(0);
                    reasonid = reasondata.get(position - 1).getReason_cd().get(0);
                    entry_allow = reasondata.get(position - 1).getEntry_allow().get(0);
                    image_allow = reasondata.get(position - 1).getIMAGE_ALLOW().get(0);
                    if (image_allow.equalsIgnoreCase("1")) {
                        rel_cam.setVisibility(View.VISIBLE);
                        image = "true";
                    } else {
                        rel_cam.setVisibility(View.GONE);
                        image = "false";
                    }
                    reason_reamrk = "true";
                    if (reason_reamrk.equalsIgnoreCase("true")) {
                        reason_lay.setVisibility(View.VISIBLE);
                    } else {
                        reason_lay.setVisibility(View.GONE);
                    }
                } else {
                    reasonname = "";
                    reasonid = "";
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    protected void startCameraActivity() {
        try {
            Log.i("MakeMachine", "startCameraActivity()");
            File file = new File(_path);
            Uri outputFileUri = Uri.fromFile(file);
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);

        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;

            case -1:
                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(str + _pathforcheck).exists()) {
                        camera.setImageDrawable(getResources().getDrawable(R.mipmap.camera_green));
                        image1 = _pathforcheck;
                    }
                }
                break;

        }

    }

    public boolean imageAllowed() {
        boolean result = true;

        if (image.equalsIgnoreCase("true")) {

            if (image1.equalsIgnoreCase("")) {
                result = false;
            }
        }
        return result;
    }

    public boolean textAllowed() {
        boolean result = true;
        if (text.getText().toString().trim().equals("")) {
            result = false;
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgcam) {
            _pathforcheck = store_id + "_NONWORKING_" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
            _path = CommonString1.FILE_PATH + _pathforcheck;
            startCameraActivity();
        }
        if (v.getId() == R.id.save) {
            if (validatedata()) {
                if (imageAllowed()) {
                    if (textAllowed()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NonWorkingReason.this);
                        builder.setMessage("Do you want to save the data ")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int id) {
                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                        if (entry_allow.equals("0")) {
                                            database.deleteAllTables();
                                            jcp = database.getJCPData(visit_date);
                                            for (int i = 0; i < jcp.size(); i++) {
                                                String stoteid = jcp.get(i).getStore_cd().get(0);

                                                CoverageBean cdata = new CoverageBean();
                                                cdata.setStoreId(stoteid);
                                                cdata.setVisitDate(visit_date);
                                                cdata.setUserId(_UserId);
                                                cdata.setInTime(intime);
                                                cdata.setOutTime(getCurrentTime());
                                                cdata.setReason(reasonname);
                                                cdata.setReasonid(reasonid);
                                                cdata.setLatitude(lat);
                                                cdata.setLongitude(lon);
                                                cdata.setImage(image1);
                                                cdata.setRemark(text.getText().toString().replaceAll("[&^<>{}'$]", " "));
                                                cdata.setStatus(CommonString1.STORE_STATUS_LEAVE);
                                                database.InsertCoverageData(cdata);
                                                database.updateStoreStatusOnLeave(store_id, visit_date, CommonString1.STORE_STATUS_LEAVE);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString(CommonString1.KEY_STOREVISITED_STATUS + stoteid, "No");
                                                editor.putString(CommonString1.KEY_STOREVISITED_STATUS, "");
                                                editor.commit();
                                            }
                                        } else {

                                            CoverageBean cdata = new CoverageBean();
                                            cdata.setStoreId(store_id);
                                            cdata.setVisitDate(visit_date);
                                            cdata.setUserId(_UserId);
                                            cdata.setInTime(intime);
                                            cdata.setOutTime(getCurrentTime());
                                            cdata.setReason(reasonname);
                                            cdata.setReasonid(reasonid);
                                            cdata.setLatitude(lat);
                                            cdata.setLongitude(lon);
                                            cdata.setImage(image1);
                                            cdata.setRemark(text.getText().toString().replaceAll("[&^<>{}'$]", " "));
                                            cdata.setStatus(CommonString1.STORE_STATUS_LEAVE);
                                            database.InsertCoverageData(cdata);
                                            database.updateStoreStatusOnLeave(store_id, visit_date, CommonString1.STORE_STATUS_LEAVE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString(CommonString1.KEY_STOREVISITED_STATUS + store_id, "No");
                                            editor.putString(CommonString1.KEY_STOREVISITED_STATUS, "");
                                            editor.commit();
                                        }
                                        finish();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int id) {
                                        dialog.cancel();
                                    }
                                });

                        alert = builder.create();
                        alert.show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter required remark reason", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Capture Image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Please Select a Reason", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean validatedata() {
        boolean result = false;
        if (reasonid != null && !reasonid.equalsIgnoreCase("")) {
            result = true;
        }
        return result;
    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());
        return cdate;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // NavUtils.navigateUpFromSameTask(this);
            finish();
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
