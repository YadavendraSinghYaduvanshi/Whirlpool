package com.cpm.geotag;

import java.util.ArrayList;


import com.cpm.GetterSetter.Storenamebean;

import com.cpm.whirlpool.R;
import com.cpm.database.GSKDatabase;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GeoTagging extends Activity {
	
	
	
	ArrayList<CityBean> temp = new ArrayList<CityBean>();
	public static ArrayList<Storenamebean> storedetails = new ArrayList<Storenamebean>();
	 
	ListView l1;
	GeotaggingBeans gpsdata =new GeotaggingBeans();
	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);

		}

		public int getCount() {
			return storedetails.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.locationrow, null);
				holder = new ViewHolder();
				holder.text = (TextView) convertView
						.findViewById(R.id.TextView01);
				
				holder.text1 = (TextView) convertView
						.findViewById(R.id.TextView02);


				holder.check = (ImageView) convertView
						.findViewById(R.id.storecheck);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text.setText(storedetails.get(position).getStorename());
			holder.text1.setText(storedetails.get(position).getStoreAddress());
			if (storedetails.get(position).getStatus().equalsIgnoreCase("Y")) {
				holder.check.setVisibility(View.VISIBLE);

			}
			if (storedetails.get(position).getStatus().equalsIgnoreCase("N")) {
				holder.check.setVisibility(View.INVISIBLE);

			}
			
			if (storedetails.get(position).getStatus().equalsIgnoreCase("P")) {
				holder.check.setVisibility(View.VISIBLE);
				
				holder.check.setBackgroundResource(R.drawable.pointer1);

			}

			return convertView;
		}

		static class ViewHolder {
			TextView text;
			TextView text1;
			ImageView check;

		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.geotaggingscreen);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);
		final ProgressBar _progress = (ProgressBar) findViewById(R.id.progressBar1);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		GSKDatabase data = new GSKDatabase(getApplicationContext());
		data.open();
		TextView tv = new TextView(getApplicationContext());
		tv.setText("Required");
		tv.setTextColor(Color.RED);
		
		
		
		//temp = data.getCityDetails();

		for (int i = 0; i < temp.size(); i++) {
			adapter.add(temp.get(i).getCityName());

		}
		data.close();
		final Spinner sp = (Spinner) findViewById(R.id.select_city);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int item = sp.getSelectedItemPosition();

				String selectedcity = temp.get(item).getCityId();
				GSKDatabase data = new GSKDatabase(getApplicationContext());
				data.open();
				storedetails = new ArrayList<Storenamebean>();
				//storedetails = data.getstorename();
				data.close();
				_progress.setVisibility(View.VISIBLE);
				generatelistview();

				_progress.setVisibility(View.INVISIBLE);

			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		sp.setAdapter(adapter);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	}

	public void generatelistview() {

		l1 = (ListView) findViewById(R.id.list1);
		l1.setAdapter(new EfficientAdapter(this));

		l1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text

				if (storedetails.get(position).getStatus()
						.equalsIgnoreCase("Y")) {
					
					ShowToast("Store Already Geotagged");
					
				}
				else if(storedetails.get(position).getStatus()
						.equalsIgnoreCase("P"))
				{
					
					
					Intent intent = new Intent(GeoTagging.this,
							LocationActivity.class);
					intent.putExtra("StoreName", storedetails.get(position)
							.getStorename());
					intent.putExtra("Storeid", storedetails.get(position)
							.getStoreid());
					
					intent.putExtra("Storeaddress", storedetails.get(position)
							.getStoreAddress());
					
					intent.putExtra("storelatitude", storedetails.get(position)
							.getLatitude());
					
					intent.putExtra("storelongitude", storedetails.get(position)
							.getLongitude());

					startActivity(intent);
					locationactivity();
					
					
					
				}
				else {

					

					/*Intent intent = new Intent(GeoTagging.this,
							LocationActivity.class);
					intent.putExtra("StoreName", storedetails.get(position)
							.getStorename());
					intent.putExtra("Storeid", storedetails.get(position)
							.getStoreid());
					
					intent.putExtra("Storeaddress", storedetails.get(position)
							.getStoreAddress());
					intent.putExtra("storelatitude", "0");
					
					intent.putExtra("storelongitude", "0");

					startActivity(intent);
					locationactivity();*/
					
					if(!(gpsdata.getLatitude()==0 && gpsdata.getLongitude()==0))
                    {
                    Intent intent = new Intent(GeoTagging.this,
                                LocationActivity.class);
                    intent.putExtra("StoreName", storedetails.get(position)
                                .getStorename());
                    intent.putExtra("Storeid", storedetails.get(position)
                                .getStoreid());
                    
                    intent.putExtra("Storeaddress", storedetails.get(position)
                                .getStoreAddress());
                    intent.putExtra("storelatitude", "0");
                    
                    intent.putExtra("storelongitude", "0");

                    startActivity(intent);
                    locationactivity();

              }else
              {
                    ShowToast("GPS Not Available");
                    
              }



				
					

				}
			}
		});
	}

	public void locationactivity() {

		this.finish();

	}
	
	 public void ShowToast(String message)
     {
    	 
    	  LayoutInflater inflater = getLayoutInflater();
   	   View layout = inflater.inflate(R.layout.toastview,
   	                                  (ViewGroup) findViewById(R.id.toast_layout_root));

   	   ImageView image = (ImageView) layout.findViewById(R.id.image);
   	   image.setImageResource(R.drawable.danger_icon);
   	   TextView text = (TextView) layout.findViewById(R.id.text);
   	   text.setText(message);

   	   Toast toast = new Toast(getApplicationContext());
   	   toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
   	   toast.setDuration(Toast.LENGTH_LONG);
   	   toast.setView(layout);
   	   toast.show();
		
     }

}
