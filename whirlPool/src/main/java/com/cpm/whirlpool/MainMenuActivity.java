package com.cpm.whirlpool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString;
import com.cpm.Constants.CommonString1;
import com.cpm.dailyentry.DailyEntryScreen;
import com.cpm.database.GSKDatabase;
import com.cpm.delegates.CoverageBean;
import com.cpm.download.CompleteDownloadActivity;
import com.cpm.fragment.HelpFragment;
import com.cpm.fragment.MainFragment;
import com.cpm.message.AlertMessage;
import com.cpm.retrofit.PostApi;
import com.cpm.retrofit.StringConverterFactory;
import com.cpm.upload.CheckoutNUpload;
import com.cpm.upload.UploadDataActivity;
import com.cpm.upload.UploadOptionActivity;
import com.cpm.xmlGetterSetter.JourneyPlanGetterSetter;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GSKDatabase database;
    ArrayList<JourneyPlanGetterSetter> jcplist;
    private SharedPreferences preferences = null;
    private String date, user_name, user_type;
    TextView tv_username, tv_usertype;
    FrameLayout frameLayout;
    NavigationView navigationView;
    String result = "";
    boolean isvalid = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        user_name = preferences.getString(CommonString1.KEY_USERNAME, null);
        user_type = preferences.getString(CommonString1.KEY_USER_TYPE, null);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main_menu2, navigationView, false);
        navigationView.addHeaderView(headerView);
        tv_username = (TextView) headerView.findViewById(R.id.nav_user_name);
        tv_usertype = (TextView) headerView.findViewById(R.id.nav_user_type);
        tv_username.setText(user_name);
        tv_usertype.setText(user_type);
        navigationView.setNavigationItemSelectedListener(this);
        database = new GSKDatabase(this);
        database.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString1.KEY_DATE, null);
        setTitle("Main Menu - " + date);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
        // Handle action bar item clicks here. The action bar will
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_daily) {
            // Handle the camera action

            Intent startDownload = new Intent(this, DailyEntryScreen.class);
            startActivity(startDownload);

            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        } else if (id == R.id.nav_download) {
            if (checkNetIsAvailable()) {
                if (database.isCoverageDataFilled(date)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Parinaam");
                    builder.setMessage("Please Upload Previous Data First")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent startUpload = new Intent(MainMenuActivity.this, CheckoutNUpload.class);
                                    startActivity(startUpload);
                                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                    finish();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    try {
                        database.open();
                        database.deletePreviousUploadedData(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent startDownload = new Intent(getApplicationContext(), CompleteDownloadActivity.class);
                    startActivity(startDownload);
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                    finish();
                }
            } else {
                Snackbar.make(frameLayout, "No Network Available", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }

        } else if (id == R.id.nav_upload) {
            if (checkNetIsAvailable()) {
                jcplist = database.getJCPData(date);
                ArrayList<CoverageBean> cdata = new ArrayList<CoverageBean>();
                cdata = database.getCoverageData(date);
                boolean flag = true;
                if (jcplist.size() == 0) {
                    Snackbar.make(frameLayout, "Please Download Data First", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                } else {
                    if (cdata.size() > 0) {
                        for (int i = 0; i < cdata.size(); i++) {
                            if (cdata.get(i).getStatus().equals(CommonString1.KEY_CHECK_IN)) {
                                Snackbar.make(frameLayout, "First checkout of store",
                                        Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            if ((validate_data())) {
                                Intent i = new Intent(getBaseContext(), UploadDataActivity.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                finish();
                            } else {
                                Snackbar.make(frameLayout, AlertMessage.MESSAGE_NO_DATA, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }
                        }
                    } else {
                        Snackbar.make(frameLayout, AlertMessage.MESSAGE_NO_DATA, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    }
                }
            } else {
                Snackbar.make(frameLayout, "No Network Available", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }

        } else if (id == R.id.nav_exit) {
            Intent startDownload = new Intent(this, LoginActivity.class);
            startActivity(startDownload);
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            finish();

        } else if (id == R.id.nav_help) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            HelpFragment cartfrag = new HelpFragment();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, cartfrag).commit();
        } else if (id == R.id.nav_export_database) {
            backupDB();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean checkNetIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragment cartfrag = new MainFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, cartfrag).commit();
    }


    public boolean validate_data() {
        boolean result = false;
        database.open();
        ArrayList<CoverageBean> cdata = new ArrayList<CoverageBean>();
        JourneyPlanGetterSetter storestatus = new JourneyPlanGetterSetter();
        cdata = database.getCoverageData(date);
        if (cdata.size() > 0) {
            for (int i = 0; i < cdata.size(); i++) {
                storestatus = database.getStoreStatus(cdata.get(i).getStoreId());
                if (!storestatus.getUploadStatus().get(0).equalsIgnoreCase(CommonString1.KEY_U)) {
                    if ((storestatus.getCheckOutStatus().get(0).equalsIgnoreCase(CommonString1.KEY_C)
                            || storestatus.getUploadStatus().get(0).equalsIgnoreCase(CommonString1.KEY_P) ||
                            cdata.get(i).getStatus().equalsIgnoreCase(CommonString1.STORE_STATUS_LEAVE)) ||
                            storestatus.getUploadStatus().get(0).equalsIgnoreCase(CommonString1.KEY_D)) {
                        result = true;
                        break;

                    }
                }
            }
        }

        return result;
    }

    private void backupDB() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(
                MainMenuActivity.this);
        builder1.setMessage(
                "Are you sure you want to take the backup of your data")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @SuppressWarnings("resource")
                    public void onClick(DialogInterface dialog, int id) {

                        try {
                            File file = new File(Environment.getExternalStorageDirectory(), "WHIRLPOOL_backup");
                            if (!file.isDirectory()) {
                                file.mkdir();
                            }
                            File sd = Environment.getExternalStorageDirectory();
                            File data = Environment.getDataDirectory();
                            if (sd.canWrite()) {
                                String currentDBPath = "//data//com.cpm.whirlpool//databases//" + GSKDatabase.DATABASE_NAME;
                                String backupDBPath = "WHIRLPOOL_backup_" + user_name + "_" + date.replace("/", "") + "_"
                                        + getCurrentTime().replace(":", "") + ".db";
                                File currentDB = new File(data, currentDBPath);
                                File backupDB = new File("/mnt/sdcard/WHIRLPOOL_backup/", backupDBPath);
                                if (currentDB.exists()) {
                                    FileChannel src = new FileInputStream(currentDB).getChannel();
                                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                    dst.transferFrom(src, 0, src.size());
                                    src.close();
                                    dst.close();
                                }
                            }
                          /*  File dir = new File(CommonString1.BACKUP_PATH);
                            ArrayList<String> list = new ArrayList();
                            list = getFileNames(dir.listFiles());

                            if (list.size() > 0) {
                                for (int i1 = 0; i1 < list.size(); i1++) {
                                    if (list.get(i1).contains("WHIRLPOOL_backup")) {
                                        File originalFile = new File(CommonString1.BACKUP_PATH + list.get(i1));
                                        Object result = uploadBackup(MainMenuActivity.this, originalFile.getName(), "dbbackup");
                                        if (!result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {

                                        }
                                    }
                                }
                            }*/

                            Snackbar.make(frameLayout, "Database Exported And Uploaded Successfully", Snackbar.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert1 = builder1.create();
        alert1.show();
    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());
        return cdate;
    }

    public ArrayList<String> getFileNames(File[] file) {
        ArrayList<String> arrayFiles = new ArrayList<String>();
        if (file.length > 0) {
            for (int i = 0; i < file.length; i++)
                arrayFiles.add(file[i].getName());
        }
        return arrayFiles;
    }

    private String uploadBackup(final Context context, String file_name, String folder_name) {
        RequestBody body1;
        result = "";
        isvalid = false;
        final File originalFile = new File(CommonString1.BACKUP_PATH + file_name);
        RequestBody photo = RequestBody.create(MediaType.parse("application/octet-stream"), originalFile);
        body1 = new MultipartBuilder().type(MultipartBuilder.FORM)
                .addFormDataPart("file", originalFile.getName(), photo)
                .addFormDataPart("FolderName", folder_name)
                .build();
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(CommonString1.URL + "/")
                .addConverterFactory(new StringConverterFactory())
                .build();
        PostApi api = adapter.create(PostApi.class);
        Call<String> call = api.getUploadImage(body1);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response) {
                if (response.toString() != null) {
                    if (response.body().contains(CommonString1.KEY_SUCCESS)) {
                        isvalid = true;
                        result = CommonString1.KEY_SUCCESS;
                        originalFile.delete();
                    } else {
                        result = "Servererror!";
                    }
                } else {
                    result = "Servererror!";
                }
            }

            @Override
            public void onFailure(Throwable t) {
                isvalid = true;
                if (t instanceof UnknownHostException) {
                    result = AlertMessage.MESSAGE_SOCKETEXCEPTION;
                } else {
                    result = AlertMessage.MESSAGE_SOCKETEXCEPTION;
                }
                Toast.makeText(context, originalFile.getName() + " not uploaded", Toast.LENGTH_SHORT).show();
            }
        });

        return result;
    }
}
