package com.cpm.geotag;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Timer;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import com.cpm.Constants.CommonString;
import com.cpm.Constants.CommonString1;
import com.cpm.whirlpool.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;



public class ImageUploadActivity extends Activity {
	private static boolean status;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploadscreen);
	}
	public static void UploadGeoTaggingImage(String path) {
		try {
			System.out.println(path);
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(CommonString1.FILE_PATH + path, o);
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
			Bitmap bitmap = BitmapFactory.decodeFile(
					CommonString1.FILE_PATH + path, o2);

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			byte[] ba = bao.toByteArray();
			String ba1 = Base64.encodeBytes(ba);
			SoapObject request = new SoapObject(CommonString.NAMESPACE, CommonString1.METHOD_UPLOAD_IMAGE);
			request.addProperty("img", ba1);
			request.addProperty("name", path);
			request.addProperty("FolderName", "GeoTagImages");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString1.URL);

			androidHttpTransport.call(CommonString1.SOAP_ACTION_UPLOAD_IMAGE, envelope);
			Object result = (Object) envelope.getResponse();
			if(result.toString().equalsIgnoreCase("Success"));
			{
			 File file = new File(CommonString1.FILE_PATH + path);
			 boolean deleted = file.delete();
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();

		}
	}
	
	
	public static boolean CheckGeotagImage(String path) {
		try {
			status = false;
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(CommonString1.FILE_PATH + path, o);
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
			Bitmap bitmap = BitmapFactory.decodeFile(
					CommonString1.FILE_PATH + path, o2);
			
			if(bitmap!=null)
			{
				
				status = true;
				
			}
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		
		 return status;
	}
	
}
