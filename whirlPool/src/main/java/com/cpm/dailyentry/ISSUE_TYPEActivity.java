package com.cpm.dailyentry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cpm.Constants.CommonString1;
import com.cpm.whirlpool.R;
import com.cpm.database.GSKDatabase;
import com.cpm.xmlGetterSetter.ISSUE_TYPEGetterSetter;

import java.util.ArrayList;

public class ISSUE_TYPEActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner_category,spinner_brand;
    EditText etpromotion/*,etremark*/;
    Button btnsave;
    ListView lv;
    LinearLayout heading;
    RelativeLayout BrandLayout;
    GSKDatabase db;
    String issueType_cd,IssueType,brand_cd,brand;

    private SharedPreferences preferences;
    String store_cd,CATEGORY_CD;
    Boolean Issueflag=true;

    private ArrayAdapter<CharSequence> IssueTypeAdapter,brandAdapter;
    ArrayList<ISSUE_TYPEGetterSetter> Issuelist=new ArrayList<ISSUE_TYPEGetterSetter>();
    ArrayList<ISSUE_TYPEGetterSetter> brandlist=new ArrayList<>();
    ISSUE_TYPEGetterSetter IssueTypeGetterSetter=new ISSUE_TYPEGetterSetter();
    ArrayList<ISSUE_TYPEGetterSetter> Issuetypelist = new ArrayList<ISSUE_TYPEGetterSetter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_promotions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner_category = (Spinner) findViewById(R.id.spincategory);
        spinner_brand = (Spinner) findViewById(R.id.spinbrand);
        etpromotion = (EditText) findViewById(R.id.etfaceup);
       // etremark = (EditText) findViewById(R.id.etpromotion);
        btnsave = (Button) findViewById(R.id.btn_poi_save);
        lv = (ListView) findViewById(R.id.lvaddnpoi);
        heading = (LinearLayout) findViewById(R.id.headerpoi);

        BrandLayout = (RelativeLayout) findViewById(R.id.brandspinner);

        db=new GSKDatabase(getApplicationContext());
        db.open();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        store_cd = preferences.getString(CommonString1.KEY_STORE_CD, null);
        //CATEGORY_CD = preferences.getString(CommonString1.KEY_CATEGORY_CD, null);

        IssueTypeAdapter = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_custom_item);

        IssueTypeAdapter.add("Select Issue Type");

        brandAdapter = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_custom_item);

        brandAdapter.add("Select Brand");

       // Issuelist = db.getISSUE_TYPEData();

        for (int i=0;i<Issuelist.size();i++){

            IssueTypeAdapter.add(Issuelist.get(i).getISSUE_TYPE());

        }

       /* brandlist = db.getCompanyData();

        for (int i=0;i<brandlist.size();i++){

            brandAdapter.add(brandlist.get(i).getCompany().get(0));

        }*/

        spinner_category.setAdapter(IssueTypeAdapter);
        spinner_category.setOnItemSelectedListener(this);

        spinner_brand.setOnItemSelectedListener(this);
        spinner_brand.setAdapter(brandAdapter);

        setCompetitorPOIData();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (issueType_cd.equals("") || (issueType_cd == null)) {

                    Snackbar.make(v, "Please Select a Issue Type", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                    Issueflag=false;
                }

                else if(IssueType.equalsIgnoreCase("Brand Related"))
                {
                    if (brand_cd.equals("") || (brand_cd == null)) {

                        Snackbar.make(v, "Please Select a Brand", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();

                        Issueflag=false;
                    }
                    else if(etpromotion.getText().toString().equals("")){


                        Snackbar.make(v, "Please Enter Issue details", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        Issueflag=false;
                    }
                    else
                    {
                        Issueflag=true;
                    }


                }


                else if(etpromotion.getText().toString().equals("")){


                    Snackbar.make(v, "Please Enter Issue details", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    Issueflag=false;
                }
                else {
                    Issueflag=true;
                }

                if(Issueflag)
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ISSUE_TYPEActivity.this);
                    builder.setMessage("Are you sure you want to save")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {


                                            // promotionGetterSetter.setRemark(etremark.getText().toString().replaceAll("[&^<>{}'$]", ""));
                                            IssueTypeGetterSetter.setIssuedetail(etpromotion.getText().toString().replaceAll("[-@.?/|=+_#%:;^*()!&^<>{},'$1234567890]", ""));
                                            IssueTypeGetterSetter.setISSUETYPE_CD(issueType_cd);
                                            IssueTypeGetterSetter.setISSUE_TYPE(IssueType);
                                            IssueTypeGetterSetter.setBrand_cd(brand_cd);
                                            IssueTypeGetterSetter.setBrand(brand);

                                           // db.insertIssueTypeData(IssueTypeGetterSetter, store_cd,CATEGORY_CD);

                                            spinner_category.setSelection(0);

                                            brandAdapter.clear();
                                            brandAdapter.add("Select Brand");

                                            spinner_brand.setAdapter(brandAdapter);

                                            spinner_brand.setSelection(0);

                                            // etremark.setText("");
                                            etpromotion.setText("");

                                            Issuetypelist.clear();
                                            setCompetitorPOIData();
                                            lv.invalidate();
                                            BrandLayout.setVisibility(View.GONE);



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

        if(parent.getId()==  R.id.spincategory){

            String str = spinner_category.getSelectedItem().toString();

            if (position !=0) {
                if (str.equalsIgnoreCase("Brand Related")) {


                    BrandLayout.setVisibility(View.VISIBLE);


                    issueType_cd = Issuelist.get(position - 1).getISSUETYPE_CD();
                    IssueType = Issuelist.get(position - 1).getISSUE_TYPE();

                   // brandlist = db.getBrandCompetitionData();

                    brandAdapter.clear();
                    brandAdapter.add("Select Brand");

                    for (int i = 0; i < brandlist.size(); i++) {

                        brandAdapter.add(brandlist.get(i).getBrand());
                    }
                    spinner_brand.setAdapter(brandAdapter);

                }
                else {
                    BrandLayout.setVisibility(View.GONE);
                    issueType_cd = Issuelist.get(position - 1).getISSUETYPE_CD();
                    IssueType = Issuelist.get(position - 1).getISSUE_TYPE();

                    brand_cd="";
                    brand = "";

                }
            }
            else
            {
                BrandLayout.setVisibility(View.GONE);
                issueType_cd="";
                IssueType="";


            }

        }
        else  if(parent.getId()==  R.id.spinbrand){

            if (position != 0) {

                brand_cd = brandlist.get(position-1).getBrand_cd();
                brand = brandlist.get(position - 1).getBrand();
            }
            else{
                brand_cd="";
                brand = "";
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setCompetitorPOIData(){



       // Issuetypelist = db.getISSUE_TYPEData(store_cd,CATEGORY_CD);


        if(Issuetypelist.size()>0){

            /*skuStocklist.clear();

            for (int j = 0; j < nonpromoterData.size(); j++) {
                skuData skudata=new skuData();
                String skuname="";

                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).getSKU_CD().get(0).equals(nonpromoterData.get(j).getSku_CD())){
                        skuname=list.get(i).getSKU().get(0);
                        break;
                    }
                }

                skudata.setId(nonpromoterData.get(j).getKey_ID());
                skudata.setSku(skuname);
                skudata.setStock(nonpromoterData.get(j).getStock());
                skudata.setSales( nonpromoterData.get(j).getSales());

                skuStocklist.add(skudata);
            }
*/


            heading.setVisibility(View.VISIBLE);

            lv.setAdapter(new IssueTypeAdaptor(getApplicationContext(), Issuetypelist));
            lv.setVisibility(View.VISIBLE);
        }

    }


    private class IssueTypeAdaptor extends ArrayAdapter<ISSUE_TYPEGetterSetter>{



        public IssueTypeAdaptor(Context context, ArrayList<ISSUE_TYPEGetterSetter> poidata) {
            super(context,0, poidata);
            // TODO Auto-generated constructor stub
        }

        @Override
        public int getCount() {
            return Issuetypelist.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewStockHolder holder = null;

            if (convertView == null) {
                holder = new ViewStockHolder();

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.competitionpromotion_list, null);

                holder.tvIssue = (TextView) convertView
                        .findViewById(R.id.tvcategory);
                holder.tvIssuedetails= (TextView) convertView
                        .findViewById(R.id.tvpromotion);
                holder.tvremark= (TextView) convertView
                        .findViewById(R.id.tvremark);
                holder.tvbrand = (TextView) convertView.findViewById(R.id.tvbrand);
                holder.delete = (Button) convertView
                        .findViewById(R.id.btndelete);
                holder.layout = (RelativeLayout) convertView
                        .findViewById(R.id.poihead);

                holder.delete.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        AlertDialog.Builder builder = new AlertDialog.Builder(ISSUE_TYPEActivity.this);
                        builder.setMessage("Are you sure you want to Delete")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {



                                               /* RelativeLayout layout=(RelativeLayout) v.getParent();
                                                int id = layout.getId();*/

                                                String listid=Issuetypelist.get(position).getKey_ID();

                                               // db.deleteCompetitionPromotionRow(listid);
                                                Issuetypelist.remove(position);
                                                notifyDataSetChanged();
                                                //Snackbar.make(v, "Data Deleted", Snackbar.LENGTH_LONG).setAction("Action", null).show();



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
                });


                convertView.setTag(holder);

            } else {
                holder = (ViewStockHolder) convertView.getTag();
            }



			/*holder.id.setId(position);
			holder.stock.setId(position);
			holder.sku.setId(position);
			holder.sales.setId(position);*/

            holder.layout.setId(Integer.parseInt(Issuetypelist.get(position).getKey_ID()));
            holder.tvIssue.setText(Issuetypelist.get(position).getISSUE_TYPE());
           holder.tvIssuedetails.setText(Issuetypelist.get(position).getIssuedetail());
            holder.tvbrand.setText(Issuetypelist.get(position).getBrand());
            //holder.tvremark.setText(Issuetypelist.get(position).getRemark());

            return convertView;
        }


        public class ViewStockHolder {
            TextView tvIssue;
            TextView tvIssuedetails;
            TextView tvremark;
            TextView tvbrand;
            Button delete;
            RelativeLayout layout;
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
