package com.cpm.dailyentry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpm.Constants.CommonString1;
import com.cpm.whirlpool.R;
import com.cpm.database.GSKDatabase;
import com.cpm.keyboard.BasicOnKeyboardActionListener;
import com.cpm.keyboard.CustomKeyboardView;
import com.cpm.xmlGetterSetter.AssetInsertdataGetterSetter;
import com.cpm.xmlGetterSetter.FacingCompetitorGetterSetter;
import com.cpm.xmlGetterSetter.STOREFIRSTIMEGetterSetter;
import com.cpm.xmlGetterSetter.StockNewGetterSetter;

import java.util.ArrayList;
import java.util.Calendar;

public class STOREFIRSTTIMEActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    Spinner spinner_category,spinner_asset,spinner_brand;
    EditText Edname,EdContact,EdAddress;
    Button btnsave;
    CustomKeyboardView mKeyboardView;
    Keyboard mKeyboard;

    //ListView lv;
    //LinearLayout heading;

    String _pathforcheck,_path,str;

    GSKDatabase db;
   // private ArrayAdapter<CharSequence> categoryAdapter,assetAdapter,brandAdapter;
    ArrayList<FacingCompetitorGetterSetter> categorylist=new ArrayList<FacingCompetitorGetterSetter>();
    ArrayList<StockNewGetterSetter> brandlist=new ArrayList<StockNewGetterSetter>();
    ArrayList<AssetInsertdataGetterSetter> assetlist;
    ArrayList<STOREFIRSTIMEGetterSetter> SFTLIST=new ArrayList<STOREFIRSTIMEGetterSetter>();
    //ArrayList<STOREFIRSTIMEGetterSetter> SFTLIST=new ArrayList<STOREFIRSTIMEGetterSetter>();

    String category_cd, asset_cd, category, asset, brand_cd, brand;
    static int currentapiVersion = 1;
    STOREFIRSTIMEGetterSetter STOREFIRSTIMEGetterSetter = new STOREFIRSTIMEGetterSetter();

    private SharedPreferences preferences;
    String store_cd,visit_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Edname = (EditText) findViewById(R.id.name);

        EdContact = (EditText) findViewById(R.id.Contact);
        EdAddress = (EditText) findViewById(R.id.Address);

        btnsave = (Button) findViewById(R.id.btn_poi_save);

        mKeyboard = new Keyboard(this, R.xml.keyboard);

        mKeyboardView = (CustomKeyboardView) findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView
                .setOnKeyboardActionListener(new BasicOnKeyboardActionListener(
                        this));

        currentapiVersion = android.os.Build.VERSION.SDK_INT;

        if (currentapiVersion >= 11) {

            EdContact.setTextIsSelectable(true);


            EdContact.setRawInputType(InputType.TYPE_CLASS_TEXT);



        } else {

            EdContact.setInputType(0);



        }

        EdContact.setOnFocusChangeListener(STOREFIRSTTIMEActivity.this);

        EdContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showKeyboardWithAnimation();
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                // imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


            }
        });


        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        store_cd = preferences.getString(CommonString1.KEY_STORE_CD, null);
        visit_date = preferences.getString(CommonString1.KEY_DATE, null);

        str = CommonString1.FILE_PATH;

        db=new GSKDatabase(getApplicationContext());
        db.open();

        setSFTdata();




        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( (Edname.getText().toString() == null) || Edname.getText().toString().equals("")) {

                    Snackbar.make(v, "Please enter Retailer name", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    Edname.setHint("Retailer name");
                    Edname.setHintTextColor(getResources().getColor(R.color.red));
                }


               else if ((EdContact.getText().toString() == null) || EdContact.getText().toString().equals("")) {
                    Snackbar.make(v, "Please enter Retailer contact no.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    EdContact.setHint("Retailer  contact no.");
                    EdContact.setHintTextColor(getResources().getColor(R.color.red));


                }

                else if ((EdAddress.getText().toString() == null) || EdAddress.getText().toString().equals("")) {
                    Snackbar.make(v, "Please enter outlet address", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    EdAddress.setHint("Outlet address");
                    EdAddress.setHintTextColor(getResources().getColor(R.color.red));


                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(STOREFIRSTTIMEActivity.this);
                    builder.setMessage("Are you sure you want to save")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {


                                            STOREFIRSTIMEGetterSetter.setName(Edname.getText().toString().replaceAll("[-@.?/|=+_#%:;^*()!&^<>{},'$]", ""));
                                            STOREFIRSTIMEGetterSetter.setContact(EdContact.getText().toString().replaceAll("[@&^<>{}'$]", ""));
                                            STOREFIRSTIMEGetterSetter.setAddress(EdAddress.getText().toString().replaceAll("[-@.?/|=+_#%:;^*()!&^<>{},'$]", ""));


                                            db.insertSTOREFIRSTIMEData(STOREFIRSTIMEGetterSetter, store_cd);

                                            Toast.makeText(getApplicationContext(),
                                                    "Data has been saved", Toast.LENGTH_SHORT).show();
                                            finish();

                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {

                                            if(mKeyboardView.getVisibility() == View.INVISIBLE){
                                                mKeyboardView.setVisibility(View.VISIBLE);
                                            }

                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();

                    alert.show();

                }

            }
        });


    }





    public void setSFTdata(){

        SFTLIST = db.getSFTData(store_cd);


        if(SFTLIST.size()>0){



            Edname.setText(SFTLIST.get(0).getName());

            EdContact.setText(SFTLIST.get(0).getContact());

            EdAddress.setText(SFTLIST.get(0).getAddress());


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
        if(mKeyboardView.getVisibility() == View.VISIBLE){
            mKeyboardView.setVisibility(View.INVISIBLE);
        }
        else
        {

            finish();


            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }

    }




    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();

        String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
                + m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);

        return intime;

    }

    private void showKeyboardWithAnimation() {
        if (mKeyboardView.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils
                    .loadAnimation(STOREFIRSTTIMEActivity.this,
                            R.anim.slide_in_bottom);
            mKeyboardView.showWithAnimation(animation);
        }
        else if(mKeyboardView.getVisibility() == View.INVISIBLE){
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }



    public void onFocusChange(View view, boolean hasFocus) {
        // TODO Auto-generated method stub

        int id = view.getId();

        if(id==R.id.Contact){
            if(hasFocus){
                showKeyboardWithAnimation();
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
               // imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            else{
                hide();


            }
        }

    }

    public void hide() {
        mKeyboardView.setVisibility(View.INVISIBLE);


    }




}
