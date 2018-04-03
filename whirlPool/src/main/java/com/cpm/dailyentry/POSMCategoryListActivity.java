package com.cpm.dailyentry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpm.Constants.CommonString1;
import com.cpm.whirlpool.R;
import com.cpm.database.GSKDatabase;
import com.cpm.xmlGetterSetter.POSM_MASTER_DataGetterSetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class POSMCategoryListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<POSM_MASTER_DataGetterSetter> categoryList;
    CategoryListAdapter adapter;
    GSKDatabase db;
    String store_id, visit_date, username;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        db = new GSKDatabase(this);
        db.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        store_id = preferences.getString(CommonString1.KEY_STORE_CD, null);
        visit_date = preferences.getString(CommonString1.KEY_DATE, null);
        toolbar.setTitle(getResources().getString(R.string.title_activity_category_list) +" - " + visit_date);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_category);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        categoryList = new ArrayList<>();
        categoryList = db.getPOSMCategoryData();

        if (categoryList.size() > 0) {

            adapter = new CategoryListAdapter(POSMCategoryListActivity.this, categoryList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {
        List<POSM_MASTER_DataGetterSetter> list = Collections.emptyList();
        Context context;
        private LayoutInflater inflator;
        public CategoryListAdapter(Context context, List<POSM_MASTER_DataGetterSetter> list) {
            inflator = LayoutInflater.from(context);
            this.list = list;
            this.context = context;
        }

        @Override
        public CategoryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.category_menu_row, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final POSM_MASTER_DataGetterSetter data = list.get(position);
            holder.categoryName.setText(data.getPosm_category());
            if (db.checkPOSMCategoryData(store_id, data.getpCategory_cd())) {
                holder.img_done.setVisibility(View.VISIBLE);
                holder.img_done.setImageResource(R.drawable.tickgreenv);
            } else {
                holder.img_done.setVisibility(View.INVISIBLE);
            }
            holder.lay_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(POSMCategoryListActivity.this, Posm_TrackingActivity.class);
                    intent.putExtra("category_cd", data.getpCategory_cd());
                    intent.putExtra("posm_category", data.getPosm_category());
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView categoryName;
            ImageView img_done;
            LinearLayout lay_menu;

            public MyViewHolder(View itemView) {
                super(itemView);
                categoryName = (TextView) itemView.findViewById(R.id.categoryName);
                img_done = (ImageView) itemView.findViewById(R.id.img_done);
                lay_menu = (LinearLayout) itemView.findViewById(R.id.lay_menu);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        finish();
    }
}
