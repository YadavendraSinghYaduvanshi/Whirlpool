package com.cpm.dailyentry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.cpm.Constants.CommonString1;
import com.cpm.database.GSKDatabase;
import com.cpm.delegates.CoverageBean;
import com.cpm.whirlpool.R;
import com.cpm.xmlGetterSetter.DeploymentXmlGetterSetter;
import java.util.ArrayList;

public class DeploymentActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private GSKDatabase database;
    private FloatingActionButton btnNext;
    private SharedPreferences preferences;
    Toolbar toolbar;
    Spinner spinner;
    AlertDialog alert;
    String selectedItemCd,flag,date,store_cd;
    ArrayList<CoverageBean> coverage;
    ArrayList<DeploymentXmlGetterSetter> deploymentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployment);

        toolbar = (Toolbar) findViewById(R.id.deploy_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Deployment Status");

        spinner = (Spinner) findViewById(R.id.sp_deploy);
        btnNext = (FloatingActionButton)findViewById(R.id.deploy_fab);
        btnNext.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString1.KEY_DATE, null);
        store_cd = preferences.getString(CommonString1.KEY_STORE_CD, null);

        database = new GSKDatabase(this);
        database.open();
        // getting deployment data from database
        deploymentList = database.getDeloymentData();

        CustomeAdapter customeAdapter = new CustomeAdapter(this,deploymentList);
        spinner.setAdapter(customeAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedItemCd = deploymentList.get(i).getDEPLOY_STATUS_CD().get(0).toString();
        flag =  deploymentList.get(i).getWHIRLPOOL_SKU().get(0).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if(selectedItemCd.equalsIgnoreCase("")){
            Toast.makeText(this, "Please Select Deployment Status", Toast.LENGTH_SHORT).show();
        }else{


            AlertDialog.Builder builder = new AlertDialog.Builder(DeploymentActivity.this);
            builder.setMessage("Do you want to save the data ?")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            CoverageBean cdata = new CoverageBean();
                            database.open();
                            // To retrieve object in second Activity
                            cdata = (CoverageBean) getIntent().getSerializableExtra("coverageObj");
                            database.InsertCoverageData(cdata);
                            database.updateCoverageDeploymentStatus(date,store_cd,selectedItemCd,flag);

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(CommonString1.KEY_STOREVISITED_STATUS, "");
                            editor.commit();
                            Intent intent = new Intent(DeploymentActivity.this,StoreEntry.class);
                            intent.putExtra("cityItem", getIntent().getStringExtra("cityItem"));
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

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
        }
    }


    //  creating customer adapter for spinner list
    private class CustomeAdapter extends BaseAdapter{
        private ArrayList<DeploymentXmlGetterSetter> deploymentList;
        Context context;
        public CustomeAdapter(DeploymentActivity context, ArrayList<DeploymentXmlGetterSetter> deploymentList) {
            this.deploymentList = deploymentList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return deploymentList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.txt_spinner,null);
            TextView textView = (TextView) view.findViewById(R.id.txt_spinner);
            textView.setText(deploymentList.get(i).getDEPLOY_STATUS().get(0).toString());
            return view;
        }
    }

}
