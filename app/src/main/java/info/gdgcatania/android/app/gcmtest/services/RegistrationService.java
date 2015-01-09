package info.gdgcatania.android.app.gcmtest.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import info.gdgcatania.android.app.gcmtest.Constants;
import info.gdgcatania.android.app.gcmtest.MainFragment;

public class RegistrationService extends IntentService {

	private static final String TAG = "GDG GCM Test Intent Service";


	private GoogleCloudMessaging gcm;
	
	private String regid;



	public RegistrationService() {
		super(TAG);
	}


	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle data = intent.getExtras();
		int type = intent.getIntExtra(Constants.INTENT_TYPE, -1);
		String caller= intent.getStringExtra(Constants.INTENT_CALL_CLASS);
		Log.d(TAG, "Intent ricevuto da: " +caller);
		switch(type){
		case Constants.MSG_REG:
			Log.d(TAG, "Intent Registrazione Ricevuto");
			String usr=data.getString(Constants.MSG_REG_USER);
			String psw=data.getString(Constants.MSG_REG_PSW);
			Log.d(TAG, "ricevuto " + usr + ":" + psw);
			int id=register(usr, psw);
			Intent localIntent = new Intent();
			localIntent.setAction(Constants.REGISTED_ACTION);
			localIntent.addCategory(Constants.INTENT_CATEGORY);
			localIntent.putExtra(Constants.MSG_ID, id);
			localIntent.setClass(getApplication(), MainFragment.ServiceReceiver.class);
			// manda il broadcast

			if(LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent))
				Log.d(TAG, "broadcast inviato " + id);
			else
				Log.e(TAG, "broadcast non inviato " + id);
			break;

		case Constants.INSKEYID:
			Log.d(TAG, "Intent Inserimento ID notifica");
			String kID=data.getString(Constants.INSKEYID_BUNDLE);
			insKeyID(kID);

			break;
		default:
			break;
		}

	}


	private synchronized int register(String username,String password){


		boolean res=false;
		String msg = "";
		
		HttpParams mParams = new BasicHttpParams();

		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(mParams, timeoutConnection);
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(mParams, timeoutSocket);

		HttpClient httpclient = new DefaultHttpClient(mParams);
		HttpPost httppost = new HttpPost("http://"+Constants.SERVER+Constants.PATH+Constants.REGISTER_PAGE);
		int UserID=-1;

		try{
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("username", username));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			nameValuePairs.add(new BasicNameValuePair("udid", Constants.getDeviceID(getBaseContext())));




			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity httpEntity = response.getEntity();
			String result = EntityUtils.toString(httpEntity);

			Log.d(TAG, "Response: "+result);

			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray =  jsonObject.getJSONArray("status");


			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject insideJSON = jsonArray.getJSONObject(i);           

				Log.d(TAG, "letto "+insideJSON.getInt("id"));
				UserID=insideJSON.getInt("id");
			}


			if (gcm == null) {
				gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
			}
			regid = gcm.register(Constants.SENDER_ID);
			msg = "Device registered, registration ID=" + regid;

			res=this.insKeyID(regid);

			
			Log.d("gcm","res: " + res);
			Log.d("gcm","ID: " + Constants.SENDER_ID);
			Log.d(TAG , "msg "+ msg);
			Log.d("gcm","ID= " + Constants.getDeviceID(getApplicationContext()));






		}
		catch(Exception e)
		{
			Log.e(TAG, "Error:  "+e.toString());
		} 

		if(res)
			return UserID;
		else
			return -1;



	}



	private synchronized boolean insKeyID(String keyID){
		boolean res=false;
		File fileKey = new File(Constants.getmAppContext().getFilesDir() + "/keyID.conf");
		HttpParams mParams = new BasicHttpParams();

		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(mParams, timeoutConnection);
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(mParams, timeoutSocket);

		HttpClient httpclient = new DefaultHttpClient(mParams);
		HttpPost httppost = new HttpPost("http://"+Constants.SERVER+Constants.PATH+Constants.INSERT_PAGE);
		
		if(!fileKey.exists()){
			try {
				fileKey.createNewFile();



				PrintStream fileOut = new PrintStream(fileKey);
				fileOut.print(keyID);
				fileOut.close();
				try{
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
					nameValuePairs.add(new BasicNameValuePair("udid", Constants.getDeviceID(getApplicationContext())));
					nameValuePairs.add(new BasicNameValuePair("keyid", keyID));
					nameValuePairs.add(new BasicNameValuePair("devname", Constants.getDeviceName()));




					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity httpEntity = response.getEntity();
					String result = EntityUtils.toString(httpEntity);
					Log.d(TAG, "Response: "+result);

					// 
					JSONObject jsonObject = new JSONObject(result);
					res=jsonObject.getBoolean("res");



				}
				catch(Exception e)
				{
					Log.e(TAG, "Error:  "+e.toString());
					fileKey.delete();
				} 


			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return res;
	}
}
