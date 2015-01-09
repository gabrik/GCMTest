package info.gdgcatania.android.app.gcmtest.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import info.gdgcatania.android.app.gcmtest.wearnotification.ActionButtonGCMNotification;
import info.gdgcatania.android.app.gcmtest.wearnotification.BigViewGCMNotification;
import info.gdgcatania.android.app.gcmtest.wearnotification.FeatureGCMNotification;
import info.gdgcatania.android.app.gcmtest.wearnotification.MultiPageGCMNotification;
import info.gdgcatania.android.app.gcmtest.wearnotification.ReplyGCMNotification;
import info.gdgcatania.android.app.gcmtest.wearnotification.SimpleGCMNotification;
import info.gdgcatania.android.app.gcmtest.wearnotification.StackGCMNotification;


public class TestGCMIntentService extends IntentService {
	
	private static final String TAG = "GDG GCM IntentService";
	
	
	public static String RegId = "";

	public TestGCMIntentService() {
		super(TAG);
	}


	@Override
	    protected void onHandleIntent(Intent intent) {
		 	Log.d(TAG, "Intent GCM Ricevuto");
		 	
	        Bundle extras = intent.getExtras();
	        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
	        String messageType = gcm.getMessageType(intent);

	        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
	            /*
	             * Filter messages based on message type. Since it is likely that GCM
	             * will be extended in the future with new message types, just ignore
	             * any message types you're not interested in, or that you don't
	             * recognize.
	             * NON TOCCARE GOOGLE POTREBBE IMPLEMENTARE ALTRI TIPI DI MESSAGGI
	             */
	            if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
	                sendNotification("Send error: " + extras.toString(),GoogleCloudMessaging.
		                    MESSAGE_TYPE_SEND_ERROR,-1);
	            } else if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_DELETED.equals(messageType)) {
	                sendNotification("Deleted messages on server: " +
	                        extras.toString(),GoogleCloudMessaging.
		                    MESSAGE_TYPE_DELETED,-1);
	            // Se e' un normale messaggio GCM posso lavorarci
	            } else if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
	               
	            	String msg=extras.getString("message");
	            	String title = extras.getString("title");
	            	int notificationType = Integer.parseInt(extras.getString("noti_type"));
	            	
	            	Log.i(TAG, "tipo_notifica_fuori: " + notificationType);
	            	
	                // Invio della notifica
	                sendNotification(msg,title,notificationType);
	                Log.i(TAG, "Received: " + extras.toString());
	            }
	        }
	        // Rilascio del wake lock
	        WakefulBroadcastReceiver.completeWakefulIntent(intent);
	    }

	    
	    private void sendNotification(String msg,String title,int type) {
	    	
	    	Log.d(TAG, "tipo_notifica_dentro: " + type);
	    	
	    	//Mostrare vari tipi di notifica
	    		switch(type){
	    		
	    		case -1:	    		
	    			Log.d(TAG, "Errore: " + type);
	    		case 0:
	    			SimpleGCMNotification.notify(this, msg, title +" simple");
	    			break;
	    		case 1:
	    			ActionButtonGCMNotification.notify(this, msg, title + " action");
	    			break;
	    		case 2:
	    			BigViewGCMNotification.notify(this, msg, title + " big view");
	    			break;
	    		case 3:
	    			FeatureGCMNotification.notify(this, msg, title + " feature");
	    			break;
	    		case 4:
	    			ReplyGCMNotification.notify(this, msg, title + " reply");
	    			break;
	    		case 5:
	    			MultiPageGCMNotification.notify(this, msg, title + " multi page");
	    			break;
	    		case 6:
	    			StackGCMNotification.notify(this, msg, title + " stack notify");
	    			break;
	    		default:
	    			Log.i(TAG, "Default: " + type);
	    			break;
	    		}
	    	
	    	
	    	
	    		
	    }

	
   
	
	
}
