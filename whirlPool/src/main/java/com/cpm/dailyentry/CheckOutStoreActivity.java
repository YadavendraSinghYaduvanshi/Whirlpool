package com.cpm.dailyentry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString1;
import com.cpm.database.GSKDatabase;
import com.cpm.delegates.CoverageBean;
import com.cpm.whirlpool.R;
import com.cpm.message.AlertMessage;

import com.cpm.xmlGetterSetter.CheckoutBean;
import com.cpm.xmlGetterSetter.FailureGetterSetter;

@SuppressWarnings("deprecation")
public class CheckOutStoreActivity extends Activity {
    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    private String username, visit_date, store_id;
    private Data data;
    private GSKDatabase db;
    private SharedPreferences preferences = null;
    ArrayList<CoverageBean> list_coverage = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storename);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = preferences.getString(CommonString1.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString1.KEY_DATE, null);
        db = new GSKDatabase(this);
        db.open();
        store_id = getIntent().getStringExtra(CommonString1.KEY_STORE_CD);
        list_coverage = db.getCoverageSpecificData(store_id);
        new BackgroundTask(this).execute();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        db.open();

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        db.close();
    }

    private class BackgroundTask extends AsyncTask<Void, Data, String> {
        private Context context;

        BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom);
            dialog.setTitle("Sending Checkout Data");
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);

        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                data = new Data();
                data.value = 20;
                data.name = "Checked out Data Uploading";
                publishProgress(data);
                if (list_coverage.size() > 0) {
                    String onXML = "[STORE_CHECK_OUT_STATUS][USER_ID]"
                            + username
                            + "[/USER_ID]" + "[STORE_ID]"
                            + store_id
                            + "[/STORE_ID][LATITUDE]"
                            + list_coverage.get(0).getLatitude()
                            + "[/LATITUDE][LOGITUDE]"
                            + list_coverage.get(0).getLongitude()
                            + "[/LOGITUDE][CHECKOUT_DATE]"
                            + visit_date
                            + "[/CHECKOUT_DATE][CHECK_OUTTIME]"
                            + getCurrentTime()
                            + "[/CHECK_OUTTIME][CHECK_INTIME]"
                            + list_coverage.get(0).getInTime()
                            + "[/CHECK_INTIME][CREATED_BY]"
                            + username
                            + "[/CREATED_BY][/STORE_CHECK_OUT_STATUS]";

                    final String sos_xml = "[DATA]" + onXML + "[/DATA]";
                    SoapObject request = new SoapObject(CommonString1.NAMESPACE, "Upload_Store_ChecOut_Status");
                    request.addProperty("onXML", sos_xml);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString1.URL);
                    androidHttpTransport.call(CommonString1.SOAP_ACTION + "Upload_Store_ChecOut_Status", envelope);
                    Object result = (Object) envelope.getResponse();
                    if (result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS_chkout)) {
                        db.updateCoverageStoreOutTime(store_id, visit_date, getCurrentTime(), CommonString1.KEY_C);
                        db.updateStoreStatusOnCheckout(store_id, visit_date, CommonString1.KEY_C);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(CommonString1.KEY_STOREVISITED, "");
                        editor.putString(CommonString1.KEY_STOREVISITED_STATUS, "");
                        editor.commit();
                        data.value = 100;
                        data.name = "Checkout Done";
                        publishProgress(data);
                        return CommonString1.KEY_SUCCESS;
                    } else {
                        return CommonString1.METHOD_Checkout_StatusNew;
                    }
                }
            } catch (MalformedURLException e) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        showMessage(AlertMessage.MESSAGE_SOCKETEXCEPTION);
                    }
                });

            } catch (IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage(AlertMessage.MESSAGE_SOCKETEXCEPTION);
                    }
                });
            } catch (Exception e) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        showMessage(AlertMessage.MESSAGE_EXCEPTION);
                    }
                });
            }

            return "";
        }

        @Override
        protected void onProgressUpdate(Data... values) {
            // TODO Auto-generated method stub
            pb.setProgress(values[0].value);
            percentage.setText(values[0].value + "%");
            message.setText(values[0].name);
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equals(CommonString1.KEY_SUCCESS)) {
                AlertMessage message = new AlertMessage(CheckOutStoreActivity.this, "Successfully Checked out", "checkout", null);
                message.showMessage();
                finish();
            } else if (!result.equals("")) {
                Toast.makeText(getApplicationContext(), "Network Error Try Again", Toast.LENGTH_SHORT).show();
                finish();
            }

        }

    }

    class Data {
        int value;
        String name;
    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());
        return cdate;

    }

    public void showMessage(String msg) {
        new AlertDialog.Builder(CheckOutStoreActivity.this)
                .setTitle("Alert Dialog")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(R.drawable.parinaam_logo_ico)
                .show();


    }

}
