package com.cpm.upload;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cpm.Constants.CommonString1;
import com.cpm.dailyentry.DailyEntryScreen;
import com.cpm.database.GSKDatabase;
import com.cpm.delegates.CoverageBean;
import com.cpm.message.AlertMessage;
import com.cpm.whirlpool.MainMenuActivity;
import com.cpm.whirlpool.R;
import com.cpm.xmlGetterSetter.JourneyPlanGetterSetter;

import java.util.ArrayList;

public class UploadOptionActivity extends AppCompatActivity implements View.OnClickListener {
    private String date;
    private SharedPreferences preferences;
    private static GSKDatabase database;
    ArrayList<CoverageBean> cdata = new ArrayList<CoverageBean>();
    JourneyPlanGetterSetter storestatus = new JourneyPlanGetterSetter();
    Button btn_upload_data, btn_upload_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_option);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_upload_data = (Button) findViewById(R.id.btn_upload_data);
        btn_upload_image = (Button) findViewById(R.id.btn_upload_image);
        btn_upload_data.setOnClickListener(this);
        btn_upload_image.setOnClickListener(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString1.KEY_DATE, null);
        database = new GSKDatabase(this);
        database.open();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean validate_data() {
        boolean result = false;
        database.open();
        cdata = database.getCoverageData(date);
        for (int i = 0; i < cdata.size(); i++) {
            storestatus = database.getStoreStatus(cdata.get(i).getStoreId());
            if (!storestatus.getUploadStatus().get(0).equalsIgnoreCase(CommonString1.KEY_D)) {
                if ((storestatus.getCheckOutStatus().get(0).equalsIgnoreCase(
                        CommonString1.KEY_C)
                        || storestatus.getUploadStatus().get(0).equalsIgnoreCase(
                        CommonString1.KEY_P) || storestatus.getUploadStatus().get(0)
                        .equalsIgnoreCase(CommonString1.STORE_STATUS_LEAVE))) {
                    result = true;
                    break;

                }
            }
        }

        return result;
    }

    public boolean validate() {
        boolean result = false;
        database.open();
        cdata = database.getCoverageData(date);
        for (int i = 0; i < cdata.size(); i++) {
            if (cdata.get(i).getStatus().equalsIgnoreCase(CommonString1.KEY_D)) {
                result = true;
                break;
            }
        }

        return result;
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        database.close();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_upload_data) {
            cdata = database.getCoverageData(date);
            if (cdata.size() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Parinaam");
                builder.setMessage(AlertMessage.MESSAGE_NO_DATA).setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(UploadOptionActivity.this, MainMenuActivity.class);
                                startActivity(i);
                                finish();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                if ((validate_data())) {
                    Intent i = new Intent(getBaseContext(), UploadDataActivity.class);
                    i.putExtra("UploadAll", false);
                    startActivity(i);
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Parinaam");
                    builder.setMessage(AlertMessage.MESSAGE_NO_DATA).setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(UploadOptionActivity.this, DailyEntryScreen.class);
                                    startActivity(i);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        } else if (id == R.id.btn_upload_image) {
            cdata = database.getCoverageData(date);
            if (cdata.size() == 0) {
                Toast.makeText(getBaseContext(), AlertMessage.MESSAGE_NO_IMAGE, Toast.LENGTH_LONG).show();
            } else {
                if (validate()) {
                    Intent i = new Intent(getBaseContext(), UploadAllImageActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), AlertMessage.MESSAGE_DATA_FIRST, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent i = new Intent(this, MainMenuActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }

        return super.onOptionsItemSelected(item);
    }
}
