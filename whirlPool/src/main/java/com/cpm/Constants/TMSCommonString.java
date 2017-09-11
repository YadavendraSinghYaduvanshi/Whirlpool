package com.cpm.Constants;

public class TMSCommonString {

	/**
	 * String that define name of database-----------------
	 */

	public static final String DATABASE_NAME = "htcambdatabase";
	public static final String APP_VERSION = "1.0";
	public static final String UPLOAD_DOC = "UploadDocument";
	public static final String UPLOAD_DOC_CHECK = "CheckDocumentImgStatus";
	
	public static final String STORE_STATUS = "U";
	public static final String STORE_STATUS_NORMAL = "N";
	public static final String STORE_STATUS_PARTIAL = "P";
	public static final String STORE_DATA_UPLOADED = "D";

	public static final String KEY_SUCCESS = "Success";
	public static final String KEY_FALSE = "False";
	public static final String KEY_FAILURE = "Failure";
	public static final String KEY_CHANGED = "Changed";
	public static final String KEY_IMAGEURL_MSG = "IMAGEURL_MSG";
	public static final String KEY_USERNAME = "UserName";
	public static final String KEY_COUNT = "Count";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_PATH = "path";
	public static final String KEY_VERSION = "version";
	public static final String KEY_FRIEND_ID = "FRIEND_ID";
	public static final String KEY_STATUS = "STATUS";
	public static final String KEY_MSZ_SERVICE_STATUS = "NO";
	public static final String KEY_UPLOAD_SEND_FRNREQ_STATUS = "NO";
	public static final String KEY_UPLOAD_FRN_ACCEPTED_STATUS = "NO";

	public static final String MESSAGE_SOCKET_EXCEPTION = "Network Busy.Please try after sometime";

	public static final String NAMESPACE = "http://tempuri.org/";
	 //public static final String URL_LOCAL =
	//"http://10.200.20.133/HTCTalent/HTCTalent.asmx";
	//public static final String URL = "http://parinaam.in/HTC/HTCTalent.asmx";
	

			//public static final String URL = "http://htcbd.parinaam.in/HTCTalent.asmx";
	public static final String URL = "http://Htc.parinaam.in/HTCTalent.asmx";
	//public static final String URL = "http://htcsg.parinaam.in/HTCTalent.asmx";
	//public static final String URL = "http://htcth.parinaam.in/HTCTalent.asmx";
	//public static final String URL = "http://htcmy.parinaam.in/HTCTalent.asmx";
	
	//public static final String URL = "http://testbed.parinaam.in/htctalent.asmx";
	
	//public static final String METHOD_NAME_USERINFO = "DOWNLOAD_TMS_Login_V1";
	//public static final String METHOD_NAME_USERINFO = "DOWNLOAD_TMS_USERPROFILE";
	
	public static final String METHOD_NAME_USERINFO = "DOWNLOAD_TMS_USERPROFILE_WITH_STORE_LATITUDE";
	
	
	public static final String METHOD_NAME_RESULT = "Insert_QuizAnswerDetails";
	public static final String SOAP_ACTION_NAME_RESULT= "http://tempuri.org/Insert_QuizAnswerDetails";
	
	//public static final String SOAP_ACTION_USERINFO = "http://tempuri.org/" + METHOD_NAME_USERINFO;
	
	//public static final String SOAP_ACTION_USERINFO = "http://tempuri.org/DOWNLOAD_TMS_USERPROFILE";
	

	public static final String SOAP_ACTION_USERINFO = "http://tempuri.org/DOWNLOAD_TMS_USERPROFILE_WITH_STORE_LATITUDE";
	
	
	public static final String SOAP_ACTION_FRIENDLIST = "http://tempuri.org/DOWNLOAD_TMS_GET_FRIENDLIST";
	public static final String METHOD_NAME_FRIENDLIST = "DOWNLOAD_TMS_GET_FRIENDLIST";

	public static final String SOAP_ACTION_DOWNLOAD_TMS_PERFORMANCE = "http://tempuri.org/DOWNLOAD_TMS_PERFORMANCE";
	public static final String METHOD_NAME_DOWNLOAD_TMS_PERFORMANCE = "DOWNLOAD_TMS_PERFORMANCE";
	
	public static final String METHOD_NAME_DOWNLOAD_TMS_PERFORMANCE_ValueWise = "DOWNLOAD_TMS_PERFORMANCEValueWise";
	public static final String SOAP_ACTION_DOWNLOAD_TMS_PERFORMANCE_ValueWise = "http://tempuri.org/DOWNLOAD_TMS_PERFORMANCEValueWise";

	

	
	public static final String METHOD_NAME_DOWNLOAD_TMS_TOTAL_PERFORMANCE = "DOWNLOAD_TOTAL_TMS_PERFORMANCE";
	public static final String SOAP_ACTION_DOWNLOAD_TMS_TOTAL_PERFORMANCE = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_TMS_TOTAL_PERFORMANCE;

	public static final String METHOD_NAME_DOWNLOAD_TMS_HEROS = "DOWNLOAD_TOP_TMS_PERFORMANCES";
	public static final String SOAP_ACTION_DOWNLOAD_TMS_HEROS = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_TMS_HEROS;

	public static final String METHOD_NAME_DOWNLOAD_TMS_HEROS_REGIONWISE = "DOWNLOAD_TOP_TMS_PERFORMANCE_REGIONWISE";
	public static final String SOAP_ACTION_DOWNLOAD_TMS_HEROS_REGIONWISE = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_TMS_HEROS_REGIONWISE;

	public static final String METHOD_NAME_DOWNLOAD_TMS_HEROS_CITYWISE = "DOWNLOAD_TOP_TMS_PERFORMANCE_CITYWISE";
	public static final String SOAP_ACTION_DOWNLOAD_TMS_HEROS_CITYWISE = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_TMS_HEROS_CITYWISE;

	public static final String METHOD_NAME_DOWNLOAD_RECENT_PERFORMERS = "DOWNLOAD_RECENT_PERFORMERS";
	public static final String SOAP_ACTION_DOWNLOAD_RECENT_PERFORMERS = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_RECENT_PERFORMERS;

	public static final String SOAP_ACTION_DOWNLOAD_TMS_TITLE = "http://tempuri.org/DOWLOAD_Tms_TitleMaster_BY_USER";
	public static final String METHOD_NAME_DOWNLOAD_TMS_TITLE = "DOWLOAD_Tms_TitleMaster_BY_USER";

	public static final String SOAP_ACTION_DOWNLOAD_TMS_TITLE_MASTER = "http://tempuri.org/DOWLOAD_Tms_TitleMasterNew";
	public static final String METHOD_NAME_DOWNLOAD_TMS_TITLE_MASTER = "DOWLOAD_Tms_TitleMasterNew";

	public static final String SOAP_ACTION_DOWNLOAD_TMS_SEND_MESSAGES = "http://tempuri.org/DOWNLOAD_TMS_SEND_MESSAGES_CPM";
	public static final String METHOD_NAME_DOWNLOAD_TMS_SEND_MESSAGES = "DOWNLOAD_TMS_SEND_MESSAGES_CPM";

	public static final String SOAP_ACTION_DOWNLOAD_TMS_SEARCH_FRIEND = "http://tempuri.org/DOWNLOAD_TMS_SEARCH_FRIEND";
	public static final String METHOD_NAME_DOWNLOAD_TMS_SEARCH_FRIEND = "DOWNLOAD_TMS_SEARCH_FRIEND";

	public static final String METHOD_NAME_DOWNLOAD_TMS_FRIENDREQ = "DOWNLOAD_TMS_FrdRequestListByReqBy";
	public static final String SOAP_ACTION_DOWNLOAD_TMS_FRIENDREQ = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_TMS_FRIENDREQ;

	public static final String METHOD_NAME_DOWNLOAD_TMS_FRIENDINFo = "DOWNLOAD_FRIEND_DETAIL";
	public static final String SOAP_ACTION_DOWNLOAD_TMS_FRIENDINFO = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_TMS_FRIENDINFo;
	// user updates

	public static final String METHOD_NAME_UPLOAD_TMS_USER_UPDATES = "Upload_TMS_USER_UPDATE";
	public static final String SOAP_ACTION_UPLOAD_TMS_USER_UPDATES = "http://tempuri.org/"
			+ METHOD_NAME_UPLOAD_TMS_USER_UPDATES;
	public static final String METHOD_NAME_DOWNLOAD_TMS_USER_UPDATES = "DOWNLOAD_CURRENT_UPDATES";
	public static final String SOAP_ACTION_DOWNLOAD_TMS_USER_UPDATES = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_TMS_USER_UPDATES;

	// HTC INside

	public static final String METHOD_NAME_DOWNLOAD_HTC_INSIDE = "DOWNLOAD_TMS_SEND_MESSAGES_HTC";
	public static final String SOAP_ACTION_DOWNLOAD_HTC_INSIDE = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_HTC_INSIDE;

	public static final String METHOD_NAME_UPLOAD_UPDATE_LIKES = "UPLOAD_UPDATES_LIKES";
	public static final String SOAP_ACTION_UPLOAD_UPDATE_LIKES = "http://tempuri.org/"
			+ METHOD_NAME_UPLOAD_UPDATE_LIKES;

	public static final String METHOD_NAME_UPLOOAD_MESSAGE = "UPLOAD_TMS_SEND_MESSAGES";
	public static final String SOAP_ACTION_UPLOAD_MESSAGE = "http://tempuri.org/"
			+ METHOD_NAME_UPLOOAD_MESSAGE;
	// Rewards
	public static final String METHOD_NAME_DOWNLOAD_REWARDS = "DOWNLOAD_TMS_EMPLOYEE_REWARDS";
	public static final String SOAP_ACTION_DOWNLOAD_REWARDS = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_REWARDS;
	// Training
	public static final String METHOD_NAME_DOWNLOAD_TRAINING = "DOWLOAD_TMS_TRAINING_MATERIAL";
	public static final String SOAP_ACTION_DOWNLOAD_TRAINING = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_TRAINING;

	public static final String METHOD_NAME_DOWNLOAD_TRAINING_QUIZ = "DOWNLOAD_TRAINING_READY_FOR_QUIZ";
	public static final String SOAP_ACTION_DOWNLOAD_TRAINING_QUIZ = "http://tempuri.org/"
			+ METHOD_NAME_DOWNLOAD_TRAINING_QUIZ;

	// read status of CPM mszs
	public static final String METHOD_NAME_UPLOOAD_MESSAGE_STATUS = "UPLOAD_TMS_MESSAGES_Status_CPM";
	public static final String SOAP_ACTION_UPLOAD_MESSAGE_STATUS = "http://tempuri.org/"
			+ METHOD_NAME_UPLOOAD_MESSAGE_STATUS;

	// read status htc
	public static final String METHOD_NAME_UPLOAD_HTC_MESSAGE_STATUS = "UPLOAD_TMS_MESSAGES_Status_HTC";
	public static final String SOAP_ACTION_UPLOAD_HTC_MESSAGE_STATUS = "http://tempuri.org/"
			+ METHOD_NAME_UPLOAD_HTC_MESSAGE_STATUS;

	// training taken
	public static final String METHOD_NAME_UPLOAD_TRAINING_STATUS = "UPLOAD_TMS_ADD_Training";
	public static final String SOAP_ACTION_UPLOAD_TRAINING_STATUS = "http://tempuri.org/"
			+ METHOD_NAME_UPLOAD_TRAINING_STATUS;

	// read status of Frns mszs
	public static final String METHOD_NAME_UPLOAD_READ_CHAT_STATUS = "UPLOAD_TMS_MESSAGES_Status_Friend";
	public static final String SOAP_ACTION_UPLOAD_READ_CHAT_STATUS = "http://tempuri.org/"
			+ METHOD_NAME_UPLOAD_READ_CHAT_STATUS;

	// upload profile status
	public static final String METHOD_NAME_UPLOAD_PROFILE = "UPLOAD_TMS_PROFILE";
	public static final String SOAP_ACTION_UPLOAD_PROFILE = "http://tempuri.org/"
			+ METHOD_NAME_UPLOAD_PROFILE;

	// FriendchatMsz

	public static final String METHOD_NAME_CHAT_MESSAGE_DOWNLOAD = "DOWNLOAD_TMS_SEND_MESSAGES_FrdList";
	public static final String SOAP_ACTION_CHAT_MESSAGE_DOWNLOAD = "http://tempuri.org/"
			+ METHOD_NAME_CHAT_MESSAGE_DOWNLOAD;

	public static final String METHOD_NAME_CHAT_MESSAGE_DOWNLOAD_FRNwise = "DOWNLOAD_TMS_SEND_MESSAGES_FrdWise";
	public static final String SOAP_ACTION_CHAT_MESSAGE_DOWNLOAD_FRNwise = "http://tempuri.org/"
			+ METHOD_NAME_CHAT_MESSAGE_DOWNLOAD_FRNwise;

	// accept frn request
	public static final String METHOD_NAME_ACCEPT_FRIEND = "UPLOAD_TMS_ADD_FRIEND_With_Status";
	public static final String SOAP_ACTION_ACCEPT_FRIEND = "http://tempuri.org/"
			+ METHOD_NAME_ACCEPT_FRIEND;

	// send like number
	public static final String METHOD_NAME_LIKE_HEROS = "UPLOAD_UPDATES_LIKES";
	public static final String SOAP_ACTION_LIKE_HEROS = "http://tempuri.org/"
			+ METHOD_NAME_LIKE_HEROS;

	// Send Friend request

	public static final String METHOD_NAME_SEND_REQUEST = "UPLOAD_TMS_ADD_FRIEND_REQUEST";
	public static final String SOAP_ACTION_SEND_REQUEST = "http://tempuri.org/"
			+ METHOD_NAME_SEND_REQUEST;

	// upload profile image

	public static final String METHOD_Get_DR_POSM_THEME_IMAGES = "GetTMSImage";
	public static final String SOAP_ACTION_Get_DR_POSM_THEME_IMAGES = "http://tempuri.org/"
			+ METHOD_Get_DR_POSM_THEME_IMAGES;

	public static final String KEY_ID = "ID";

	// Profile Table
	public static final String TABLE_USER_DETAILS = "PROFILE_DETAILS";

	public static final String KEY_EMP_ID = "EMP_ID";
	public static final String KEY_EMP_NAME = "EMP_NAME";
	public static final String KEY_STORE_ID = "STORE_ID";
	public static final String KEY_STORE_NAME = "STORE_NAME";
	public static final String KEY_DESIGNATION = "DESIGNATION";
	public static final String KEY_EMAIL = "EMAIL";
	public static final String KEY_MOBILE = "MOBILE";
	public static final String KEY_IMAGEURL = "IMAGEURL";
	public static final String KEY_APPLICATION = "APPLICATION";
	public static final String KEY_APK_VERSION = "APK_VERSION";
	public static final String KEY_APK_PATH = "APK_PATH";
	public static final String KEY_DATE = "DATE";
	public static final String KEY_ISD_DATE = "ISD_DATE";
	public static final String KEY_FACEBOOK = "FACEBOOK";
	
	
	public static final String store_latitude = "STORE_LATITUDE";
	public static final String store_longitude = "STORE_LONGITUDE";
	
	
	

	public static final String CREATE_TABLE_USER_DETAILS = "CREATE TABLE "
			+ TABLE_USER_DETAILS + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_EMP_ID + " VARCHAR,"
			+ KEY_EMP_NAME + " VARCHAR," + KEY_STORE_ID + " VARCHAR,"
			+ KEY_STORE_NAME + " VARCHAR," + KEY_DESIGNATION + " VARCHAR,"
			+ KEY_EMAIL + " VARCHAR," + KEY_MOBILE + " VARCHAR," + KEY_IMAGEURL
			+ " VARCHAR," + KEY_APPLICATION + " VARCHAR," + KEY_APK_VERSION
			+ " VARCHAR," + KEY_APK_PATH + " VARCHAR," + "STORE_LATITUDE" + " VARCHAR,"+ "STORE_LONGITUDE" + " VARCHAR,"
			
			+ KEY_DATE + " VARCHAR)";

	public static final String TABLE_UPDATE_USER_DETAILS = "UPDATE_USER_DETAILS";
	public static final String CREATE_TABLE_UPDATE_USER_DETAILS = "CREATE TABLE "
			+ TABLE_UPDATE_USER_DETAILS
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_EMAIL
			+ " VARCHAR,"
			+ KEY_MOBILE
			+ " VARCHAR,"
			+ KEY_IMAGEURL
			+ " VARCHAR,"
			+ KEY_FACEBOOK + " VARCHAR)";
	// Refresh Rate

	public static final String TABLE_REFRESH = "REFRESH";
	public static final String KEY_FIELD = "FIELD";
	public static final String KEY_REFRESH_RATE = "REFRESH_RATE";
	public static final String KEY_LAST_UPDATE = "LAST_UPDATE";
	public static final String CREATE_TABLE_REFRESH = "CREATE TABLE "
			+ TABLE_REFRESH + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_FIELD + " VARCHAR,"
			+ KEY_REFRESH_RATE + " VARCHAR," + KEY_LAST_UPDATE + " VARCHAR)";

	// Friendlist table
	public static final String TABLE_FRIEND_LIST = "FRIEND_LIST";

	public static final String CREATE_TABLE_FRIEND_LIST = "CREATE TABLE "
			+ TABLE_FRIEND_LIST + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_EMP_ID + " VARCHAR,"
			+ KEY_EMP_NAME + " VARCHAR," + KEY_DESIGNATION + " VARCHAR,"
			+ KEY_EMAIL + " VARCHAR," + KEY_STORE_NAME + " VARCHAR,"
			+ KEY_MOBILE + " VARCHAR," + KEY_IMAGEURL + " VARCHAR)";

	// Search Frn
	public static final String TABLE_SEARCH_FRIEND_LIST = "SEARCH_FRIEND_LIST";

	public static final String CREATE_TABLE_SEARCH_FRIEND_LIST = "CREATE TABLE "
			+ TABLE_SEARCH_FRIEND_LIST
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_EMP_ID
			+ " VARCHAR,"
			+ KEY_EMP_NAME
			+ " VARCHAR,"
			+ KEY_DESIGNATION
			+ " VARCHAR,"
			+ KEY_STATUS + " VARCHAR," + KEY_IMAGEURL + " VARCHAR)";
	// Frn Req
	public static final String TABLE_FRIEND_REQ = "FRIEND_REQ";

	public static final String KEY_REQUESTED_BY = "REQUESTED_BY";
	public static final String KEY_REQUESTED_TO = "REQUESTED_TO";
	public static final String KEY_COMMON_ID = "COMMON_ID";

	public static final String CREATE_TABLE_FRIEND_REQ = "CREATE TABLE "
			+ TABLE_FRIEND_REQ + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_REQUESTED_BY
			+ " VARCHAR," + KEY_COMMON_ID + " VARCHAR," + KEY_EMP_NAME
			+ " VARCHAR," + KEY_STATUS + " VARCHAR," + KEY_DESIGNATION
			+ " VARCHAR)";

	// Title

	public static final String TABLE_TITLE_LIST = "TITLE_LIST";
	public static final String TABLE_TITLE_LIST_USER = "TITLE_LIST_USER";

	public static final String TABLE_TITLE_MASTER_LIST = "TITLE_MASTER_LIST";

	public static final String KEY_TITLE_ID = "TITLE_ID";
	public static final String KEY_TITLE = "TITLE";
	public static final String KEY_DESC = "DESC";

	public static final String CREATE_TABLE_TITLE_LIST = "CREATE TABLE "
			+ TABLE_TITLE_LIST + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_EMP_ID + " VARCHAR,"
			+ KEY_TITLE_ID + " VARCHAR," + KEY_TITLE + " VARCHAR," + KEY_DESC
			+ " VARCHAR," + KEY_DATE + " VARCHAR)";

	public static final String CREATE_TABLE_TITLE_LIST_USER = "CREATE TABLE "
			+ TABLE_TITLE_LIST_USER + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_EMP_ID + " VARCHAR,"
			+ KEY_TITLE_ID + " VARCHAR," + KEY_TITLE + " VARCHAR," + KEY_DESC
			+ " VARCHAR," + KEY_DATE + " VARCHAR)";
	// perfromane update
	public static final String TABLE_PERFORMANCE_UPDATE = "PERFORMANCE_UPDATE";
	public static final String KEY_LIKES = "LIKES";
	public static final String KEY_PARAMETER = "PARAMETER";
	public static final String KEY_TEXT = "TEXT";
	public static final String CREATE_TABLE_PERFORMANCE_UPDATE = "CREATE TABLE "
			+ TABLE_PERFORMANCE_UPDATE
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_EMP_ID
			+ " VARCHAR,"
			+ KEY_COMMON_ID
			+ " VARCHAR,"
			+ KEY_LIKES
			+ " INTEGER,"
			+ KEY_STATUS + " VARCHAR," + KEY_TEXT + " VARCHAR)";

	// updates
	public static final String TABLE_USER_UPDATES = "USER_UPDATES";

	public static final String CREATE_TABLE_USER_UPDATES = "CREATE TABLE "
			+ TABLE_USER_UPDATES + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_EMP_ID + " VARCHAR,"
			+ KEY_COMMON_ID + " VARCHAR," + KEY_LIKES + " VARCHAR,"
			+ KEY_STATUS + " VARCHAR," + KEY_TEXT + " VARCHAR)";

	// Reward
	public static final String TABLE_REWARD = "REWARD_LIST";
	public static final String TABLE_REWARD_USER = "REWARD_LIST_USER";
	public static final String KEY_REWARD = "REWARD";

	public static final String CREATE_TABLE_REWARD = "CREATE TABLE "
			+ TABLE_REWARD + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_REWARD + " VARCHAR,"
			+ KEY_DESC + " VARCHAR)";

	public static final String CREATE_TABLE_REWARD_USER = "CREATE TABLE "
			+ TABLE_REWARD_USER + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_REWARD + " VARCHAR,"
			+ KEY_DESC + " VARCHAR)";

	// Training

	public static final String TABLE_TRAINING = "TRAINING_LIST";
	public static final String TABLE_TRAINING_QUESTION = "TRAINING_QUESTION";
	public static final String KEY_TRAINING_ID = "TRAINING_ID";
	public static final String KEY_TOPIC_NAME = "TOPIC_NAME";
	public static final String KEY_TOPIC_DESC = "TOPIC_DESC";
	public static final String KEY_FILE_ATTACHED = "FILE_ATTACHED";
	public static final String KEY_FILE_TYPE = "FILE_TYPE";
	public static final String KEY_FILE_PATH = "FILE_PATH";
	
	
	public static final String KEY_SUBJECT_ID = "SUBJECT_ID";
	public static final String KEY_SUBJECT_NAME = "SUBJECT_NAME";
	
	
	
	
	public static final String KEY_INS = "INS";
	public static final String KEY_DESCR = "DESCR";
	public static final String KEY_NOQ = "NOQ";
	public static final String KEY_QUIZ_ID = "QUIZ_ID";
	
	
	public static final String TABLE_QUIZ_SUBJECT = "QUIZ_SUBJECT";
	
	public static final String TABLE_QUIZ_ANSWER_RESULT = "ANSWER_RESULT";
	
	public static final String TABLE_QUIZ_QUESTION = "QUIZ_QUESTION";
	public static final String TABLE_QUIZ_ANSWER = "QUIZ_ANSWER";
	
	public static final String CREATE_TABLE_QUIZ_SUBJECT = "CREATE TABLE "
			+ TABLE_QUIZ_SUBJECT + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_SUBJECT_ID + " VARCHAR," + KEY_INS + " VARCHAR," + KEY_DESCR + " VARCHAR,"
			+ KEY_NOQ + " VARCHAR," + KEY_QUIZ_ID + " VARCHAR,"
			+ KEY_SUBJECT_NAME + " VARCHAR)";
	
	public static final String KEY_QUESTION_ID = "QUESTIONID";
	public static final String KEY_QUESTION_DESC = "DESCR";
	public static final String KEY_QUESTION_TYPE = "TYPE";
	
	
	public static final String KEY_CORRECT = "CORRECT";
	public static final String KEY_ANSWER_DESC = "ANSDESCR";
	public static final String KEY_ANSWER_ID = "ANS";
	
	public static final String CREATE_TABLE_QUESTION = "CREATE TABLE "
			+ TABLE_QUIZ_QUESTION + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_QUESTION_ID + " VARCHAR," + KEY_QUESTION_DESC + " VARCHAR," 
			 + KEY_QUIZ_ID + " VARCHAR,"
			+ KEY_QUESTION_TYPE + " VARCHAR)";
	
	
	
	
	
	public static final String CREATE_TABLE_ANSWER_RESULT = "CREATE TABLE "
			+ TABLE_QUIZ_ANSWER_RESULT + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_QUESTION_ID + " VARCHAR," + KEY_ANSWER_ID + " VARCHAR," 
			
			+ KEY_QUIZ_ID + " VARCHAR)";
	
	
	
	
	
	public static final String CREATE_TABLE_ANSWER = "CREATE TABLE "
			+ TABLE_QUIZ_ANSWER + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_QUESTION_ID + " VARCHAR," + KEY_ANSWER_DESC + " VARCHAR," 
			 + KEY_QUIZ_ID + " VARCHAR," + KEY_ANSWER_ID + " VARCHAR,"
			+ KEY_CORRECT + " VARCHAR)";
	
	
	
	
	public static final String CREATE_TABLE_TRAINING = "CREATE TABLE "
			+ TABLE_TRAINING + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_TRAINING_ID
			+ " VARCHAR," + KEY_TOPIC_NAME + " VARCHAR," + KEY_TOPIC_DESC
			+ " VARCHAR," + KEY_FILE_ATTACHED + " VARCHAR," + KEY_FILE_TYPE
			+ " VARCHAR," + KEY_STATUS + " VARCHAR," + KEY_FILE_PATH
			+ " VARCHAR)";

	public static final String CREATE_TABLE_TRAINING_QUESTION = "CREATE TABLE "
			+ TABLE_TRAINING_QUESTION + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_TOPIC_NAME
			+ " VARCHAR)";

	public static final String KEY_IMAGENAME_ENABLE = "IMAGE_ENABLE";
	public static final String KEY_IMAGENAME_DISABLE = "IMAGE_DISABLE";
	public static final String KEY_IMAGENAME_HIGH = "IMAGE_HIGH";

	public static final String CREATE_TABLE_TITLE_MASTER_LIST = "CREATE TABLE "
			+ TABLE_TITLE_MASTER_LIST + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_TITLE_ID
			+ " VARCHAR," + KEY_IMAGENAME_DISABLE + " VARCHAR," + KEY_DESC
			+ " VARCHAR," + KEY_DATE + " VARCHAR," + KEY_IMAGENAME_ENABLE
			+ " VARCHAR," + KEY_IMAGENAME_HIGH + " VARCHAR," + KEY_TITLE
			+ " VARCHAR)";

	// Performance

	public static final String TABLE_ISD_PERFORMANCE = "ISD_PERFORMANCE";
	public static final String TABLE_HEROS_PERFORMANCE_REGION = "HEROS_PERFORMANCE_REGION";
	public static final String TABLE_HEROS_PERFORMANCE_CITY = "HEROS_PERFORMANCE_CITY";
	public static final String TABLE_ISD_TOTAL_PERFORMANCE = "ISD_TOTAL_PERFORMANCE";
	public static final String KEY_ISD_NAME = "ISD_NAME";
	public static final String KEY_TARGET = "TARGET";
	public static final String KEY_ACHEIVEMENT = "ACHEIVEMENT";
	public static final String KEY_MONTH = "MONTH";
	public static final String KEY_YEAR = "YEAR";

	public static final String KEY_PERCENT = "PERCENT";

	public static final String CREATE_TABLE_ISD_PERFORMANCE = "CREATE TABLE "
			+ TABLE_ISD_PERFORMANCE + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_EMP_ID + " VARCHAR,"
			+ KEY_ISD_NAME + " VARCHAR," + KEY_TARGET + " VARCHAR,"
			+ KEY_ACHEIVEMENT + " VARCHAR," + KEY_PERCENT + " VARCHAR)";

	public static final String CREATE_TABLE_HEROS_PERFORMANCE_REGION = "CREATE TABLE "
			+ TABLE_HEROS_PERFORMANCE_REGION
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_EMP_ID
			+ " VARCHAR,"
			+ KEY_ISD_NAME
			+ " VARCHAR,"
			+ KEY_IMAGEURL
			+ " VARCHAR,"
			+ KEY_TARGET
			+ " VARCHAR,"
			+ KEY_ACHEIVEMENT
			+ " VARCHAR,"
			+ KEY_PERCENT + " VARCHAR)";

	public static final String CREATE_TABLE_HEROS_PERFORMANCE_CITY = "CREATE TABLE "
			+ TABLE_HEROS_PERFORMANCE_CITY
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_EMP_ID
			+ " VARCHAR,"
			+ KEY_ISD_NAME
			+ " VARCHAR,"
			+ KEY_IMAGEURL
			+ " VARCHAR,"
			+ KEY_TARGET
			+ " VARCHAR,"
			+ KEY_ACHEIVEMENT
			+ " VARCHAR,"
			+ KEY_PERCENT + " VARCHAR)";

	public static final String CREATE_TABLE_ISD_TOTAL_PERFORMANCE = "CREATE TABLE "
			+ TABLE_ISD_TOTAL_PERFORMANCE
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_EMP_ID
			+ " VARCHAR,"
			+ KEY_ISD_NAME
			+ " VARCHAR,"
			+ KEY_TARGET
			+ " VARCHAR,"
			+ KEY_MONTH
			+ " VARCHAR,"
			+ KEY_YEAR
			+ " VARCHAR,"
			+ KEY_ACHEIVEMENT
			+ " VARCHAR," + KEY_PERCENT + " VARCHAR)";

	// send Message

	public static final String TABLE_OUTBOX = "OUTBOX";

	public static final String KEY_RECEIVER_ID = "RECEIVER_ID";

	public static final String KEY_SENDER_ID = "SENDER_ID";
	public static final String KEY_SENDER = "SENDER";
	public static final String KEY_MESSAGE = "MESSAGE";
	public static final String KEY_TIME = "TIME";
	public static final String KEY_POSITION = "POSITION";

	public static final String CREATE_TABLE_OUTBOX = "CREATE TABLE "
			+ TABLE_OUTBOX + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_RECEIVER_ID
			+ " VARCHAR," + KEY_MESSAGE + " VARCHAR," + KEY_IMAGEURL
			+ " VARCHAR," + KEY_DATE + " VARCHAR," + KEY_TIME + " VARCHAR,"
			+ KEY_POSITION + " VARCHAR," + KEY_STATUS + " VARCHAR)";

	// Message Inbox

	public static final String TABLE_INBOX = "INBOX";
	public static final String KEY_SUBJECT = "SUBJECT";

	public static final String CREATE_TABLE_INBOX = "CREATE TABLE "
			+ TABLE_INBOX + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_SENDER_ID
			+ " VARCHAR," + KEY_SENDER + " VARCHAR," + KEY_MESSAGE
			+ " VARCHAR," + KEY_STATUS + " VARCHAR," + KEY_DATE + " VARCHAR,"
			+ KEY_TIME + " VARCHAR," + KEY_COMMON_ID + " VARCHAR,"
			+ KEY_SUBJECT + " VARCHAR," + KEY_IMAGEURL + " VARCHAR)";

	// HTC Inbox
	public static final String TABLE_HTC_INBOX = "HTC_INBOX";

	public static final String CREATE_TABLE_HTC_INBOX = "CREATE TABLE "
			+ TABLE_HTC_INBOX + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_SENDER_ID
			+ " VARCHAR," + KEY_SENDER + " VARCHAR," + KEY_MESSAGE
			+ " VARCHAR," + KEY_STATUS + " VARCHAR," + KEY_DATE + " VARCHAR,"
			+ KEY_TIME + " VARCHAR," + KEY_COMMON_ID + " VARCHAR,"
			+ KEY_SUBJECT + " VARCHAR," + KEY_IMAGEURL + " VARCHAR)";
	// FriendchatInbox

	public static final String TABLE_FRN_CHAT_INBOX = "FRN_CHAT_INBOX";

	public static final String CREATE_TABLE_FRN_CHAT_INBOX = "CREATE TABLE "
			+ TABLE_FRN_CHAT_INBOX + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_SENDER_ID
			+ " VARCHAR," + KEY_SENDER + " VARCHAR," + KEY_MESSAGE
			+ " VARCHAR," + KEY_STATUS + " VARCHAR," + KEY_POSITION
			+ " VARCHAR," + KEY_DATE + " VARCHAR," + KEY_TIME + " VARCHAR,"
			+ KEY_COMMON_ID + " VARCHAR," + KEY_IMAGEURL + " VARCHAR)";

}
