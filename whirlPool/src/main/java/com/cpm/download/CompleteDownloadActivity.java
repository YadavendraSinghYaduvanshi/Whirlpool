package com.cpm.download;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cpm.Constants.CommonString1;
import com.cpm.whirlpool.R;
import com.cpm.database.GSKDatabase;
import com.cpm.delegates.TableBean;
import com.cpm.fragment.MainFragment;
import com.cpm.message.AlertMessage;
import com.cpm.xmlGetterSetter.AssetChecklistGetterSetter;
import com.cpm.xmlGetterSetter.AssetMasterGetterSetter;
import com.cpm.xmlGetterSetter.BrandGetterSetter;
import com.cpm.xmlGetterSetter.CategoryMasterGetterSetter;
import com.cpm.xmlGetterSetter.ColdroomClosingGetterSetter;
import com.cpm.xmlGetterSetter.CompanyGetterSetter;
import com.cpm.xmlGetterSetter.DeepFreezerGetterSetter;
import com.cpm.xmlGetterSetter.DeploymentXmlGetterSetter;
import com.cpm.xmlGetterSetter.DesignationGetterSetter;
import com.cpm.xmlGetterSetter.Deviation_Reason;
import com.cpm.xmlGetterSetter.JourneyPlanGetterSetter;
import com.cpm.xmlGetterSetter.MappingAssetChecklistGetterSetter;
import com.cpm.xmlGetterSetter.MappingAssetGetterSetter;
import com.cpm.xmlGetterSetter.MappingAvailabilityGetterSetter;
import com.cpm.xmlGetterSetter.MappingPromotionGetterSetter;
import com.cpm.xmlGetterSetter.MappingStatusWindows;
import com.cpm.xmlGetterSetter.NonWorkingReasonGetterSetter;
import com.cpm.xmlGetterSetter.POSM_MASTERGetterSetter;
import com.cpm.xmlGetterSetter.PerformanceGetterSetter;
import com.cpm.xmlGetterSetter.SkuMasterGetterSetter;
import com.cpm.xmlGetterSetter.Sup_Merchandiser;
import com.cpm.xmlGetterSetter.Sup_Performance;
import com.cpm.xmlGetterSetter.Sup_Window;
import com.cpm.xmlGetterSetter.SupervisorGetterSetter;
import com.cpm.xmlHandler.XMLHandlers;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;

public class CompleteDownloadActivity extends AppCompatActivity {
    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    private Data data;
    int eventType;
/*    SkuMasterGetterSetter skumastergettersetter;
    MappingAvailabilityGetterSetter mappingavailgettersetter;
    MappingStatusWindows mapplingstatuswindows;
    MappingPromotionGetterSetter mappingprormotgettersetter;
    MappingAssetGetterSetter mappingassetgettersetter;
    DeepFreezerGetterSetter deepfreezgettersetter;
    AssetMasterGetterSetter assetmastergettersetter;
    CompanyGetterSetter companyGetterSetter;
    BrandGetterSetter brandGetterSetter;
    CategoryMasterGetterSetter categorygettersetter;
    PerformanceGetterSetter performanceGetterSetter;
    ColdroomClosingGetterSetter closingGetterSetter;
    //MerchndiserGetterSetter merchandiserData;
    AssetChecklistGetterSetter assetChecklistGetterSetter;
    MappingAssetChecklistGetterSetter mappingAssetChecklistGetterSetter;
    //NonWrkingMasterGetterSetter nonworkingData;
    SupervisorGetterSetter ditributorlist;
    //StoreListGetterSetter storeTable;
    DesignationGetterSetter designation;
    JourneyPlanGetterSetter journeyplanMerchan;

    Deviation_Reason deviation_Reason;
    Sup_Window supWin;
    Sup_Performance supperformance;
    Sup_Merchandiser supMerchandiser;*/


    DeploymentXmlGetterSetter deploymentXmlgettersetter;
    JourneyPlanGetterSetter jcpgettersetter;
    NonWorkingReasonGetterSetter nonworkinggettersetter;
    POSM_MASTERGetterSetter posm_mastergettersetter;

    GSKDatabase db;
    TableBean tb;
    String _UserId;
    private SharedPreferences preferences;

    boolean ResultFlag = true;
    String str;

    boolean flag_cold_stock = true;
    boolean promotion_flag = true;
    boolean asset_flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.mainpage);

        setContentView(R.layout.activity_main_menu);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _UserId = preferences.getString(CommonString1.KEY_USERNAME, "");
        tb = new TableBean();
        db = new GSKDatabase(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new BackgroundTask(this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // db.open();
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragment cartfrag = new MainFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, cartfrag).commit();
    }
    class Data {
        int value;
        String name;
    }

    private class BackgroundTask extends AsyncTask<Void, Data, String> {
        private Context context;

        BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom);
            //dialog.setTitle("Download Files");
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @Override
        protected String doInBackground(Void... params) {
            String resultHttp = "";

            try {
                data = new Data();

                // JCP
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                SoapObject request = new SoapObject(CommonString1.NAMESPACE, CommonString1.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "JOURNEY_PLAN");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString1.URL);
                androidHttpTransport.call(CommonString1.SOAP_ACTION_UNIVERSAL, envelope);

                Object result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    jcpgettersetter = XMLHandlers.JCPXMLHandler(xpp, eventType);

                    if (jcpgettersetter.getStore_cd().size() > 0) {
                        resultHttp = CommonString1.KEY_SUCCESS;
                        String jcpTable = jcpgettersetter.getTable_journey_plan();
                        TableBean.setjcptable(jcpTable);
                    } else {
                        return "JOURNEY_PLAN";
                    }
                    data.value = 10;
                    data.name = "JCP Data Downloading";
                }

                publishProgress(data);


                // WINDOW_MASTER data
                request = new SoapObject(CommonString1.NAMESPACE, CommonString1.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "POSM_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString1.URL);
                androidHttpTransport.call(CommonString1.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultmapping = envelope.getResponse();

                if (resultmapping.toString() != null) {
                    xpp.setInput(new StringReader(resultmapping.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    posm_mastergettersetter = XMLHandlers.posm_masterXML(xpp, eventType);

                    if (posm_mastergettersetter.getPOSM_MASTER_table() != null) {
                        String mappingtable = posm_mastergettersetter.getPOSM_MASTER_table();
                        TableBean.setPosm_master_table_Bean(mappingtable);
                    }

                    if (posm_mastergettersetter.getPOSM_CD().size() > 0) {
                        resultHttp = CommonString1.KEY_SUCCESS;

                        data.value = 40;
                        data.name = "POSM_MASTER Data Downloading";
                    } else {
                        return "POSM_MASTER";
                        // promotion_flag = false;
                    }
                }
                publishProgress(data);

                // Deployment status
                request = new SoapObject(CommonString1.NAMESPACE, CommonString1.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "DEPLOYMENT_STATUS");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString1.URL);
                androidHttpTransport.call(CommonString1.SOAP_ACTION_UNIVERSAL, envelope);

                Object deploymentResult = envelope.getResponse();

                if (deploymentResult.toString() != null) {
                    xpp.setInput(new StringReader(deploymentResult.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    deploymentXmlgettersetter = XMLHandlers.deploymentXMLData(xpp, eventType);

                    if (deploymentXmlgettersetter.getDeployment_master_table() != null) {
                        String deploymentTable = deploymentXmlgettersetter.getDeployment_master_table();
                        TableBean.setDeloymentTable(deploymentTable);
                    }

                    if (deploymentXmlgettersetter.getDEPLOY_STATUS_CD().size() > 0) {
                        resultHttp = CommonString1.KEY_SUCCESS;
                        data.value = 70;
                        data.name = "Deployment Data Downloading";
                    } else {
                        return "DEPLOYMENT_STATUS";
                        // promotion_flag = false;
                    }
                }
                publishProgress(data);



                //Non Working Reason data
                request = new SoapObject(CommonString1.NAMESPACE, CommonString1.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "NON_WORKING_REASON");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString1.URL);
                androidHttpTransport.call(CommonString1.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultnonworking = envelope.getResponse();

                if (resultnonworking.toString() != null) {
                    xpp.setInput(new StringReader(resultnonworking.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    nonworkinggettersetter = XMLHandlers.nonWorkinReasonXML(xpp, eventType);

                    if (nonworkinggettersetter.getReason_cd().size() > 0) {
                        resultHttp = CommonString1.KEY_SUCCESS;
                        String nonworkingtable = nonworkinggettersetter.getNonworking_table();
                        TableBean.setNonworkingtable(nonworkingtable);
                    } else {
                        return "NON_WORKING_REASON";
                    }
                    data.value = 90;
                    data.name = "Non Working Reason Downloading";
                }
                publishProgress(data);


                db.open();
                db.insertJCPData(jcpgettersetter);
                db.insertPOSM_MASTERData(posm_mastergettersetter);
                db.insertNonWorkingReasonData(nonworkinggettersetter);
                db.insetDeploymenyData(deploymentXmlgettersetter);

                data.value = 100;
                data.name = "Finishing";
                publishProgress(data);

                //return resultHttp;

            } catch (MalformedURLException e) {
                /*final AlertMessage message = new AlertMessage(CompleteDownloadActivity.this,
                        AlertMessage.MESSAGE_EXCEPTION, "download", e);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        message.showMessage();
                    }
                });*/

                ResultFlag = false;
                str = AlertMessage.MESSAGE_EXCEPTION;
                return AlertMessage.MESSAGE_EXCEPTION;
            } catch (IOException e) {
                /*final AlertMessage message = new AlertMessage(CompleteDownloadActivity.this,
                        AlertMessage.MESSAGE_SOCKETEXCEPTION, "socket", e);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        message.showMessage();
                    }
                });*/
                ResultFlag = false;
                str = AlertMessage.MESSAGE_SOCKETEXCEPTION;
                return AlertMessage.MESSAGE_SOCKETEXCEPTION;

            } catch (Exception e) {
                /*final AlertMessage message = new AlertMessage(CompleteDownloadActivity.this,
                        AlertMessage.MESSAGE_EXCEPTION + e, "download", e);

                e.getMessage();
                e.printStackTrace();
                e.getCause();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        message.showMessage();
                    }
                });*/
                ResultFlag = false;
                str = AlertMessage.MESSAGE_EXCEPTION;
                return AlertMessage.MESSAGE_EXCEPTION;
            }

            if (ResultFlag) {
                return "";
            } else {
                return str;
            }
        }

        @Override
        protected void onProgressUpdate(Data... values) {
            pb.setProgress(values[0].value);
            percentage.setText(values[0].value + "%");
            message.setText(values[0].name);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();

            if (result.equalsIgnoreCase("")) {
                AlertMessage message = new AlertMessage(CompleteDownloadActivity.this,
                        AlertMessage.MESSAGE_DOWNLOAD, "success", null);
                message.showMessage();
            } else {
                AlertMessage message = new AlertMessage(CompleteDownloadActivity.this,
                        AlertMessage.MESSAGE_JCP_FALSE + result, "success", null);
                message.showMessage();
            }
            /*dialog.dismiss();
            if (result.equals(CommonString1.KEY_SUCCESS)) {
                AlertMessage message = new AlertMessage(CompleteDownloadActivity.this,
                        AlertMessage.MESSAGE_DOWNLOAD, "success", null);
                message.showMessage();
            } else {
                AlertMessage message = new AlertMessage(CompleteDownloadActivity.this,
                        AlertMessage.MESSAGE_JCP_FALSE + result, "success", null);
                message.showMessage();
            }*/
        }
    }

}
