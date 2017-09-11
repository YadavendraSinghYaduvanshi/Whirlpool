package com.cpm.dailyentry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonFunctions;
import com.cpm.Constants.CommonString1;
import com.cpm.whirlpool.R;
import com.cpm.database.GSKDatabase;
import com.cpm.xmlGetterSetter.POSM_MASTERGetterSetter;

import java.io.File;
import java.util.ArrayList;

import static com.cpm.Constants.CommonFunctions.getCurrentTime;

/**
 * Created by ashishc on 08-03-2017.
 */

public class PosmActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    Button save_btn;
    ArrayList<POSM_MASTERGetterSetter> posmList;
    GSKDatabase db;
    Context context;
    ImageButton global_camera;
    Integer global_position = 0;
    RecyclerAdapter adapter;
    Activity activity;
    private SharedPreferences preferences;
    String store_cd = "0", _pathforcheck = "", _path = "", image = "", flag_operation = "save", visit_date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posm_layout);
        declaration();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("POSM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        posmList = db.getPOSMData(store_cd);

        if (posmList.size() == 0) {
            posmList = db.getPosmList();
            adapter = new RecyclerAdapter(context, posmList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        else
        {
            adapter = new RecyclerAdapter(context, posmList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerView.clearFocus();

                if (validate()) {
                    long id = db.insertPOSMData(posmList,store_cd);
                    if (id > 0) {
                        Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, "Data not inserted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please fill data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        LayoutInflater inflater;
        ArrayList<POSM_MASTERGetterSetter> posmList;

        public RecyclerAdapter(Context context, ArrayList<POSM_MASTERGetterSetter> posmList) {
            inflater = LayoutInflater.from(context);
            this.posmList = posmList;
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_posm_list, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, final int position) {
            holder.posm_textView.setText(posmList.get(position).getPOSM().get(0));
            if(posmList.get(position).getImage().equalsIgnoreCase(""))
            {
                holder.posm_Image.setImageResource(R.drawable.camera);
            }
            else
            {
                holder.posm_Image.setImageResource(R.drawable.camera_done);
            }

            holder.posm_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    String val = ((EditText) findViewById(v.getId())).getText().toString();
                    if (val.equalsIgnoreCase("")) {
                        posmList.get(position).setQuantity("");
                    } else {
                        posmList.get(position).setQuantity(val);
                    }
                }
            });
            holder.posm_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    _pathforcheck = store_cd + "POSM"
                            + "Image" + "-"+ visit_date.replace("/","")+"-"+ getCurrentTime().replace(":", "") + ".jpg";
                    _path = CommonString1.FILE_PATH + _pathforcheck;
                    global_camera = (ImageButton) v;
                    global_position = position;
                    CommonFunctions.startCameraActivity(activity, _path);
                }
            });


            holder.posm_edt.setText(posmList.get(position).getQuantity());

            holder.posm_edt.setId(position);

        }

        @Override
        public int getItemCount() {
            return posmList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView posm_textView;
            EditText posm_edt;
            ImageButton posm_Image;

            public ViewHolder(View itemView) {
                super(itemView);
                posm_textView = (TextView) itemView.findViewById(R.id.posm_txt);
                posm_edt = (EditText) itemView.findViewById(R.id.posm_qty);
                posm_Image = (ImageButton) itemView.findViewById(R.id.posm_Image);
            }


        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;

            case -1:
                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(CommonString1.FILE_PATH + _pathforcheck).exists()) {
                        global_camera.setBackgroundResource(R.drawable.camera_done);
                        image = _pathforcheck;
                        posmList.get(global_position).setImage(image);
                        adapter.notifyDataSetChanged();
                        image = "";
                    }
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void declaration() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.posm_recyclerView);
        save_btn = (Button) findViewById(R.id.save_btn);
        posmList = new ArrayList<POSM_MASTERGetterSetter>();
        context = this;
        activity = this;
        db = new GSKDatabase(context);
        db.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString1.KEY_DATE, null);
        store_cd = preferences.getString(CommonString1.KEY_STORE_CD, null);


    }


    private boolean validate() {
        boolean isGood = false;
        for (int i = 0; i < posmList.size(); i++) {
            if ((!posmList.get(i).getQuantity().equalsIgnoreCase(""))) {
                if((!posmList.get(i).getImage().equalsIgnoreCase("")))
                {
                    isGood = true;
                    break;
                }
            }
        }

        for (int i = 0; i < posmList.size(); i++) {
            if ((!posmList.get(i).getQuantity().equalsIgnoreCase("")) && (posmList.get(i).getImage().equalsIgnoreCase(""))) {
                isGood = false;
                break;
            }
        }


        return isGood;
    }


}
