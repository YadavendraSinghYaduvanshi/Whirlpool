package com.cpm.dailyentry;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.cpm.Constants.CommonString1;
import com.cpm.database.GSKDatabase;
import com.cpm.delegates.CoverageBean;
import com.cpm.whirlpool.R;
import com.cpm.xmlGetterSetter.AssetInsertdataGetterSetter;
import com.cpm.xmlGetterSetter.ChecklistInsertDataGetterSetter;
import com.cpm.xmlGetterSetter.MappingAssetChecklistGetterSetter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AbsListView.OnScrollListener;

public class AssetActivity extends AppCompatActivity implements OnClickListener{

	boolean checkflag=true;
	List<Integer> checkHeaderArray = new ArrayList<Integer>();

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	Button btnSave;

	List<AssetInsertdataGetterSetter> listDataHeader;
	HashMap<AssetInsertdataGetterSetter, List<AssetInsertdataGetterSetter>> listDataChild;

	ArrayList<AssetInsertdataGetterSetter> categoryData;
	ArrayList<AssetInsertdataGetterSetter> skuData;

	AssetInsertdataGetterSetter insertData=new AssetInsertdataGetterSetter();

	GSKDatabase db;

	private SharedPreferences preferences;

	String store_cd,visit_date,username,intime;

	ImageView img;
	
	boolean ischangedflag=false;

	String _pathforcheck,_path,str;

	private String image1 = "";

	String img1 = "";
	static int grp_position = -1;
	static int child_position = -1;

	ArrayList<ChecklistInsertDataGetterSetter> checklistInsertDataGetterSetters;
	ArrayList<MappingAssetChecklistGetterSetter> mappingChecklistDataGetterSetters;

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.asset_layout);

		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		btnSave=(Button) findViewById(R.id.save_btn);

		img=(ImageView) findViewById(R.id.imgnodata);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		db=new GSKDatabase(getApplicationContext());
		db.open();

		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		store_cd = preferences.getString(CommonString1.KEY_STORE_CD, null);

		visit_date = preferences.getString(CommonString1.KEY_DATE, null);
		username= preferences.getString(CommonString1.KEY_USERNAME, null);
		intime= preferences.getString(CommonString1.KEY_STORE_IN_TIME, "");

		str = CommonString1.FILE_PATH;

		mappingChecklistDataGetterSetters = db.getMapingCheckListData();

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		btnSave.setOnClickListener(this);


		expListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				expListView.invalidateViews();
			}

		});

	}

	private void prepareListData() {
		listDataHeader = new ArrayList<AssetInsertdataGetterSetter>();
		listDataChild = new HashMap<AssetInsertdataGetterSetter, List<AssetInsertdataGetterSetter>>();

		/*categoryData=db.getAssetUpload(store_cd);

		if(categoryData.size()<0){

		}*/



		categoryData=db.getAssetCategoryData(store_cd);

		if(categoryData.size()>0){

			// Adding child data

			for(int i=0;i<categoryData.size();i++){
				listDataHeader.add(categoryData.get(i));

				skuData=db.getAssetDataFromdatabase(store_cd, categoryData.get(i).getCategory_cd());
				if(!(skuData.size()>0) || (skuData.get(0).getAsset()==null) || (skuData.get(0).getAsset().equals(""))){
					skuData=db.getAssetData(categoryData.get(i).getCategory_cd(),store_cd);
				}
				else{
					btnSave.setText("Update");
				}

				List<AssetInsertdataGetterSetter> skulist = new ArrayList<AssetInsertdataGetterSetter>();
				for(int j=0;j<skuData.size();j++){
					skulist.add(skuData.get(j));
				}

				listDataChild.put(listDataHeader.get(i), skulist); // Header, Child data
			}

		}
		else{
			expListView.setVisibility(View.GONE);
			btnSave.setVisibility(View.INVISIBLE);
			img.setVisibility(View.VISIBLE);
		}




	}



	public class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context _context;
		private List<AssetInsertdataGetterSetter> _listDataHeader; // header titles
		// child data in format of header title, child title
		private HashMap<AssetInsertdataGetterSetter, List<AssetInsertdataGetterSetter>> _listDataChild;

		public ExpandableListAdapter(Context context, List<AssetInsertdataGetterSetter> listDataHeader,
				HashMap<AssetInsertdataGetterSetter, List<AssetInsertdataGetterSetter>> listChildData) {
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

			final AssetInsertdataGetterSetter childText = (AssetInsertdataGetterSetter) getChild(groupPosition, childPosition);

			ViewHolder holder=null;

			if (convertView == null) {



				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.asset_entry, null);

				holder=new ViewHolder();

				holder.cardView=(CardView) convertView.findViewById(R.id.card_view);
				holder.etremark=(EditText) convertView.findViewById(R.id.etremarks);
				holder.tbpresent=(ToggleButton) convertView.findViewById(R.id.tbpresent);
				holder.btn_cam = (Button) convertView.findViewById(R.id.btncam);
				holder.cam_layout = (LinearLayout) convertView.findViewById(R.id.cam_layout);
				holder.remark_layout = (LinearLayout) convertView.findViewById(R.id.remark_layout);
				holder.btn_checklist = (Button) convertView.findViewById(R.id.btn_checklist);


				convertView.setTag(holder);

			}

			holder = (ViewHolder) convertView.getTag();

			holder.tbpresent.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					ischangedflag=true;
					String val = ((ToggleButton) v).getText().toString();
					_listDataChild.get(listDataHeader.get(groupPosition))
							.get(childPosition).setPresent(val);
					if(val.equals("NO")){
						_listDataChild.get(listDataHeader.get(groupPosition))
								.get(childPosition).setImg("");

						db.deleteCheckListInsertData(_listDataChild.get(listDataHeader.get(groupPosition))
								.get(childPosition).getAsset_cd(),store_cd,visit_date);
					}
					else{
						_listDataChild.get(listDataHeader.get(groupPosition))
								.get(childPosition).setRemark("");
					}


					expListView.invalidateViews();
				}
			});

			holder.btn_checklist.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {

					showChecklistDialogue(_listDataChild.get(listDataHeader.get(groupPosition))
							.get(childPosition).getAsset_cd());
				}
			});

			holder.btn_cam.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					grp_position = groupPosition;
					child_position = childPosition;

					_pathforcheck = store_cd + "Asset"
							+ "Image" + visit_date.replace("/","") + getCurrentTime().replace(":","")+".jpg";

					_path = CommonString1.FILE_PATH + _pathforcheck;

					startCameraActivity();

				}
			});

			holder.etremark.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {

					if (!hasFocus) {
						final EditText Caption = (EditText) v;
						String value1 = Caption.getText().toString().trim();
						value1 = value1.replaceAll("[&^<>{}'$]", "");
						if (value1.equals("") || value1.substring(0).equals(".") || value1.substring(0).equals(",")) {

							_listDataChild
							.get(listDataHeader.get(groupPosition))
							.get(childPosition).setRemark("");

						} else {
							ischangedflag=true;
							_listDataChild
							.get(listDataHeader.get(groupPosition))
							.get(childPosition).setRemark(value1);

						}

					}

				}
			});



			holder.etremark.setText(childText.getRemark());

			holder.tbpresent.setChecked(_listDataChild
					.get(listDataHeader.get(groupPosition))
					.get(childPosition).getPresent().equals("YES"));

			if (!img1.equalsIgnoreCase("")) {
				if (childPosition == child_position && groupPosition == grp_position) {
					//childText.get(childPosition).setCamera("YES");
					_listDataChild
							.get(listDataHeader.get(groupPosition))
							.get(childPosition).setImg(img1);
					//childText.setImg(img1);
					img1 = "";

				}
			}

			if(_listDataChild
					.get(listDataHeader.get(groupPosition))
					.get(childPosition).getPresent().equals("YES")){
				//holder.etremark.setVisibility(View.INVISIBLE);

				holder.remark_layout.setVisibility(View.GONE);
				holder.cam_layout.setVisibility(View.VISIBLE);

				if (_listDataChild
						.get(listDataHeader.get(groupPosition))
						.get(childPosition).getImg()!=null && !_listDataChild
						.get(listDataHeader.get(groupPosition))
						.get(childPosition).getImg().equals("")) {
					holder.btn_cam.setBackgroundResource(R.drawable.camera_done);
				} else {
					holder.btn_cam.setBackgroundResource(R.drawable.camera);
				}

			}else if(_listDataChild
					.get(listDataHeader.get(groupPosition))
					.get(childPosition).getPresent().equals("NO")){
				//holder.etremark.setVisibility(View.VISIBLE);

				holder.remark_layout.setVisibility(View.VISIBLE);
				holder.cam_layout.setVisibility(View.GONE);

				holder.etremark.setText(childText.getRemark());
			}

			TextView txtListChild = (TextView) convertView
					.findViewById(R.id.lblListItem);

			txtListChild.setText(childText.getAsset());

			for(int m=0;m<mappingChecklistDataGetterSetters.size();m++){

				if(mappingChecklistDataGetterSetters.get(m).getAsset_cd().get(0).equals(_listDataChild
						.get(listDataHeader.get(groupPosition))
						.get(childPosition).getAsset_cd())){

					if(_listDataChild
							.get(listDataHeader.get(groupPosition))
							.get(childPosition).getPresent().equals("YES")){
						holder.btn_checklist.setVisibility(View.VISIBLE);
					}
					else{
						holder.btn_checklist.setVisibility(View.GONE);
					}


					if(!checkflag && !(db.getCheckListInsertData(_listDataChild
							.get(listDataHeader.get(groupPosition))
							.get(childPosition).getAsset_cd(),store_cd,visit_date).size()>0)){
						holder.btn_checklist.setTextColor(getResources().getColor(R.color.red));
					}
					else{
						holder.btn_checklist.setTextColor(getResources().getColor(R.color.black));
					}

					break;
				}
				else{
					holder.btn_checklist.setVisibility(View.GONE);
				}
			}



			if(!checkflag){
				boolean tempflag=false;
				if(_listDataChild
						.get(listDataHeader.get(groupPosition))
						.get(childPosition).getPresent().equals("NO")){
					if(holder.etremark.getText().toString().equals("")){
						//holder.etmidstock.setBackgroundColor(getResources().getColor(R.color.red));
						holder.etremark.setHintTextColor(getResources().getColor(R.color.green));
						holder.etremark.setHint("Empty");
						tempflag=true;
					}
				}
				else{
					if(_listDataChild
							.get(listDataHeader.get(groupPosition))
							.get(childPosition).getImg().equals("")){
						holder.btn_cam.setBackgroundResource(R.drawable.camera_not_done);

					}
					else{
						holder.btn_cam.setBackgroundResource(R.drawable.camera_done);
					}
				}


				if(tempflag){

					holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
				}
				else{

					holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
				}

			}

			//holder.tvpromo.setText(childText.getAsset());
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
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			AssetInsertdataGetterSetter headerTitle = (AssetInsertdataGetterSetter) getGroup(groupPosition);
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

		EditText etremark;
		ToggleButton tbpresent;
		CardView cardView;
		LinearLayout cam_layout,remark_layout;
		Button btn_cam,btn_checklist;
		//TextView tvpromo;
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();

		if(id==R.id.save_btn){

			expListView.clearFocus();

			if(validateData(listDataChild, listDataHeader)){

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Are you sure you want to save")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {

						db.open();

						getMid();

						db.deleteAssetData(store_cd);
						db.InsertAssetData(
								store_cd, listDataChild,
								listDataHeader);

						Toast.makeText(getApplicationContext(),
								"Data has been saved", Toast.LENGTH_SHORT).show();

						/*Intent DailyEntryMenu = new Intent(
								AssetActivity.this,
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
				Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
			}

		}
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
			cdata.setStatus(CommonString1.KEY_CHECK_IN);
			mid = db.InsertCoverageData(cdata);

		}

		return mid;
	}

	public String getCurrentTime() {

		Calendar m_cal = Calendar.getInstance();

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String cdate = formatter.format(m_cal.getTime());

       /* String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
                + m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);*/

		return cdate;

	}

	boolean validateData(
			HashMap<AssetInsertdataGetterSetter, List<AssetInsertdataGetterSetter>> listDataChild2,
			List<AssetInsertdataGetterSetter> listDataHeader2) {
		//boolean flag = true;
		checkHeaderArray.clear();

		for (int i = 0; i < listDataHeader2.size(); i++) {
			for (int j = 0; j < listDataChild2.get(listDataHeader2.get(i))
					.size(); j++) {

				ArrayList<ChecklistInsertDataGetterSetter> checklistInsertDataGetterSetter;

				/*String aspermccain = listDataChild2.get(listDataHeader2.get(i)).get(j).getAs_per_meccain();*/
				String present= listDataChild2.get(listDataHeader2.get(i)).get(j).getPresent();
				String remark = listDataChild2.get(listDataHeader2.get(i)).get(j).getRemark();
				String img = listDataChild2.get(listDataHeader2.get(i)).get(j).getImg();

				String asset_cd = listDataChild2.get(listDataHeader2.get(i)).get(j).getAsset_cd();


				if(present.equalsIgnoreCase("NO")){
					if (remark.equalsIgnoreCase("")) {

						if(!checkHeaderArray.contains(i)){
							checkHeaderArray.add(i);
						}

						checkflag=false;

						//flag = false;
						break;

					} else{
						checkflag=true;
						//flag = true;
					}
				}
				else if(present.equalsIgnoreCase("YES")){
					if(img.equals("")){
						if(!checkHeaderArray.contains(i)){
							checkHeaderArray.add(i);
						}

						checkflag=false;

						//flag = false;
						break;

					} else{

						checkflag=true;
						//flag = true;
					}
				}

				if(checkflag && present.equalsIgnoreCase("YES")){

					for(int m=0;m<mappingChecklistDataGetterSetters.size();m++){

						if(mappingChecklistDataGetterSetters.get(m).getAsset_cd().get(0).equals(asset_cd)){
							checklistInsertDataGetterSetter = db.getCheckListInsertData(asset_cd,store_cd,visit_date);
							if(!(checklistInsertDataGetterSetter.size()>0)){
								checkflag=false;
							}
							break;
						}

					}

					if(!checkflag){
						if(!checkHeaderArray.contains(i)){
							checkHeaderArray.add(i);
							break;
						}
					}

				}

			}

			if(checkflag == false){
				break;
			}

		}

		//expListView.invalidate();

		return checkflag;

	}

	@Override
	public void onBackPressed() {
		/*Intent i = new Intent(this, StoreEntry.class);
		startActivity(i);*/
		
		if(ischangedflag){
			AlertDialog.Builder builder = new AlertDialog.Builder(
					AssetActivity.this);
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


	protected void startCameraActivity() {

		try {
			Log.i("MakeMachine", "startCameraActivity()");
			File file = new File(_path);
			Uri outputFileUri = Uri.fromFile(file);

			Intent intent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

			startActivityForResult(intent, 0);
		} catch (Exception e) {

			e.printStackTrace();
		}
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
					if (new File(str + _pathforcheck).exists()) {

						//cam.setBackgroundResource(R.drawable.camera_list_tick);
						image1 = _pathforcheck;
						img1 = _pathforcheck;
						expListView.invalidateViews();
						_pathforcheck = "";
						//Toast.makeText(getApplicationContext(), ""+image1, Toast.LENGTH_LONG).show();

					}
				}

				break;
		}


		super.onActivityResult(requestCode, resultCode, data);
	}

	public void showChecklistDialogue(final String asset_cd){

		boolean update_flag = false;

		checklistInsertDataGetterSetters = db.getCheckListInsertData(asset_cd,store_cd,visit_date);
		if(!(checklistInsertDataGetterSetters.size()>0)){
			checklistInsertDataGetterSetters = db.getCheckListData(asset_cd);
		}
		else{
			update_flag = true;
		}


		if(checklistInsertDataGetterSetters.size()>0){

			final Dialog dialog = new Dialog(AssetActivity.this);
			dialog.setCancelable(false);

			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

			dialog.setContentView(R.layout.checklist_dialogue_layout);

			// set values for custom dialog components - text, image and button

			listView = (ListView) dialog.findViewById(R.id.lv_checklist);

			listView.setAdapter(new MyAdaptor());

			Button btnsave = (Button) dialog.findViewById(R.id.btn_save_checklist);
			//Button btncancel = (Button) dialog.findViewById(R.id.btn_cancel_checklist);
			if(update_flag){
				btnsave.setText("UPDATE");
			}

			/*btncancel.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					dialog.cancel();
				}
			});*/

			btnsave.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {

					listView.clearFocus();

					boolean isvalid = true;

				for(int i = 0; i < checklistInsertDataGetterSetters.size() ; i++){

					if(checklistInsertDataGetterSetters.get(i).getChecklist_type().equals("FREETEXT") && checklistInsertDataGetterSetters.get(i).getChecklist_text().equals("")){
						isvalid = false;
						break;
					}

				}
					if(isvalid){

						db.insertAssetCheckListData(checklistInsertDataGetterSetters, asset_cd, visit_date, store_cd);

						dialog.cancel();
					}
					else{
						Toast.makeText(AssetActivity.this, "Please fill text in text field", Toast.LENGTH_SHORT).show();
					}


				}
			});

			dialog.show();

		}

	}


	private class MyAdaptor extends BaseAdapter {

		@Override
		public int getCount() {

			return checklistInsertDataGetterSetters.size();
		}

		@Override
		public Object getItem(int position) {

			return position;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewCheckHolder holder = null;

			//spinflag=false;

			//final String status="";

			if (convertView == null) {
				holder = new ViewCheckHolder();

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.check_list_child, null);

				holder.dfavail= (TextView) convertView
						.findViewById(R.id.tv_checklist);
				//holder.spinstat=(Spinner) convertView.findViewById(R.id.spinstatus);
				holder.tbpresent=(ToggleButton) convertView.findViewById(R.id.toggle_checklist);
				holder.etremark=(EditText) convertView.findViewById(R.id.et_checklist);

				holder.stauslay=(LinearLayout) convertView.findViewById(R.id.status_layout);

				/*if(position==0 && deepFreezlist.get(position).getStatus().equals("") || position==0 && deepFreezlist.get(position).getStatus().equals("NO")){
					listsize=1;
					notifyDataSetChanged();
				}

				if(listsize==1){

					holder.etremark.addTextChangedListener(new TextWatcher(){

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
													  int arg3) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTextChanged(CharSequence s, int a, int b, int c) {
							// TODO Auto-generated method stub

							deepFreezlist.get(position).setRemark(s.toString());
						}});

				}
				else{
*/
					holder.etremark
							.setOnFocusChangeListener(new OnFocusChangeListener() {
								public void onFocusChange(View v, boolean hasFocus) {

									if (!hasFocus) {
										final int position = v.getId();
										final EditText Caption = (EditText) v;
										String value1 = Caption.getText().toString();

										if (value1.equals("")) {

											checklistInsertDataGetterSetters.get(position).setChecklist_text("");

										} else {

											checklistInsertDataGetterSetters.get(position).setChecklist_text(value1.toString().replaceAll("[&^<>{}'$]", ""));

										}

									}
								}
							});
				//}



				/*holder.spinstat.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View v,
							int pos, long arg3) {
						// TODO Auto-generated method stub

						if(pos!=0){
							status=statuslist.get(pos);

							final int position = arg0.getId();


							if(position==0 && status.equals("No")){
								listsize=1;
								 //notifyDataSetChanged();
								//lv.invalidateViews();


								SharedPreferences.Editor editor = preferences.edit();
								editor.putBoolean("opnestkmccaindf", false);

								editor.commit();
							}
							else if(position==0 && status.equals("Yes")){
								listsize=deepFreezlist.size();

								SharedPreferences.Editor editor = preferences.edit();
								editor.putBoolean("opnestkmccaindf", true);

								editor.commit();
							}


							if(status.equals("No")){
								//lv.setClickable(false);
								spinflag=false;
								deepFreezlist.get(position).setStatus("No");

								//lv.getChildAt(position).findViewById(R.id.etremark).setVisibility(View.VISIBLE);

							}
							else{
								deepFreezlist.get(position).setStatus("Yes");
								spinflag=true;

								//lv.getChildAt(position).findViewById(R.id.etremark).setVisibility(View.INVISIBLE);
							}


						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});*/

				holder.tbpresent.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub



						String val = ((ToggleButton) v).getText().toString();

					/*	status=statuslist.get(pos);

						final int position = arg0.getId();
*/
						final int position = v.getId();
					/*	if(position==0 && val.equals("NO")){
							//listsize=1;
							//notifyDataSetChanged();
							//lv.invalidateViews();

							SharedPreferences.Editor editor = preferences.edit();
							editor.putBoolean("opnestkmccaindf", false);

							editor.commit();
						}
						else if(position==0 && val.equals("YES")){
							listsize=deepFreezlist.size();

							*//*if(mccainflag){

								String msg;
								String user_type = preferences.getString(CommonString1.KEY_USER_TYPE, null);
								if(user_type.equals("Merchandiser")){
									msg="Fill Mccain DF in Opening Stock";
								}
								else{
									msg="Fill Mccain DF in Opening and Closing Stock";
								}

								AlertDialog.Builder builder = new AlertDialog.Builder(MccainType.this);
								builder.setMessage(msg)
								.setCancelable(false)
								.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {


										dialog.cancel();
									//finish();

									}
								});
								AlertDialog alert = builder.create();

								alert.show();
							}*//*

							SharedPreferences.Editor editor = preferences.edit();
							editor.putBoolean("opnestkmccaindf", true);

							editor.commit();
						}*/



						checklistInsertDataGetterSetters.get(position).setChecklist_text(val);

						listView.invalidateViews();
					}
				});


				convertView.setTag(holder);


			} else {
				holder = (ViewCheckHolder) convertView.getTag();
			}

			//holder.spinstat.setAdapter(dfAdapter);

			holder.dfavail.setId(position);
			holder.etremark.setId(position);
			holder.tbpresent.setId(position);
			//holder.spinstat.setId(position);

/*
			if(deepFreezlist.get(position).getDeep_freezer().contains("Deep Freezer cooling")){
				holder.stauslay.setVisibility(View.INVISIBLE);
			}
			else{
				holder.stauslay.setVisibility(View.VISIBLE);
			}*/

			/*if(deepFreezlist.get(position).getDeep_freezer().contains("Material Wellness")){
				holder.etremark.setVisibility(View.INVISIBLE);
			}
			else{
				holder.etremark.setVisibility(View.VISIBLE);
			}*/

			holder.dfavail.setText(checklistInsertDataGetterSetters.get(position).getChecklist());
			holder.etremark.setText(checklistInsertDataGetterSetters.get(position).getChecklist_text());
			//status=deepFreezlist.get(position).getStatus();
			if(checklistInsertDataGetterSetters.get(position).getChecklist_type().equals("FREETEXT")){
				holder.etremark.setVisibility(View.VISIBLE);
				holder.tbpresent.setVisibility(View.GONE);
				//dfAdapter.notifyDataSetChanged();
				// notifyDataSetChanged();
				//listView.invalidateViews();
			}
			else if(checklistInsertDataGetterSetters.get(position).getChecklist_type().equals("TOGGLE")){

				holder.etremark.setVisibility(View.GONE);
				holder.tbpresent.setVisibility(View.VISIBLE);

				holder.tbpresent.setChecked(checklistInsertDataGetterSetters.get(position).getChecklist_text().equals("YES"));
				//dfAdapter.notifyDataSetChanged();
				// notifyDataSetChanged();
				//lv.invalidateViews();
				/*if(position!=0){

					//dfAdapter.notifyDataSetChanged();
					lv.invalidateViews();
				}*/
				//listView.invalidateViews();
				// notifyDataSetChanged();
			}/*else{
				holder.etremark.setVisibility(View.VISIBLE);
				//dfAdapter.notifyDataSetChanged();
				//lv.invalidateViews();
				 notifyDataSetChanged();
			}*/

			/*if(spinflag){
				if(position!=0){
					holder.etremark.setVisibility(View.INVISIBLE);
					dfAdapter.notifyDataSetChanged();
				}
			}
			else{
				holder.etremark.setVisibility(View.VISIBLE);
				dfAdapter.notifyDataSetChanged();
			}*/
			//	int indexspin=statuslist.indexOf(status);


			//holder.spinstat.setSelection(indexspin);

			return convertView;
		}

	}


	private class ViewCheckHolder {
		TextView dfavail;
		//Spinner spinstat;

		ToggleButton tbpresent;
		EditText etremark;
		LinearLayout stauslay;

	}



}
