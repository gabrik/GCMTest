package info.gdgcatania.android.app.gcmtest.wearnotification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import edu.gabrielebaldoni.gdgcataniagcmtest.R;
import info.gdgcatania.android.app.gcmtest.Constants;
import info.gdgcatania.android.app.gcmtest.MainActivity;


public class SimpleGCMNotification {
	
	private static final String NOTIFICATION_TAG = "GCM";


	public static void notify(final Context context,
			final String exampleString, final String titleString) {
		final Resources res = context.getResources();
		
		
		Intent intent = new Intent(context, MainActivity.class);
		
		intent.putExtra(Constants.INTENT_TYPE, Constants.OPENNOTI);
		intent.putExtra(Constants.TITLE, titleString);
		intent.putExtra(Constants.BODY, exampleString);
		intent.setAction(Constants.OPEN_ACTION);
	    PendingIntent pIntent =  PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
	    Log.d(NOTIFICATION_TAG, "title: "+intent.getStringExtra(Constants.TITLE));
	    Log.d(NOTIFICATION_TAG, "body: "+intent.getStringExtra(Constants.BODY));

		 String ticker = exampleString;
		 String title = res.getString(
				R.string.gcm_notification_title_template, titleString);
		String text = res.getString(
				R.string.gcm_notification_placeholder_text_template,
				exampleString);

		NotificationCompat.Builder simpleBuilder = new NotificationCompat.Builder(
				context)
				.setDefaults(Notification.DEFAULT_SOUND)
				.setSmallIcon(R.drawable.ic_stat_gcm).setContentTitle(title)
				.setContentText(text)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setTicker(ticker)
				.setContentIntent(pIntent)
				.setAutoCancel(true);
		
		
		Notification mNoti=simpleBuilder.build();
		
		mNoti.ledARGB=Color.RED;
		
		mNoti.flags = Notification.FLAG_SHOW_LIGHTS;
		mNoti.ledOnMS = 750; 
		mNoti.ledOffMS = 500; 
	    
	    //						_________			_________				
	    long[] vib = {250,500,250,500,};
	    
	    mNoti.vibrate=vib;
	    
		notify(context, mNoti);
	}

	@TargetApi(Build.VERSION_CODES.ECLAIR)
	private static void notify(final Context context,
			final Notification notification) {
		final NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			nm.notify(NOTIFICATION_TAG, 0, notification);
		} else {
			nm.notify(NOTIFICATION_TAG.hashCode(), notification);
		}
	}

	/**
	 * Cancels any notifications of this type previously shown using
	 * {@link #notify(Context, String, int)}.
	 */
	@TargetApi(Build.VERSION_CODES.ECLAIR)
	public static void cancel(final Context context) {
		final NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			nm.cancel(NOTIFICATION_TAG, 0);
		} else {
			nm.cancel(NOTIFICATION_TAG.hashCode());
		}
	}
}