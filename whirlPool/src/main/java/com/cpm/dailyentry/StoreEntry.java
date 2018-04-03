package com.cpm.dailyentry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString1;
import com.cpm.GetterSetter.NavMenuItemGetterSetter;
import com.cpm.whirlpool.R;
import com.cpm.database.GSKDatabase;
import com.cpm.geotag.LocationActivity;
import com.cpm.xmlGetterSetter.POSM_MASTER_DataGetterSetter;
import com.cpm.xmlGetterSetter.StockGetterSetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoreEntry extends AppCompatActivity implements OnClickListener {
    GSKDatabase db;
    private SharedPreferences preferences;
    String store_cd;
    boolean food_flag;
    String user_type = "", GEO_TAG = "",date;
    private ArrayList<StockGetterSetter> stockData = new ArrayList<StockGetterSetter>();
    ValueAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<POSM_MASTER_DataGetterSetter> categoryList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_item_recycle_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new GSKDatabase(getApplicationContext());
        db.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        store_cd = preferences.getString(CommonString1.KEY_STORE_CD, null);
        food_flag = preferences.getBoolean(CommonString1.KEY_FOOD_STORE, false);
        GEO_TAG = preferences.getString(CommonString1.KEY_GEO_TAG, null);
        user_type = preferences.getString(CommonString1.KEY_USER_TYPE, null);
        date = preferences.getString(CommonString1.KEY_DATE, null);
        intent = new Intent();
        intent.putExtra("cityItem", getIntent().getStringExtra("cityItem"));
        setTitle("Store Entry - " + date);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = (RecyclerView) findViewById(R.id.drawer_layout_recycle);
        adapter = new ValueAdapter(getApplicationContext(), getdata());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        validate2();
    }

    public void validate2() {
        boolean flag = true;
        categoryList = db.getPOSMCategoryData();
        if (categoryList.size() > 0) {
            if (!db.isCheckPOSM_CategoryFill(store_cd, categoryList, date)) {
                flag = false;
            }
            if (!db.isDeploymentFormFilled(store_cd)) {
                flag = false;
            }
        }
        if (flag) {
            db.updateCoverageStatus(Integer.parseInt(store_cd), CommonString1.KEY_VALID);
        }
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub

        int id = view.getId();

        switch (id) {
            case R.id.openingstock:
                if (!db.isClosingDataFilled(store_cd)) {
                    Intent in1 = new Intent(getApplicationContext(), PosmActivity.class);
                    startActivity(in1);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                } else {
                    Toast.makeText(getApplicationContext(), "Data cannot be changed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.closingstock:
                stockData = db.getOpeningStock(store_cd);
                if ((stockData.size() <= 0) || (stockData.get(0).getOpen_stock_cold_room() == null) || (stockData.get(0).getOpen_stock_cold_room().equals(""))) {
                    Toast.makeText(getApplicationContext(), "First Fill Opening Stock and Midday Stock Data", Toast.LENGTH_SHORT).show();
                } else {
                    stockData = db.getMiddayStock(store_cd);
                    if ((stockData.size() <= 0) || (stockData.get(0).getMidday_stock() == null) || (stockData.get(0).getMidday_stock().equals(""))) {
                        Toast.makeText(getApplicationContext(), "First Fill Opening Stock and Midday Stock Data", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent in2 = new Intent(getApplicationContext(), ClosingStock.class);
                        startActivity(in2);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                    }
                }


                break;


            case R.id.midstock:

                if (!db.isClosingDataFilled(store_cd)) {

                    Intent in3 = new Intent(getApplicationContext(), MidDayStock.class);

                    startActivity(in3);

                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                } else {
                    Toast.makeText(getApplicationContext(), "Data cannot be changed", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.prommotion:

                Intent in4 = new Intent(getApplicationContext(), WindowsActivity.class);

                startActivity(in4);

                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                break;

            case R.id.assets:

                Intent in5 = new Intent(getApplicationContext(), AssetActivity.class);

                startActivity(in5);

                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                break;

            case R.id.foodstore:

                Intent in6 = new Intent(getApplicationContext(), FoodStore.class);

                startActivity(in6);

                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                break;

            case R.id.facingcompetitor:

                Intent in7 = new Intent(getApplicationContext(), FacingCompetitor.class);

                startActivity(in7);

                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                break;

            case R.id.calls:

                Intent in8 = new Intent(getApplicationContext(), CallsActivity.class);

                startActivity(in8);

                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                break;


            case R.id.performance:

                Intent startPerformance = new Intent(StoreEntry.this, Performance.class);
                startActivity(startPerformance);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }

    }

    public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        List<NavMenuItemGetterSetter> data = Collections.emptyList();

        public ValueAdapter(Context context, List<NavMenuItemGetterSetter> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public ValueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.custom_row, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ValueAdapter.MyViewHolder viewHolder, final int position) {
            final NavMenuItemGetterSetter current = data.get(position);
            viewHolder.icon.setImageResource(current.getIconImg());
            viewHolder.label.setText(current.getIconName());
            viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (current.getIconImg() == R.drawable.posm || current.getIconImg() == R.drawable.posm_done) {
                        categoryList = db.getPOSMCategoryData();
                        if (!db.isCheckPOSM_CategoryFill(store_cd, categoryList, date)) {
                            Intent in1 = new Intent(StoreEntry.this, POSMCategoryListActivity.class);
                            startActivity(in1);
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        } else {
                            Snackbar.make(recyclerView, "Data cannot be changed", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    if (current.getIconImg() == R.drawable.midday_stock || current.getIconImg() == R.drawable.midday_stock_done) {

                        if (!db.isClosingDataFilled(store_cd)) {
                            Intent in3 = new Intent(getApplicationContext(), MidDayStock.class);
                            startActivity(in3);
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        } else {
                            Snackbar.make(recyclerView, "Data cannot be changed", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    if (current.getIconImg() == R.drawable.windows || current.getIconImg() == R.drawable.window_done) {
                        Intent in4 = new Intent(getApplicationContext(), WindowsActivity.class);
                        startActivity(in4);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }
                    if (current.getIconImg() == R.drawable.asset || current.getIconImg() == R.drawable.asset_done) {
                        Intent in5 = new Intent(getApplicationContext(), AssetActivity.class);
                        startActivity(in5);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }
                    if (current.getIconImg() == R.drawable.gps || current.getIconImg() == R.drawable.gps_done) {
                        if (GEO_TAG.equalsIgnoreCase(CommonString1.KEY_GEO_Y)) {
                            Snackbar.make(recyclerView, "GoeTag Already Done", Snackbar.LENGTH_LONG).show();

                        } else if (db.getGeotaggingData(store_cd).size() > 0) {
                            if (db.getGeotaggingData(store_cd).get(0).getGEO_TAG().equalsIgnoreCase(CommonString1.KEY_GEO_Y)) {
                                Snackbar.make(recyclerView, "GoeTag Already Done", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Intent in4 = new Intent(getApplicationContext(), LocationActivity.class);
                            startActivity(in4);
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        }
                    }
                    if (current.getIconImg() == R.drawable.closing_stock || current.getIconImg() == R.drawable.closing_stock_done) {
                        if (db.isOpeningDataFilled(store_cd)) {
                            if (db.isMiddayDataFilled(store_cd)) {
                                Intent in2 = new Intent(getApplicationContext(), ClosingStock.class);
                                startActivity(in2);
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                            } else {
                                Snackbar.make(recyclerView, "First fill Midday Stock Data", Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(recyclerView, "First fill Opening Stock data", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    if (current.getIconImg() == R.drawable.competition || current.getIconImg() == R.drawable.competition_done) {
                        Intent in7 = new Intent(getApplicationContext(), CompetionMenuActivity.class);
                        startActivity(in7);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }

                    if (current.getIconImg() == R.drawable.depform || current.getIconImg() == R.drawable.depform_done) {
                        startActivity(new Intent(StoreEntry.this, DeploymentFormActvity.class));
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView label;

            public MyViewHolder(View itemView) {
                super(itemView);
                icon = (ImageView) itemView.findViewById(R.id.list_icon);
                label = (TextView) itemView.findViewById(R.id.list_txt);
            }
        }
    }

    public List<NavMenuItemGetterSetter> getdata() {
        List<NavMenuItemGetterSetter> data = new ArrayList<>();
        int Stock, middayImg, closingImg, windows, assetImg, competitionImg, geotag, deploymentForm;

        if (db.isDeploymentFormFilled(store_cd)) {
            deploymentForm = R.drawable.depform_done;
        } else {
            deploymentForm = R.drawable.depform;
        }

        if (db.isClosingDataFilled(store_cd)) {
            closingImg = R.drawable.closing_stock_done;
        } else {
            closingImg = R.drawable.closing_stock;
        }
        if (db.isMiddayDataFilled(store_cd)) {
            middayImg = R.drawable.midday_stock_done;
        } else {
            middayImg = R.drawable.midday_stock;
        }
        categoryList = db.getPOSMCategoryData();

        if (db.isCheckPOSM_CategoryFill(store_cd, categoryList,date)) {
            Stock = R.drawable.posm_done;
        } else {
            Stock = R.drawable.posm;
        }

        if (db.isAssetDataFilled(store_cd)) {
            assetImg = R.drawable.asset_done;
        } else {
            assetImg = R.drawable.asset;
        }

        if (db.isPromotionDataFilled(store_cd)) {
            windows = R.drawable.window_done;
        } else {
            windows = R.drawable.windows;
        }
        if (db.getFacingCompetitorData(store_cd).size() > 0
                && db.getCompetitionPOIData(store_cd).size() > 0
                && db.getwindowsData(store_cd).size() > 0) {
            competitionImg = R.drawable.competition_done;
        } else {
            competitionImg = R.drawable.competition;

        }

        if (db.getGeotaggingData(store_cd).size() > 0) {
            if (db.getGeotaggingData(store_cd).get(0).getGEO_TAG().equalsIgnoreCase(CommonString1.KEY_GEO_Y)) {
                geotag = R.drawable.gps_done;
            } else {
                geotag = R.drawable.gps;
            }
        } else {
            if (GEO_TAG.equalsIgnoreCase(CommonString1.KEY_GEO_Y)) {
                geotag = R.drawable.gps_done;
            } else {
                geotag = R.drawable.gps;
            }
        }
        if (user_type.equals("Promoter")) {
            int img[] = {Stock, middayImg, windows, assetImg, closingImg, competitionImg};
            for (int i = 0; i < img.length; i++) {
                NavMenuItemGetterSetter recData = new NavMenuItemGetterSetter();
                recData.setIconImg(img[i]);
                data.add(recData);
            }
        } else if (user_type.equals("Merchandiser")) {
            int img[] = {Stock, geotag, deploymentForm};
            String text[] = {"POSM", "Geo Tag", "Deployment form"};
            for (int i = 0; i < img.length; i++) {
                NavMenuItemGetterSetter recData = new NavMenuItemGetterSetter();
                recData.setIconImg(img[i]);
                recData.setIconName(text[i]);
                data.add(recData);
            }
        }

        return data;
    }

    @Override
    public void onBackPressed() {
        setResult(10, intent);
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
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
            setResult(10, intent);
            finish();
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
        return super.onOptionsItemSelected(item);
    }

}
