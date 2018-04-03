package com.cpm.Constants;

public class CommonString {

	/**
	 * Variable for storing current location coordinate
	 */

	public static double LATITUDE = 33.222947;
	public static double LONGITUDE = 121.47175;

	/**
	 * String that define name of database-----------------
	 */

	public static final String DATABASE_NAME = "htcfinalseni30nov";

	/**
	 * String that define name of table used in project----------
	 */

	 public static final String TABLE_COVERAGE_INFO = "COVERAGE_INFO";

	public static final String TABLE_ATTANDANCE_INFO = "ATTANDANCE_INFO1";

	public static final String TABLE_MODEL_DEMO = "MODEL_DEMO";
	



	public static final String TABLE_INTRESTED_PEOPLE = "INTRESTED_PEOPLE";

	public static final String TABLE_MODEL_SALES = "MODEL_SALES";

	public static final String TABLE_TRAINING_ATTENDED = "TRAINING_ATTENDED";

	public static final String TABLE_TRAINING_NEEDED = "TRAINING_NEEDED";

	public static final String TABLE_COMPTITIOR_INFO = "COMPTITIOR_INFO";

	public static final String TABLE_KEY_MODEL_DATA = "KEY_MODEL_DATA";

	public static final String TABLE_OTHER_MODEL_DATA = "OTHER_MODEL_DATA";
	
	public static final String TABLE_DEMO_MODEL_DATA = "DEMO_MODEL_DATA";
	
	public static final String TABLE_DUMMY_MODEL_DATA = "DUMMY_MODEL_DATA";

	public static final String TABLE_POSM_DATA = "POSM_DATA";

	public static final String TABLE_PRICING_DATA = "PRICING_DATA";

	public static final String TABLE_PROMOTION_DATA = "PROMOTION_DATA";
	
	public static final String TABLE_MAIL = "MAIL_INBOX";
	
	public static final String TABLE_MAIL_SENT = "MAIL_SENT";
	
	public static final String TABLE_PERFORMANCE = "PERFORMANCE";
	
	
	

	public static final String CREATE_TABLE_COVERAGE_INFO = "CREATE TABLE COVERAGE_INFO (ID INTEGER PRIMARY KEY AUTOINCREMENT ,STORE_ID INTEGER,REPORTDATE VARCHAR, EMP_ID VARCHAR,USER_NAME VARCHAR,UPLOAD_STATUS VARCHAR)";

	public static final String CREATE_TABLE_ATTANDANCE_INFO = "CREATE TABLE ATTANDANCE_INFO1 (ID INTEGER PRIMARY KEY AUTOINCREMENT ,EMPID VARCHAR, ATTDATE VARCHAR, INTIME VARCHAR, STATUS VARCHAR,LAT VARCHAR ,LONG VARCHAR )";

	public static final String CREATE_TABLE_MODEL_DEMO = "CREATE TABLE MODEL_DEMO (MID INTEGER,MODEL_ID INTEGER, NO_OF_DEMO INTEGER,MODEL_NAME VARCHAR)";
	
	

	public static final String CREATE_TABLE_INTRESTED_PEOPLE = "CREATE TABLE INTRESTED_PEOPLE (MID INTEGER,NAME VARCHAR, ADDRESS VARCHAR,MOBILENO String,EMAIL VARCHAR,MODEL_ID INTEGER,MODEL_ID_1 INTEGER,MODEL_ID_2 INTEGER,MODEL_NAME VARCHAR,URL VARCHAR)";

	public static final String CREATE_TABLE_MODEL_SALES = "CREATE TABLE MODEL_SALES (MID INTEGER,MODEL_ID INTEGER, IMEI VARCHAR,MODEL_NAME VARCHAR, INVOICEVAL VARCHAR, INVOICEIMG VARCHAR)";

	public static final String CREATE_TABLE_TRAINING_ATTENDED = "CREATE TABLE TRAINING_ATTENDED (MID INTEGER, TRAINING VARCHAR,TRAINER VARCHAR,RATE_MATERIAL VARCHAR,RATE_TRAINER VARCHAR,TRAINING_ID INTEGER,TRAINER_ID INTEGER)";

	public static final String CREATE_TABLE_TRAINING_NEEDED = "CREATE TABLE TRAINING_NEEDED (MID INTEGER, TRAINING VARCHAR,TRAINING_ID INTEGER)";

	public static final String CREATE_TABLE_COMPTITIOR_INFO = "CREATE TABLE COMPTITIOR_INFO (MID INTEGER,COMPTITIOR_NAME VARCHAR,COMPTITIOR_ID INTEGER,DEVICE VARCHAR,DEVICE_ID INTEGER,LUNCH_INFO VARCHAR,PRICE INTEGER,URL VARCHAR)";

	public static final String CREATE_TABLE_KEY_MODEL_DATA = "CREATE TABLE KEY_MODEL_DATA (MID INTEGER, KEY_MODEL_NAME VARCHAR,KEY_MODEL_ID INTEGER,KEY_MODEL_QTNY INTEGER)";

	public static final String CREATE_TABLE_OTHER_MODEL_DATA = "CREATE TABLE OTHER_MODEL_DATA (MID INTEGER,OTHER_MODEL_NAME VARCHAR,OTHER_MODEL_ID INTEGER,OTHER_MODEL_QTNY INTEGER)";

	public static final String CREATE_TABLE_DEMO_MODEL_DATA = "CREATE TABLE DEMO_MODEL_DATA (MID INTEGER,MODEL_NAME VARCHAR,MODEL_ID INTEGER,MODEL_QTNY INTEGER)";

	
	public static final String CREATE_TABLE_DUMMY_MODEL_DATA = "CREATE TABLE DUMMY_MODEL_DATA (MID INTEGER,MODEL_NAME VARCHAR,MODEL_ID INTEGER,MODEL_QTNY INTEGER)";

	
	public static final String CREATE_TABLE_POSM_DATA = "CREATE TABLE POSM_DATA (MID INTEGER,EMP_ID INTEGER, POSM_NAME VARCHAR,POSM_ID INTEGER,POSM_QTNY INTEGER)";

	public static final String CREATE_TABLE_PRICING_INFO = "CREATE TABLE PRICING_DATA (MID INTEGER, COMPTITIOR_NAME VARCHAR,COMPTITIOR_ID INTEGER,PRODUCT VARCHAR,PRICE INTEGER,URL VARCHAR)";

	public static final String CREATE_TABLE_PROMOTION_INFO = "CREATE TABLE PROMOTION_DATA (MID INTEGER, COMPTITIOR_NAME VARCHAR,COMPTITIOR_ID INTEGER,PRODUCT VARCHAR,PROMOTIONINFO VARCHAR,URL VARCHAR)";

	public static final String CREATE_TABLE_MAIL_INBOX = "CREATE TABLE MAIL_INBOX (ID INTEGER PRIMARY KEY AUTOINCREMENT ,SUBJECT VARCHAR, Message VARCHAR,SENDER VARCHAR,MAIL_STATUS VARCHAR,SEND_DATE VARCHAR,SEND_TIME VARCHAR)";
	
	public static final String CREATE_TABLE_MAIL_SENT = "CREATE TABLE MAIL_SENT (ID INTEGER PRIMARY KEY AUTOINCREMENT ,SUBJECT VARCHAR, Message VARCHAR,SEND_DATE VARCHAR)";
	
	
	public static final String CREATE_TABLE_PERFORMANCE = "CREATE TABLE PERFORMANCE (EMP_ID VARCHAR,NAME VARCHAR,TARGET VARCHAR,ACHEIVMENT VARCHAR,PERCENTAGE VARCHAR)";
	
	
	//////////////////////////////Shahab Geotagging Code/////////////////////////////////////////////////////////
	
	 
	public static final String TABLE_CITY_MASTER = "CITY_MASTER";
	public static final String TABLE_STORE_GEOTAGGING = "STORE_GEOTAGGING";
	public static final String TABLE_STORE_DETAILUSER = "STORE_USER_DETAIL";
	
	
	public static final String CREATE_TABLE_CITY_MASTER = "CREATE TABLE CITY_MASTER (CITY_ID VARCHAR,CITY_NAME VARCHAR)";
	public static final String CREATE_TABLE_STORE_DETAIL = "CREATE TABLE STORE_USER_DETAIL (STORE_ID VARCHAR,STORE_NAME VARCHAR,STORE_ADDRESS VARCHAR,CITY_ID VARCHAR,STATUS VARCHAR,LATITUDE VARCHAR,LONGITUDE VARCHAR)";




	public static final String CREATE_TABLE_STORE_GEOTAGGING = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_STORE_GEOTAGGING
			+ " ("
			+ "KEY_ID"
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"

			+ "STORE_ID"
			+ " VARCHAR,"

			+ "LATITUDE"
			+ " VARCHAR,"

			+ "LONGITUDE"
			+ " VARCHAR,"

			+ "GEO_TAG"
			+ " VARCHAR,"



			+ "FRONT_IMAGE" + " VARCHAR)";










	public static final String SOAP_ACTION_CITY = "http://tempuri.org/CityUserWise";
	public static final String METHOD_NAME_CITY = "CityUserWise";
	
	public static final String SOAP_ACTION_STOREBYUSER = "http://tempuri.org/StoresByUser";
	public static final String METHOD_NAME_STOREBYUSER = "StoresByUser";
	
	public static final String SOAP_ACTION_GEOTAGGINGIMAGE = "http://tempuri.org/GetImage";
	public static final String METHOD_NAME_GEOTAGGINGIMAGE = "GetImage";
	
	public static final String SOAP_ACTION_UPDATESTORE = "http://tempuri.org/UpdateStore";
	
	public static final String SOAP_ACTION_DrUploadXml = "http://tempuri.org/DrUploadXml";
	public static final String METHOD_NAME_UPDATESTORE= "GeoStore";
	public static final String METHOD_COMMON_SERVICE= "DrUploadXml";
	
	//////////////////////////////////End/////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * Table for incoming data
	 */

	public static final String TABLE_MODEL = "MODEL";
	public static final String TABLE_ATTENDANCE_MASTER = "ATTENDANCE_MASTER";

	public static final String TABLE_TRAINER = "TRAINER";

	public static final String TABLE_TRAINING = "TRAINING";

	public static final String TABLE_POSM = "POSM";

	public static final String TABLE_KEY_MODEL = "KEY_MODEL";

	public static final String TABLE_OTHER_MODEL = "OTHER_MODEL";

	public static final String TABLE_COMPTITIOR = "COMPTITIOR";

	public static final String TABLE_DEVICE = "DEVICE";

	public static final String CREATE_TABLE_MODEL = "CREATE TABLE MODEL (ID INTEGER PRIMARY KEY AUTOINCREMENT ,MODEL_NAME VARCHAR, MODEL_ID INTEGER)";
	
	public static final String CREATE_TABLE_ATTENDANCE = "CREATE TABLE ATTENDANCE_MASTER (ID INTEGER PRIMARY KEY AUTOINCREMENT ,ATT_NAME VARCHAR, SYMBOL VARCHAR)";

	public static final String CREATE_TABLE_TRAINER = "CREATE TABLE TRAINER (ID INTEGER PRIMARY KEY AUTOINCREMENT ,TRAINER_NAME VARCHAR, TRAINER_ID INTEGER)";

	public static final String CREATE_TABLE_TRAINING = "CREATE TABLE TRAINING (ID INTEGER PRIMARY KEY AUTOINCREMENT ,TRAINING_NAME VARCHAR, TRAINING_ID INTEGER)";

	public static final String CREATE_TABLE_POSM = "CREATE TABLE POSM (POSM_NAME VARCHAR, POSM_ID INTEGER)";

	public static final String CREATE_TABLE_KEY_MODEL = "CREATE TABLE KEY_MODEL (KEY_MODEL VARCHAR, KEY_MODEL_ID INTEGER)";

	public static final String CREATE_TABLE_OTHER_MODEL = "CREATE TABLE OTHER_MODEL (OTHER_MODEL VARCHAR, OTHER_MODEL_ID INTEGER)";

	public static final String CREATE_TABLE_COMPTITIOR = "CREATE TABLE COMPTITIOR (ID INTEGER PRIMARY KEY AUTOINCREMENT ,COMPTITIOR VARCHAR, COMPTITIOR_ID INTEGER)";

	public static final String CREATE_TABLE_DEVICE = "CREATE TABLE DEVICE (ID INTEGER PRIMARY KEY AUTOINCREMENT ,DEVICE VARCHAR, DEVICE_ID INTEGER)";

	/**
	 * URL ,Name space,Soap action and method name for web services(downloading)
	 */

	public static final String SOAP_ACTION = "http://tempuri.org/ModelInfo";
	
	public static final String SOAP_ACTION_UPLOAD_DOC = "http://tempuri.org/UploadDocument";
	
	public static final String SOAP_ACTION_UPLOAD_DOC_CHECK = "http://tempuri.org/CheckDocumentImgStatus";
	
	public static final String METHOD_NAME = "ModelInfo";
	public static final String NAMESPACE = "http://tempuri.org/";
	public static final String URL1 ="http://tc.parinaam.in/tataservice.asmx";
	//public static final String URL1 ="http://htcsg.parinaam.in/webservices.asmx";
	//public static final String URL1 ="http://htcbd.parinaam.in/webservices.asmx";
	
	


	
//lll
	
	//public static final String URL_1 = "http://Htc.parinaam.in/WebServices.asmx";
	
	
	//ashish modi url
	
	
//	public static final String URL1 = "http://testbed.parinaam.in/HTCTALENT.ASMX";
//	
	public static final String URL_NEW_FOR_STATUS = "http://Htc.parinaam.in/HTCTalent.asmx";
	
	
	
	
	//public static final String URL = "http://10.200.20.133/HTCAMB/WebServices.asmx";

	public static final String METHOD_NAME_ATTENDANCE_TYPE = "DownloadATTENDANCE_TYPE";
	public static final String SOAP_ACTION_ATTENDANCE_TYPE = "http://tempuri.org/"
			+ METHOD_NAME_ATTENDANCE_TYPE;
	
	public static final String METHOD_NAME_IMEI_COUNT = "DowloadIMEICountByISD";
	public static final String SOAP_ACTION_IMEI_COUNT = "http://tempuri.org/"
			+ METHOD_NAME_IMEI_COUNT;
	
	
	public static final String SOAP_ACTION1 = "http://tempuri.org/TrainerInfo";
	public static final String METHOD_NAME1 = "TrainerInfo";

	public static final String SOAP_ACTION2 = "http://tempuri.org/TrainingInfo";
	public static final String METHOD_NAME2 = "TrainingInfo";

	public static final String SOAP_ACTION3 = "http://tempuri.org/PosmDetail";
	public static final String METHOD_NAME3 = "PosmDetail";

	public static final String SOAP_ACTION4 = "http://tempuri.org/KeyModel";
	public static final String METHOD_NAME4 = "KeyModel";

	public static final String SOAP_ACTION5 = "http://tempuri.org/OtherModel";
	public static final String METHOD_NAME5 = "OtherModel";

	public static final String SOAP_ACTION6 = "http://tempuri.org/CompaniesName";
	public static final String METHOD_NAME6 = "CompaniesName";

	public static final String SOAP_ACTION7 = "http://tempuri.org/DeviceTypes";
	public static final String METHOD_NAME7 = "DeviceTypes";

	public static final String SOAP_ACTION8 = "http://tempuri.org/UserFlagSet";
	
	public static final String SOAP_ACTION10 = "http://tempuri.org/DOWNLOAD_Quiz_Subject";
	
	public static final String SOAP_ACTION11 = "http://tempuri.org/DOWNLOAD_QuizMaster";
	public static final String SOAP_ACTION12 = "http://tempuri.org/DOWNLOAD_QuizQuestions";
	public static final String SOAP_ACTION13 = "http://tempuri.org/DOWNLOAD_QuizAnswers";
	
	public static final String SOAP_ACTION14 = "http://tempuri.org/MerchandiserStoreMessage";	
	
	public static final String SOAP_ACTION15 = "http://tempuri.org/UpdateMerchandiserStoreMessageStatus";
	
	
	
	
	
	
	
	
	
	
	public static final String METHOD_NAME8 = "UserFlagSet";
	
	public static final String METHOD_NAME10 = "DOWNLOAD_Quiz_Subject";
	
	public static final String METHOD_NAME11 = "DOWNLOAD_QuizMaster";
	
	public static final String METHOD_NAME12 = "DOWNLOAD_QuizQuestions";
	
	public static final String METHOD_NAME13 = "DOWNLOAD_QuizAnswers";
	public static final String METHOD_NAME14 = "MerchandiserStoreMessage";
	
	public static final String METHOD_NAME15 = "UpdateMerchandiserStoreMessageStatus";

	public static final String SOAP_ACTION9 = "http://tempuri.org/AttendenceWithVersion";
	public static final String METHOD_NAME9 = "AttendenceWithVersion";

	public static final String SOAP_ACTION_UDETAIL = "http://tempuri.org/GetStoreDetails";
	public static final String METHOD_NAME_UDETAIL = "GetStoreDetails";

	public static final String SOAP_ACTION_FLAG = "http://tempuri.org/FlagSet";
	public static final String METHOD_NAME_FLAG = "FlagSet";
	
	public static final String SOAP_ACTION_PERFORMANCE = "http://tempuri.org/Performance";
	public static final String METHOD_NAME_PERFORMANCE = "Performance";
	
	public static final String SOAP_ACTION_INBOXDATA = "http://tempuri.org/MesssagecentredetailbyEmpid";
	public static final String METHOD_NAME_INBOXDATA = "MesssagecentredetailbyEmpid";

	
	public static final String SOAP_ACTION_MAIL_STATUS= "http://tempuri.org/EmailStatus_New";
	public static final String METHOD_NAME_MAIL_STATUS = "EmailStatus_New";
	
	public static final String SOAP_ACTION_MAIL_REPLY= "http://tempuri.org/MailReply";
	public static final String METHOD_NAME_MAIL_REPLY = "MailReply";
	
	

	/**
	 * For uploading
	 */

	public static final String SOAP_ACTION_COVERAGE = "http://tempuri.org/CoverageNew";
	public static final String METHOD_NAME_COVERAGE = "Coverage";
	public static final String METHOD_NAME_COVERAGE_NEW = "CoverageNew";

	public static final String SOAP_ACTION_MODEL_DEMO = "http://tempuri.org/ModelDemo";
	public static final String METHOD_NAME1_MODEL_DEMO = "ModelDemo";

	public static final String SOAP_ACTION2_INTREST = "http://tempuri.org/InterestedPeople";
	public static final String METHOD_NAME2_INTREST = "InterestedPeople";

	public static final String SOAP_ACTION3_TRAINGATT = "http://tempuri.org/TrainingAttended";
	public static final String METHOD_NAME3_TRAINGATT = "TrainingAttended";

	public static final String SOAP_ACTION5_NEEDED = "http://tempuri.org/TrainingNeeded";
	public static final String METHOD_NAME5_NEEDED = "TrainingNeeded";

	public static final String SOAP_ACTION4_SALE = "http://tempuri.org/ModelSales";
	public static final String METHOD_NAME4_SALE = "ModelSales";

	public static final String SOAP_ACTION6_KEYMODEL = "http://tempuri.org/StockKeyModel";
	public static final String METHOD_NAME6_KEYMODEL = "StockKeyModel";

	public static final String SOAP_ACTION7_PRICING = "http://tempuri.org/Pricing";
	public static final String METHOD_NAME7_PRICING = "Pricing";

	public static final String SOAP_ACTION8_PROMOTION = "http://tempuri.org/Promotion";
	public static final String METHOD_NAME8_PROMOTION = "Promotion";

	public static final String SOAP_ACTION8_ATTANDANCE = "http://tempuri.org/AttendenceNew";
	//public static final String METHOD_NAME8_ATTANDANCE = "OffLineAttendence";
	public static final String METHOD_NAME8_ATTANDANCE = "AttendenceNew";

	public static final String SOAP_ACTION7_OTHERMODEL = "http://tempuri.org/StockOthersModel";
	public static final String METHOD_NAME7_OTHERMODEL = "StockOthersModel";

	public static final String SOAP_ACTION8_COMPETITORS = "http://tempuri.org/CompanyUpload";
	public static final String METHOD_NAME8_COMPETITORS = "CompanyUpload";

	public static final String SOAP_ACTION9_MERCHANDISING = "http://tempuri.org/Merchandising";
	public static final String METHOD_NAME9_MERCHANDISING = "Merchandising";
	
	
	public static final String SOAP_ACTION_DEMO_DEVICE = "http://tempuri.org/DR_DEMO_DEVICES";
	public static final String METHOD_NAME_DEMO_DEVICE = "DR_DEMO_DEVICES";

	
	public static final String SOAP_ACTION_DUMMY_DEVICE = "http://tempuri.org/DR_DUMMY_DEVICES";
	public static final String METHOD_NAME_DUMMY_DEVICE = "DR_DUMMY_DEVICES";


}
