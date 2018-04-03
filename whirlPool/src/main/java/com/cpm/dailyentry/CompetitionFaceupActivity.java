package com.cpm.dailyentry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString1;
import com.cpm.whirlpool.R;
import com.cpm.database.GSKDatabase;
import com.cpm.delegates.CoverageBean;
import com.cpm.keyboard.BasicOnKeyboardActionListener;
import com.cpm.keyboard.CustomKeyboardView;
import com.cpm.xmlGetterSetter.FacingCompetitorGetterSetter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CompetitionFaceupActivity extends AppCompatActivity implements View.OnClickListener{

    boolean checkflag=true;
    static int currentapiVersion = 1;
    Button btnsave;
    ExpandableListView expListView;
    ArrayList<FacingCompetitorGetterSetter> facingcomplist=new ArrayList<FacingCompetitorGetterSetter>();
    ArrayList<FacingCompetitorGetterSetter> categorylist=new ArrayList<FacingCompetitorGetterSetter>();
    GSKDatabase db;
    int listsize=0;
    private SharedPreferences preferences;
    String visit_date,username,intime,store_cd;

    CustomKeyboardView mKeyboardView;
    Keyboard mKeyboard;

  //  MyAdaptor myadapter;

    boolean ischangedflag=false;

    List<FacingCompetitorGetterSetter> listDataHeader;
    HashMap<FacingCompetitorGetterSetter, List<FacingCompetitorGetterSetter>> listDataChild;

    ExpandableListAdapter listAdapter;

    List<Integer> checkHeaderArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_faceup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentapiVersion = android.os.Build.VERSION.SDK_INT;

        //lv=(ListView) findViewById(R.id.lvfacing);
        // get the list view
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        btnsave=(Button) findViewById(R.id.save_btn);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mKeyboard = new Keyboard(this, R.xml.keyboard);

        mKeyboardView = (CustomKeyboardView) findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView
                .setOnKeyboardActionListener(new BasicOnKeyboardActionListener(
                        this));

        db=new GSKDatabase(getApplicationContext());
        db.open();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        store_cd = preferences.getString(CommonString1.KEY_STORE_CD, null);

        visit_date = preferences.getString(CommonString1.KEY_DATE, null);
        username= preferences.getString(CommonString1.KEY_USERNAME, null);
        intime= preferences.getString(CommonString1.KEY_STORE_IN_TIME, "");

       // myadapter=new MyAdaptor();

        setDataToListView();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        btnsave.setOnClickListener(this);

        expListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                expListView.invalidateViews();
            }

        });

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                /* Toast.makeText(getApplicationContext(),
                 "Group Clicked " + listDataHeader.get(groupPosition),
                 Toast.LENGTH_LONG).show();*/
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {


            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
				/*Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition).getBrand() + " Collapsed",
						Toast.LENGTH_LONG).show();*/
                if (mKeyboardView.getVisibility() == View.VISIBLE) {
                    mKeyboardView.setVisibility(View.INVISIBLE);
					/*mKeyboardView.requestFocusFromTouch();*/
                }

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition).getBrand()
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition).getBrand(), Toast.LENGTH_LONG)
                        .show();


                findViewById(R.id.lvExp).setVisibility(View.INVISIBLE);
                findViewById(R.id.entry_data).setVisibility(View.VISIBLE);
              /*  tvheader.setText(listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition).getSku());
                sku_cd = listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition).getSku_cd();

                saveBtnFlag = 1;

*/
                return false;
            }
        });

    }

    public void setDataToListView(){
        try{

            listDataHeader = new ArrayList<FacingCompetitorGetterSetter>();
            listDataChild = new HashMap<FacingCompetitorGetterSetter, List<FacingCompetitorGetterSetter>>();

           /* categorylist = db.getFacingCompetitorData(store_cd);

            if(categorylist.size()<=0){
                categorylist = db.getCategoryData();
            }*/

            categorylist = db.getCategoryCompetionFaceupData(store_cd);
            if(categorylist.size()>0){

                // Adding child data

                for(int i=0;i<categorylist.size(); i++) {
                    listDataHeader.add(categorylist.get(i));

                    facingcomplist=db.getFacingCompetitorCategotywiseData(store_cd,categorylist.get(i).getCategory_cd());
                    if(!(facingcomplist.size()>0) || (facingcomplist.get(0).getBrand_cd()==null) || (facingcomplist.get(0).getBrand_cd().equals(""))){
                        facingcomplist=db.getBrandCompetitionData(categorylist.get(i).getCategory_cd());
                    }
                    else{
                        btnsave.setText("Update");
                    }

                   /* List<FacingCompetitorGetterSetter> skulist = new ArrayList<>();
                    for(int j=0;j<skuData.size();j++){
                        skulist.add(skuData.get(j));
                    }*/

                    listDataChild.put(listDataHeader.get(i), facingcomplist); // Header, Child data
                }

            }
        }
        catch(Exception e){
            Log.d("Exception when fetching",
                    e.toString());
        }

      /*  if(facingcomplist.size()>0){
            listsize=facingcomplist.size();
			*//*etremarkone.setText(facingcomplist.get(0).getRemark());
			spin.setSelection(statuslist.indexOf(facingcomplist.get(0).getStatus()));*//*
            expListView.setAdapter(myadapter);
        }*/

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id==R.id.save_btn){

            expListView.clearFocus();

            if(validate(facingcomplist)){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to save")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        db.open();

                                        dialog.cancel();

                                        getMid();

                                        db.insertFscingCompetitorData(store_cd, listDataChild, listDataHeader);
                                        Toast.makeText(getApplicationContext(),
                                                "Data has been saved", Toast.LENGTH_SHORT).show();

						/*Intent DailyEntryMenu = new Intent(
								FacingCompetitor.this,
								StoreEntry.class);
						startActivity(DailyEntryMenu);*/

                                        finish();

                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();

                alert.show();

            }
            else{
                listAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Please fill all data", Toast.LENGTH_SHORT).show();
            }

        }

    }

   /* private class MyAdaptor extends BaseAdapter {

        @Override
        public int getCount() {

            return listsize;
        }

        @Override
        public Object getItem(int position) {

            return position;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();



                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.faceup_item, null);

                holder.cardView=(CardView) convertView.findViewById(R.id.card_view);

                holder.tvbrand= (TextView) convertView
                        .findViewById(R.id.tvdfav);
                holder.etfacing=(EditText) convertView.findViewById(R.id.etfaceup);
                //holder.etstoredf=(EditText) convertView.findViewById(R.id.etstoredf);


                if (currentapiVersion >= 11) {
                    holder.etfacing.setTextIsSelectable(true);
                   // holder.etstoredf.setTextIsSelectable(true);

                    holder.etfacing.setRawInputType(InputType.TYPE_CLASS_TEXT);
                   // holder.etstoredf.setRawInputType(InputType.TYPE_CLASS_TEXT);

                } else {
                    holder.etfacing.setInputType(0);
                   // holder.etstoredf.setInputType(0);

                }


                holder.etfacing
                        .setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            public void onFocusChange(View v, boolean hasFocus) {

                                if(hasFocus){

                                    showKeyboardWithAnimation();
                                }

                                if (!hasFocus) {

                                    hide();
                                    final int position = v.getId();
                                    final EditText Caption = (EditText) v;
                                    String value1 = Caption.getText().toString();

                                    if (value1.equals("")) {

                                        facingcomplist.get(position).setMccaindf("");

                                    } else {

                                        ischangedflag=true;

                                        facingcomplist.get(position).setMccaindf(value1);

                                    }

                                }
                            }
                        });

             *//*   holder.etstoredf
                        .setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            public void onFocusChange(View v, boolean hasFocus) {

                                if(hasFocus){
                                    showKeyboardWithAnimation();
                                }

                                if (!hasFocus) {

                                    hide();
                                    final int position = v.getId();
                                    final EditText Caption = (EditText) v;
                                    String value1 = Caption.getText().toString();

                                    if (value1.equals("")) {

                                        facingcomplist.get(position).setStoredf("");

                                    } else {

                                        ischangedflag=true;

                                        facingcomplist.get(position).setStoredf(value1);

                                    }

                                }
                            }
                        });*//*

                convertView.setTag(holder);


            } else {
                holder = (ViewHolder) convertView.getTag();

            }


            holder.tvbrand.setId(position);
            holder.etfacing.setId(position);
          //  holder.etstoredf.setId(position);

            holder.tvbrand.setText(facingcomplist.get(position).getBrand());
           // holder.etstoredf.setText(facingcomplist.get(position).getStoredf());
            holder.etfacing.setText(facingcomplist.get(position).getMccaindf());

            if(!checkflag){

                boolean tempflag=false;

             *//*   if(holder.etstoredf.getText().toString().equals("")){
                    //holder.dfavail.setTextColor(Color.RED);
                    //convertView.setBackgroundColor(getResources().getColor(R.color.red));
                    holder.etstoredf.setHintTextColor(getResources().getColor(R.color.red));
                    holder.etstoredf.setHint("Empty");
                    tempflag=true;
                }
*//*

                if(holder.etfacing.getText().toString().equals("")){
                    //holder.dfavail.setTextColor(Color.RED);

                    holder.etfacing.setHintTextColor(getResources().getColor(R.color.red));
                    holder.etfacing.setHint("Empty");
                    tempflag=true;
                }


                if(tempflag){
                    //holder.dfavail.setTextColor(Color.RED);
                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                }
                else{
                    //holder.dfavail.setTextColor(Color.BLACK);
                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                }

            }



            return convertView;
        }

    }*/


    /*private class ViewHolder {
        TextView dfavail;
        EditText etfacing;
        //EditText etstoredf;
        CardView cardView;
    }*/


    /***
     * Display the screen keyboard with an animated slide from bottom
     */
    private void showKeyboardWithAnimation() {
        if (mKeyboardView.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils
                    .loadAnimation(CompetitionFaceupActivity.this,
                            R.anim.slide_in_bottom);
            mKeyboardView.showWithAnimation(animation);
        }
        else if(mKeyboardView.getVisibility() == View.INVISIBLE){
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());

       /* String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
                + m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);*/

        return cdate;

    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        if (mKeyboardView.getVisibility() == View.VISIBLE) {
            mKeyboardView.setVisibility(View.INVISIBLE);
			/*mKeyboardView.requestFocusFromTouch();*/
        } else {

            if(ischangedflag){

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        CompetitionFaceupActivity.this);
                builder.setMessage(CommonString1.ONBACK_ALERT_MESSAGE)
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {

                                        finish();

                                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();

            }
            else{
                finish();

                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            }


        }

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mKeyboardView.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setVisibility(View.INVISIBLE);
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

        if(id==android.R.id.home){

            // NavUtils.navigateUpFromSameTask(this);
            finish();

            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

        }

        return super.onOptionsItemSelected(item);
    }

    public void hide() {
        mKeyboardView.setVisibility(View.INVISIBLE);
	/*	// mKeyboardView.clearFocus();
		mKeyboardView.requestFocusFromTouch();*/

    }

    public long checkMid() {
        return db.CheckMid(visit_date, store_cd);
    }

    public long getMid() {

        long mid = 0;

        mid = checkMid();

        if (mid == 0) {
            CoverageBean cdata = new CoverageBean();
            cdata.setStoreId(store_cd);
            cdata.setVisitDate(visit_date);
            cdata.setUserId(username);
            cdata.setInTime(intime);
            cdata.setOutTime(getCurrentTime());
            cdata.setReason("");
            cdata.setReasonid("0");
            cdata.setLatitude("0.0");
            cdata.setLongitude("0.0");
            mid = db.InsertCoverageData(cdata);

        }

        return mid;
    }

    public boolean validate(ArrayList<FacingCompetitorGetterSetter> complist){

        for (int j = 0; j < complist.size(); j++) {
			/*String aspermccain = listDataChild2.get(listDataHeader2.get(i)).get(j).getAs_per_meccain();*/
            String mccainfacing= complist.get(j).getMccaindf();
           // String storefacing= complist.get(j).getStoredf();

            if (mccainfacing.equalsIgnoreCase("") ) {

                checkflag=false;

                break;

            } else{
                checkflag=true;
            }
        }

        return checkflag;
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<FacingCompetitorGetterSetter> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<FacingCompetitorGetterSetter, List<FacingCompetitorGetterSetter>> _listDataChild;

        public ExpandableListAdapter(Context context, List<FacingCompetitorGetterSetter> listDataHeader,
                                     HashMap<FacingCompetitorGetterSetter, List<FacingCompetitorGetterSetter>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;

        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @SuppressLint("NewApi")
        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final FacingCompetitorGetterSetter childText = (FacingCompetitorGetterSetter) getChild(groupPosition, childPosition);

            ViewHolder holder=null;

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.faceup_item, null);

                holder=new ViewHolder();

                holder.cardView=(CardView) convertView.findViewById(R.id.card_view);

                holder.etfacing=(EditText) convertView.findViewById(R.id.etfaceup);
                holder.tvbrand= (TextView) convertView
                        .findViewById(R.id.tvdfav);

                convertView.setTag(holder);

            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }




            if (currentapiVersion >= 11) {

                holder.etfacing.setTextIsSelectable(true);

                holder.etfacing.setRawInputType(InputType.TYPE_CLASS_TEXT);

            } else {

                holder.etfacing.setInputType(0);


            }


//----------------------------------


            holder.etfacing.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (hasFocus) {
                        showKeyboardWithAnimation();
                    }

                    if (!hasFocus) {

                        hide();
                        final int position = v.getId();
                        final EditText Caption = (EditText) v;
                        String value1 = Caption.getText().toString();

                        if (value1.equals("")) {

                            _listDataChild
                                    .get(listDataHeader.get(groupPosition))
                                    .get(childPosition).setMccaindf("");

                        } else {

                            ischangedflag = true;
                            _listDataChild
                                    .get(listDataHeader.get(groupPosition))
                                    .get(childPosition).setMccaindf(value1);

                        }

                    }

                }
            });

/*
            holder.etfacing.setId(childPosition);
            holder.tvbrand.setId(childPosition);*/
            holder.etfacing.setText(childText.getMccaindf());

          /*  _listDataChild
                    .get(listDataHeader.get(groupPosition))
                    .get(childPosition).setSku_cd(childText.getSku_cd());*/


            _listDataChild
                    .get(listDataHeader.get(groupPosition))
                    .get(childPosition).setBrand_cd(childText.getBrand_cd());

            holder.tvbrand.setText(childText.getBrand());


           // txtListChild.setText(childText.getBrand());

            if(!checkflag){

                boolean tempflag=false;


                if(holder.etfacing.getText().toString().equals("")){
                    //holder.etfacing.setBackgroundColor(getResources().getColor(R.color.red));
                    holder.etfacing.setHintTextColor(getResources().getColor(R.color.red));
                    holder.etfacing.setHint("Empty");
                    tempflag=true;
                }
                else{
                    //holder.etfacing.setBackgroundColor(getResources().getColor(R.color.white));
                }


                if(tempflag){

                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                }
                else{

                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                    //validateData(_listDataChild,listDataHeader);
                }

            }


            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
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
        public View getGroupView(final int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            final FacingCompetitorGetterSetter headerTitle = (FacingCompetitorGetterSetter) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getCategory());

            if(!checkflag){
                if(checkHeaderArray.contains(groupPosition)){
                    lblListHeader.setBackgroundColor(getResources().getColor(R.color.red));
                }
                else{
                    lblListHeader.setBackgroundColor(getResources().getColor(R.color.teal_dark));
                }
            }


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


    public class ViewHolder{

        EditText etfacing;
        TextView tvbrand;
        CardView cardView;
    }

}
