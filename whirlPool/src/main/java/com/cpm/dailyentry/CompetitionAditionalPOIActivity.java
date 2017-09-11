package com.cpm.dailyentry;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cpm.Constants.CommonString1;
import com.cpm.whirlpool.R;
import com.cpm.database.GSKDatabase;
import com.cpm.xmlGetterSetter.AssetInsertdataGetterSetter;
import com.cpm.xmlGetterSetter.FacingCompetitorGetterSetter;
import com.cpm.xmlGetterSetter.STOREFIRSTIMEGetterSetter;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;

public class CompetitionAditionalPOIActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,ObservableScrollViewCallbacks{

    Spinner spinner_category,spinner_asset,spinbrand;
    EditText etremark;
    Button btnsave;
    ObservableListView lv;
    LinearLayout heading;

    GSKDatabase db;
    private ArrayAdapter<CharSequence> categoryAdapter,assetAdapter,brandAdapter;
    ArrayList<FacingCompetitorGetterSetter> categorylist=new ArrayList<FacingCompetitorGetterSetter>();
    ArrayList<AssetInsertdataGetterSetter> assetlist;
    ArrayList<STOREFIRSTIMEGetterSetter> poilist=new ArrayList<STOREFIRSTIMEGetterSetter>();
    //ArrayList<CompanyGetterSetter> companylist=new ArrayList<>();
    ArrayList<FacingCompetitorGetterSetter> brandlist=new ArrayList<>();
    //ArrayList<STOREFIRSTIMEGetterSetter> poilist=new ArrayList<STOREFIRSTIMEGetterSetter>();

    String category_cd,asset_cd,category,asset,brand_cd,brand;

    STOREFIRSTIMEGetterSetter poiGetterSetter = new STOREFIRSTIMEGetterSetter();

    private SharedPreferences preferences;
    String store_cd;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_aditional_poi);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner_category = (Spinner) findViewById(R.id.spincategory);
        spinner_asset = (Spinner) findViewById(R.id.spinasset);
        spinbrand = (Spinner) findViewById(R.id.spinbrand);
        etremark = (EditText) findViewById(R.id.etfaceup);
        btnsave = (Button) findViewById(R.id.btn_poi_save);
        lv = (ObservableListView) findViewById(R.id.lvaddnpoi);
        heading = (LinearLayout) findViewById(R.id.headerpoi);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        store_cd = preferences.getString(CommonString1.KEY_STORE_CD, null);

        db=new GSKDatabase(getApplicationContext());
        db.open();

        setCompetitorPOIData();

        lv.setScrollViewCallbacks(this);

        categoryAdapter = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_custom_item);
        assetAdapter = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_custom_item);

        categoryAdapter.add("Select Category");

        categorylist = db.getCategoryCompetionData();

        for (int i=0;i<categorylist.size();i++){

            categoryAdapter.add(categorylist.get(i).getCategory());

        }

        spinner_category.setAdapter(categoryAdapter);

        assetAdapter.add("Select Asset");

        assetlist=db.getAllAssetData();

        for (int i=0;i<assetlist.size();i++){

            assetAdapter.add(assetlist.get(i).getAsset());

        }

        spinner_asset.setAdapter(assetAdapter);

        spinner_category.setOnItemSelectedListener(this);
       // spinner_category.setBackgroundResource(R.drawable.gradient_bg_hover);

        spinner_asset.setOnItemSelectedListener(this);

        brandAdapter = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_custom_item);



       // companylist = db.getCompanyData();

      /*  for (int i=0;i<companylist.size();i++){

            brandAdapter.add(companylist.get(i).getCompany().get(0));

        }*/

        brandAdapter.add("Select Brand");
        spinbrand.setOnItemSelectedListener(this);
        spinbrand.setAdapter(brandAdapter);


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category_cd.equals("") || (category_cd == null)) {

                    Snackbar.make(v, "Please select a category", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (asset_cd.equals("") || (asset_cd == null)) {

                        Snackbar.make(v, "Please select an asset", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else if (brand_cd.equals("") || (brand_cd == null)) {

                    Snackbar.make(v, "Please select an brand", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else{
                        poiGetterSetter.setRemark(etremark.getText().toString().replaceAll("[&^<>{}'$]", ""));
                     /*   poiGetterSetter.setCategory_cd(category_cd);
                        poiGetterSetter.setCategory(category);
                        poiGetterSetter.setAsset_cd(asset_cd);
                        poiGetterSetter.setAsset(asset);
                        poiGetterSetter.setBrand_cd(brand_cd);
                        poiGetterSetter.setBrand(brand);

                        db.insertCompetitionPOIData(store_cd, poiGetterSetter);*/

                        spinner_category.setSelection(0);
                        spinner_asset.setSelection(0);

                        brandAdapter.clear();
                        brandAdapter.add("Select Brand");

                        spinbrand.setAdapter(brandAdapter);

                        spinbrand.setSelection(0);
                        
                        etremark.setText("");

                        poilist.clear();
                        setCompetitorPOIData();
                        lv.invalidate();

                    }
            }
        });


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){

            case R.id.spincategory:

                if (position != 0) {

                    category_cd = categorylist.get(position-1).getCategory_cd();
                    category = categorylist.get(position-1).getCategory();
                    brandlist = db.getBrandCompetitionData(category_cd);

                    brandAdapter.clear();
                    brandAdapter.add("Select Brand");

                    for (int i=0;i<brandlist.size();i++){

                          brandAdapter.add(brandlist.get(i).getBrand());
                    }
                    spinbrand.setAdapter(brandAdapter);
                }
                else{
                    category_cd="";
                    category = "";
                }

                    break;

            case R.id.spinasset:

                if (position != 0) {

                    asset_cd = assetlist.get(position-1).getAsset_cd();
                    asset = assetlist.get(position-1).getAsset();
                }
                else{
                    asset_cd="";
                    asset="";
                }

                break;


            case R.id.spinbrand:

                if (position != 0) {

                    brand_cd = brandlist.get(position-1).getBrand_cd();
                    brand = brandlist.get(position-1).getBrand();
                }
                else{
                    brand_cd="";
                    brand = "";
                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

        ActionBar ab = getSupportActionBar();
        if (ab == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }


    }

    private class POIAdaptor extends ArrayAdapter<STOREFIRSTIMEGetterSetter>{



        public POIAdaptor(Context context, ArrayList<STOREFIRSTIMEGetterSetter> poidata) {
            super(context,0, poidata);
            // TODO Auto-generated constructor stub
        }

        @Override
        public int getCount() {
            return poilist.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewStockHolder holder = null;

            if (convertView == null) {
                holder = new ViewStockHolder();

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.poi_list_item, null);

                holder.tvcategory = (TextView) convertView
                        .findViewById(R.id.tvcategory);
                holder.tvasset= (TextView) convertView
                        .findViewById(R.id.tvasset);
                holder.tvbrand= (TextView) convertView
                        .findViewById(R.id.tvbrand);
                holder.tvremark= (TextView) convertView
                        .findViewById(R.id.tvremark);
                holder.delete = (Button) convertView
                        .findViewById(R.id.btndelete);
                holder.layout = (RelativeLayout) convertView
                        .findViewById(R.id.poihead);

                holder.delete.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        RelativeLayout layout=(RelativeLayout) v.getParent();
                        int id = layout.getId();

                       // int id = lv.getId();

                        db.deleteCompetitionPOIRow(id);
                        poilist.remove(position);
                        notifyDataSetChanged();
                        Snackbar.make(v, "Data deleted", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

						/*LinearLayout layout=(LinearLayout) v.getParent();
						TextView tv=(TextView)layout.findViewById(R.id.tvkeyid);*/

                        /*int id = ((LinearLayout)v.getParent()).getId();

                        db.deleteStockRow(id, store_id);

                        skuStocklist.get(position).getId();

                        //	holder1.sales.setText("====");
                        skuStocklist.remove(position);

                        notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Data Deleted --"+idval, Toast.LENGTH_SHORT).show();*/
                    }
                });


                convertView.setTag(holder);

            } else {
                holder = (ViewStockHolder) convertView.getTag();
            }



			/*holder.id.setId(position);
			holder.stock.setId(position);
			holder.sku.setId(position);
			holder.sales.setId(position);*/

            holder.layout.setId(Integer.parseInt(poilist.get(position).getId()));
           // holder.id.setText(poilist.get(position).getId());
            /*holder.tvcategory.setText(poilist.get(position).getCategory());
            holder.tvasset.setText(poilist.get(position).getAsset());
            holder.tvbrand.setText(poilist.get(position).getBrand());*/
            holder.tvremark.setText(poilist.get(position).getRemark());

            return convertView;
        }


        public class ViewStockHolder {
            TextView tvcategory;
            TextView tvasset;
            TextView tvbrand;
            TextView tvremark;
            Button delete;
            RelativeLayout layout;
        }

    }

    public void setCompetitorPOIData(){

        poilist = db.getCompetitionPOIData(store_cd);


        if(poilist.size()>0){

            heading.setVisibility(View.VISIBLE);

            lv.setAdapter(new POIAdaptor(getApplicationContext(), poilist));
            lv.setVisibility(View.VISIBLE);
        }

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

    @Override
    public void onBackPressed() {
		/*Intent i = new Intent(this, DailyEntryScreen.class);
		startActivity(i);*/

        finish();


        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }


}
