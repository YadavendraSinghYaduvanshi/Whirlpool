package com.cpm.upload;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString;
import com.cpm.Constants.CommonString1;
import com.cpm.database.GSKDatabase;
import com.cpm.delegates.CoverageBean;
import com.cpm.geotag.GeotaggingBeans;
import com.cpm.geotag.ImageUploadActivity;
import com.cpm.message.AlertMessage;
import com.cpm.whirlpool.MainMenuActivity;
import com.cpm.whirlpool.R;
import com.cpm.xmlGetterSetter.DeploymentFormGetterSetter;
import com.cpm.xmlGetterSetter.FailureGetterSetter;
import com.cpm.xmlGetterSetter.POSM_MASTER_DataGetterSetter;
import com.cpm.xmlHandler.FailureXMLHandler;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@SuppressWarnings("deprecation")
public class UploadDataActivity extends Activity {
    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    String app_ver;
    private String visit_date, username;
    private SharedPreferences preferences;
    private GSKDatabase database;
    private int factor;
    String datacheck = "";
    String[] words;
    String validity;
    int mid;
    Data data;
    String exceptionMessage = "";
    boolean upload_status = false;
    String resultFinal;
    String Path;
    boolean isError = false;
    String errormsg = "";
    boolean up_success_flag = true;

    private FailureGetterSetter failureGetterSetter = null;
    private ArrayList<CoverageBean> coverageBeanlist = new ArrayList<>();
    ArrayList<POSM_MASTER_DataGetterSetter> posmTrackingList = new ArrayList<>();
    ArrayList<POSM_MASTER_DataGetterSetter> posmImageList = new ArrayList<>();
    DeploymentFormGetterSetter df = new DeploymentFormGetterSetter();
    ArrayList<GeotaggingBeans> geodata = new ArrayList<GeotaggingBeans>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString1.KEY_DATE, null);
        username = preferences.getString(CommonString1.KEY_USERNAME, null);
        app_ver = preferences.getString(CommonString1.KEY_VERSION, "");
        database = new GSKDatabase(this);
        database.open();
        Path = CommonString1.FILE_PATH;
        new UploadTask(this).execute();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        database.close();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
        UploadDataActivity.this.finish();
    }

    private class UploadTask extends AsyncTask<Void, Data, String> {
        private Context context;

        UploadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_upload);
            dialog.setTitle("Uploading Data");
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                data = new Data();
                if (!upload_status) {
                    coverageBeanlist = database.getCoverageData(visit_date);
                } else {
                    coverageBeanlist = database.getCoverageData(null);
                }

                for (int i = 0; i < coverageBeanlist.size(); i++) {
                    if (!coverageBeanlist.get(i).getStatus().equalsIgnoreCase(CommonString1.KEY_D)) {
                        String onXML = "[DATA]"
                                + "[USER_DATA]"
                                + "[STORE_CD]" + coverageBeanlist.get(i).getStoreId() + "[/STORE_CD]"
                                + "[VISIT_DATE]" + coverageBeanlist.get(i).getVisitDate() + "[/VISIT_DATE]"
                                + "[LATITUDE]" + coverageBeanlist.get(i).getLatitude() + "[/LATITUDE]"
                                + "[APP_VERSION]" + app_ver + "[/APP_VERSION]"
                                + "[LONGITUDE]" +coverageBeanlist.get(i).getLongitude() + "[/LONGITUDE]"
                                + "[IN_TIME]" + coverageBeanlist.get(i).getInTime() + "[/IN_TIME]"
                                + "[OUT_TIME]" + coverageBeanlist.get(i).getOutTime() + "[/OUT_TIME]"
                                + "[UPLOAD_STATUS]" + "N" + "[/UPLOAD_STATUS]"
                                + "[USER_ID]" + username + "[/USER_ID]"
                                + "[IMAGE_URL]" + coverageBeanlist.get(i).getImage() + "[/IMAGE_URL]"
                                + "[REASON_ID]" + coverageBeanlist.get(i).getReasonid() + "[/REASON_ID]"
                                + "[REASON_REMARK]" + coverageBeanlist.get(i).getRemark() + "[/REASON_REMARK]"
                                + "[/USER_DATA]"
                                + "[/DATA]";


                        SoapObject request = new SoapObject(CommonString1.NAMESPACE, CommonString1.METHOD_UPLOAD_DR_STORE_COVERAGE);
                        request.addProperty("onXML", onXML);
                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = true;
                        envelope.setOutputSoapObject(request);
                        HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString1.URL);
                        androidHttpTransport.call(CommonString1.SOAP_ACTION + CommonString1.METHOD_UPLOAD_DR_STORE_COVERAGE, envelope);
                        Object result = envelope.getResponse();

                        datacheck = result.toString();
                        datacheck = datacheck.replace("\"", "");
                        words = datacheck.split("\\;");
                        validity = (words[0]);

                        if (validity.equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                            database.updateCoverageStatus(coverageBeanlist.get(i).getMID(), CommonString1.KEY_P);

                            database.updateStoreStatusOnLeave(coverageBeanlist.get(i).getStoreId(),
                                    coverageBeanlist.get(i).getVisitDate(), CommonString1.KEY_P);
                        } else {
                            isError = true;
                            continue;

                        }
                        mid = Integer.parseInt((words[1]));
                        data.value = 30;
                        data.name = "Uploading";
                        publishProgress(data);


                        String final_xml = "";
                        onXML = "";
                        posmTrackingList = database.getPOSMCategoryData_UploadData(coverageBeanlist.get(i).getStoreId());

                        if (posmTrackingList.size() > 0) {

                            for (int j = 0; j < posmTrackingList.size(); j++) {
                                onXML = "[POSM_DATA_NEW]"
                                        + "[MID]" + mid + "[/MID]"
                                        + "[CREATED_BY]" + username + "[/CREATED_BY]"
                                        + "[STORE_CD]" + posmTrackingList.get(j).getStore_cd() + "[/STORE_CD]"
                                        + "[POSM_CD]" + posmTrackingList.get(j).getPosm_cd() + "[/POSM_CD]"
                                        + "[PCATEGORY_CD]" + posmTrackingList.get(j).getpCategory_cd() + "[/PCATEGORY_CD]"
                                        + "[PSUB_CATEGORY_CD]" + posmTrackingList.get(j).getpSub_Category_cd() + "[/PSUB_CATEGORY_CD]"
                                        + "[OLD_VALUE]" + posmTrackingList.get(j).getOldValue() + "[/OLD_VALUE]"
                                        + "[NEW_VALUE]" + posmTrackingList.get(j).getNewValue() + "[/NEW_VALUE]"
                                        + "[/POSM_DATA_NEW]";

                                final_xml = final_xml + onXML;
                            }

                            final String sos_xml = "[DATA]" + final_xml + "[/DATA]";
                            request = new SoapObject(CommonString1.NAMESPACE, CommonString1.METHOD_UPLOAD_XML);
                            request.addProperty("XMLDATA", sos_xml);
                            request.addProperty("KEYS", "POSM_DATA_NEW");
                            request.addProperty("USERNAME", username);
                            request.addProperty("MID", mid);
                            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                            envelope.dotNet = true;
                            envelope.setOutputSoapObject(request);

                            androidHttpTransport = new HttpTransportSE(CommonString1.URL);
                            androidHttpTransport.call(CommonString1.SOAP_ACTION + CommonString1.METHOD_UPLOAD_XML, envelope);

                            result = envelope.getResponse();

                            if (!result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                                isError = true;
                            }
                            data.value = 50;
                            data.name = "POSM_DATA_NEW";
                            publishProgress(data);
                        }


                        //StoreWise POSM Tracking Image Data
                        String imagefinal_xml = "";
                        onXML = "";
                        posmImageList = database.getPOSMImage_UploadData(coverageBeanlist.get(i).getStoreId());
                        if (posmImageList.size() > 0) {
                            for (int j = 0; j < posmImageList.size(); j++) {
                                onXML = "[POSM_IMAGE_DATA]"
                                        + "[MID]" + mid + "[/MID]"
                                        + "[CREATED_BY]" + username + "[/CREATED_BY]"
                                        + "[STORE_CD]" + posmImageList.get(j).getStore_cd() + "[/STORE_CD]"
                                        + "[PCATEGORY_CD]" + posmImageList.get(j).getpCategory_cd() + "[/PCATEGORY_CD]"
                                        + "[IMAGE1]" + posmImageList.get(j).getImage1() + "[/IMAGE1]"
                                        + "[IMAGE2]" + posmImageList.get(j).getImage2() + "[/IMAGE2]"
                                        + "[IMAGE3]" + posmImageList.get(j).getImage3() + "[/IMAGE3]"
                                        + "[/POSM_IMAGE_DATA]";

                                imagefinal_xml = imagefinal_xml + onXML;
                            }

                            final String sos_xml = "[DATA]" + imagefinal_xml + "[/DATA]";

                            request = new SoapObject(CommonString1.NAMESPACE, CommonString1.METHOD_UPLOAD_XML);
                            request.addProperty("XMLDATA", sos_xml);
                            request.addProperty("KEYS", "POSM_IMAGE_DATA");
                            request.addProperty("USERNAME", username);
                            request.addProperty("MID", mid);

                            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                            envelope.dotNet = true;
                            envelope.setOutputSoapObject(request);

                            androidHttpTransport = new HttpTransportSE(CommonString1.URL);
                            androidHttpTransport.call(CommonString1.SOAP_ACTION + CommonString1.METHOD_UPLOAD_XML, envelope);

                            result = envelope.getResponse();

                            if (!result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                                isError = true;
                            }
                            data.value = 60;
                            data.name = "POSM_IMAGE_DATA";
                            publishProgress(data);
                        }


                        //Deployment form data
                        imagefinal_xml = "";
                        onXML = "";
                        df = database.getdeploymentInsertedData(coverageBeanlist.get(i).getStoreId());
                        if (!df.getDf_img().equals("")) {
                            onXML = "[DEPLOYMENTFORM_DATA]"
                                    + "[MID]" + mid + "[/MID]"
                                    + "[CREATED_BY]" + username + "[/CREATED_BY]"
                                    + "[STORE_CD]" + df.getStore_cd() + "[/STORE_CD]"
                                    + "[DF_IMAGE]" + df.getDf_img() + "[/DF_IMAGE]"
                                    + "[/DEPLOYMENTFORM_DATA]";

                            imagefinal_xml = imagefinal_xml + onXML;


                            final String sos_xml = "[DATA]" + imagefinal_xml + "[/DATA]";
                            request = new SoapObject(CommonString1.NAMESPACE, CommonString1.METHOD_UPLOAD_XML);
                            request.addProperty("XMLDATA", sos_xml);
                            request.addProperty("KEYS", "DEPLOYMENTFORM_DATA");
                            request.addProperty("USERNAME", username);
                            request.addProperty("MID", mid);
                            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                            envelope.dotNet = true;
                            envelope.setOutputSoapObject(request);
                            androidHttpTransport = new HttpTransportSE(CommonString1.URL);
                            androidHttpTransport.call(CommonString1.SOAP_ACTION + CommonString1.METHOD_UPLOAD_XML, envelope);
                            result = envelope.getResponse();
                            if (!result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                                isError = true;
                            }
                            data.value = 67;
                            data.name = "DEPLOYMENTFORM_DATA";
                            publishProgress(data);
                        }


                        geodata = database.getGeotaggingData(coverageBeanlist.get(i).getStoreId());
                        if (geodata.size() > 0) {
                            final_xml = "";
                            onXML = "";
                            for (int i1 = 0; i1 < geodata.size(); i1++) {
                                if (!geodata.get(i1).getGEO_TAG().equals("Y")) {
                                    onXML = "[DATA][USER_DATA][STORE_ID]"
                                            + Integer.parseInt(geodata.get(i1).getStoreId())
                                            + "[/STORE_ID]"
                                            + "[USERNAME]"
                                            + username
                                            + "[/USERNAME]"
                                            + "[Image1]"
                                            + geodata.get(i1).getUrl1()
                                            + "[/Image1][Latitude]"
                                            + Double.toString(geodata.get(i1).getLatitude())
                                            + "[/Latitude][Longitude]"
                                            + Double.toString(geodata.get(i1).getLongitude())
                                            + "[/Longitude][/USER_DATA][/DATA]";
                                    final_xml = onXML;
                                    request = new SoapObject(CommonString.NAMESPACE, CommonString1.METHOD_UPLOAD_XML);
                                    request.addProperty("XMLDATA", final_xml);
                                    request.addProperty("KEYS", "GeoXML");
                                    request.addProperty("USERNAME", username);
                                    envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                                    envelope.dotNet = true;
                                    envelope.setOutputSoapObject(request);
                                    androidHttpTransport = new HttpTransportSE(CommonString1.URL);
                                    androidHttpTransport.call(CommonString.NAMESPACE + CommonString1.METHOD_UPLOAD_XML, envelope);
                                    result = (Object) envelope.getResponse();
                                    if (result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                                    } else {
                                        isError = true;
                                    }
                                }
                            }
                        }
                        //Deployment image
                        if (!df.getDf_img().equals("")) {
                            if (new File(CommonString1.FILE_PATH + df.getDf_img()).exists()) {
                                result = UploadImage(df.getDf_img(), "deploymentform");
                                if (!result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                                    isError = true;
                                }
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        message.setText("deploymentform Image Uploaded");
                                    }
                                });
                            }
                            data.value = 70;
                            data.name = "deploymentform image";
                            publishProgress(data);
                        }

                        //Store Image
                        if (coverageBeanlist.get(i).getImage() != null && !coverageBeanlist.get(i).getImage().equals("")) {
                            if (new File(CommonString1.FILE_PATH + coverageBeanlist.get(i).getImage()).exists()) {
                                result = UploadImage(coverageBeanlist.get(i).getImage(), "StoreImages");
                                if (!result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                                    //    return "StoreImages";
                                    isError = true;
                                }
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        message.setText("Store Image Uploaded");
                                    }
                                });
                            }
                        }
                        data.value = 75;
                        data.name = "StoreImages";
                        publishProgress(data);

                        //StoreWise POSM Tracking Image
                        posmImageList = database.getPOSMImage_UploadData(coverageBeanlist.get(i).getStoreId());
                        if (posmImageList.size() > 0) {
                            for (int j = 0; j < posmImageList.size(); j++) {
                                //Image 1
                                if (posmImageList.get(j).getImage1() != null && !posmImageList.get(j).getImage1().equals("")) {
                                    if (new File(CommonString1.FILE_PATH + posmImageList.get(j).getImage1()).exists()) {
                                        result = UploadImage(posmImageList.get(j).getImage1(), "PosmImages");
                                        if (!result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                                            isError = true;
                                        }
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                message.setText("Posm Images Uploaded");
                                            }
                                        });
                                    }
                                }

                                //Image 2
                                if (posmImageList.get(j).getImage2() != null && !posmImageList.get(j).getImage2().equals("")) {
                                    if (new File(CommonString1.FILE_PATH + posmImageList.get(j).getImage2()).exists()) {
                                        result = UploadImage(posmImageList.get(j).getImage2(), "PosmImages");
                                        if (!result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                                            isError = true;
                                        }

                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                message.setText("Posm Images Uploaded");
                                            }
                                        });
                                    }
                                }

                                //Image 3
                                if (posmImageList.get(j).getImage3() != null && !posmImageList.get(j).getImage3().equals("")) {

                                    if (new File(CommonString1.FILE_PATH + posmImageList.get(j).getImage3()).exists()) {

                                        result = UploadImage(posmImageList.get(j).getImage3(), "PosmImages");

                                        if (!result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                                            isError = true;
                                        }

                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                message.setText("Posm Images Uploaded");
                                            }
                                        });
                                    }
                                }
                            }
                            data.value = 80;
                            data.name = "PosmImages";
                            publishProgress(data);
                        }
                        if (geodata.size() > 0) {
                            for (int k = 0; k < geodata.size(); k++) {
                                if (geodata.get(k).getUrl1() != null && !geodata.get(k).getUrl1().equals("")) {

                                    if (new File(CommonString1.FILE_PATH + geodata.get(k).getUrl1()).exists()) {
                                        result = UploadImage(geodata.get(k).getUrl1(), "GeoTagImages");
                                        if (!result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                                            isError = true;
                                        }
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                message.setText("GeoTagImages Uploaded");
                                            }
                                        });
                                    }


                                }
                            }
                            data.value = 85;
                            data.name = "GeoTagImages";
                            publishProgress(data);
                        }

                        // SET COVERAGE STATUS
                        final_xml = "";
                        onXML = "";
                        onXML = "[COVERAGE_STATUS]"
                                + "[STORE_ID]" + coverageBeanlist.get(i).getStoreId() + "[/STORE_ID]"
                                + "[VISIT_DATE]" + coverageBeanlist.get(i).getVisitDate() + "[/VISIT_DATE]"
                                + "[USER_ID]" + coverageBeanlist.get(i).getUserId() + "[/USER_ID]"
                                + "[STATUS]" + CommonString1.KEY_D + "[/STATUS]"
                                + "[/COVERAGE_STATUS]";

                        final_xml = final_xml + onXML;
                        final String sos_xml = "[DATA]" + final_xml + "[/DATA]";
                        SoapObject request1 = new SoapObject(CommonString1.NAMESPACE, CommonString1.MEHTOD_UPLOAD_COVERAGE_STATUS);
                        request1.addProperty("onXML", sos_xml);
                        SoapSerializationEnvelope envelope1 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope1.dotNet = true;
                        envelope1.setOutputSoapObject(request1);
                        HttpTransportSE androidHttpTransport1 = new HttpTransportSE(CommonString1.URL);
                        androidHttpTransport1.call(CommonString1.SOAP_ACTION + CommonString1.MEHTOD_UPLOAD_COVERAGE_STATUS, envelope1);
                        Object result1 = envelope1.getResponse();
                        if (result1.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                            data.value = 99;
                            data.name = "COVERAGE_STATUS";
                            database.open();
                            database.updateCoverageStatus(coverageBeanlist.get(i).getMID(), CommonString1.KEY_D);
                            database.updateStoreStatusOnLeave(coverageBeanlist.get(i).getStoreId(), coverageBeanlist.get(i).getVisitDate(), CommonString1.KEY_D);
                            database.deleteSpecificStoreData(coverageBeanlist.get(i).getStoreId());
                        }

                        if (!result1.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
                            isError = true;
                        }
                        data.value = 100;
                        publishProgress(data);
                        resultFinal = result.toString();
                    }
                }
            } catch (MalformedURLException e) {
                up_success_flag = false;
                exceptionMessage = e.toString();

            } catch (IOException e) {
                up_success_flag = false;
                exceptionMessage = e.toString();

            } catch (Exception e) {
                up_success_flag = false;
                exceptionMessage = e.toString();
            }
            if (up_success_flag) {
                return resultFinal;
            } else {
                return exceptionMessage;
            }
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
            super.onPostExecute(result);
            dialog.dismiss();
            if (isError) {
                Toast.makeText(getApplicationContext(), "Uploaded Successfully with some problem ", Toast.LENGTH_SHORT).show();
            } else {
                if (result.contains(CommonString1.KEY_SUCCESS)) {
                    if (upload_status) {
                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertMessage message = new AlertMessage(UploadDataActivity.this, AlertMessage.MESSAGE_UPLOAD_DATA, "success", null);
                        message.showMessage();
                    }
                } else {
                    AlertMessage message = new AlertMessage(UploadDataActivity.this, "Error in uploading :" + result, "success", null);
                    message.showMessage();
                }
            }
        }
    }

    class Data {
        int value;
        String name;
    }

    public String UploadImage(String path, String folder_path) throws Exception {

        errormsg = "";
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(Path + path, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(Path + path, o2);

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeBytes(ba);

        SoapObject request = new SoapObject(CommonString1.NAMESPACE, CommonString1.METHOD_UPLOAD_IMAGE);
        String[] split = path.split("/");
        String path1 = split[split.length - 1];

        request.addProperty("img", ba1);
        request.addProperty("name", path1);
        request.addProperty("FolderName", folder_path);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString1.URL);
        androidHttpTransport.call(CommonString1.SOAP_ACTION_UPLOAD_IMAGE, envelope);

        Object result = envelope.getResponse();

        if (!result.toString().equalsIgnoreCase(CommonString1.KEY_SUCCESS)) {
            if (result.toString().equalsIgnoreCase(CommonString1.KEY_FALSE)) {
                return CommonString1.KEY_FALSE;
            }

            SAXParserFactory saxPF = SAXParserFactory.newInstance();
            SAXParser saxP = saxPF.newSAXParser();
            XMLReader xmlR = saxP.getXMLReader();

            // for failure
            FailureXMLHandler failureXMLHandler = new FailureXMLHandler();
            xmlR.setContentHandler(failureXMLHandler);

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(result.toString()));
            xmlR.parse(is);

            failureGetterSetter = failureXMLHandler.getFailureGetterSetter();

            if (failureGetterSetter.getStatus().equalsIgnoreCase(CommonString1.KEY_FAILURE)) {
                errormsg = failureGetterSetter.getErrorMsg();
                return CommonString1.KEY_FAILURE;
            }
        } else {
            new File(Path + path).delete();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(CommonString1.KEY_STOREVISITED_STATUS, "");
            editor.commit();
        }
        return result.toString();
    }
}
