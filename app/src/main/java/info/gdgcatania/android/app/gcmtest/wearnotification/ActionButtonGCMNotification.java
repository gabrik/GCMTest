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
import info.gdgcatania.android.app.gcmtest.services.ReplyReceiver;
import info.gdgcatania.android.app.gcmtest.utils.NotificationUtil;


public class ActionButtonGCMNotification {
	
	private static final String NOTIFICATION_TAG = "GCM";


	public static void notify(final Context context,
			final String exampleString, final String titleString) {
		final Resources res = context.getResources();
		
		final String replyAction = res.getString(R.string.action_button_reply);
		final String refreshAction = res.getString(R.string.action_button_refresh);
		
	    //intent Action Button
	    //prima azione di reply
	    Intent replyIntent = new Intent(context,ReplyReceiver.class);
	    replyIntent.setAction(NotificationUtil.ACTION_INTENT);
	    replyIntent.putExtra("action_tipo", 1);
	    PendingIntent replyPending = PendingIntent.getBroadcast(context, 2, replyIntent, 0);

	    //seconda azione di refresh
	    Intent refreshIntent = new Intent(context, ReplyReceiver.class);
	    refreshIntent.setAction(NotificationUtil.ACTION_INTENT);
	    refreshIntent.putExtra("action_tipo", 2);
	    PendingIntent refreshPending = PendingIntent.getBroadcast(context, 3, refreshIntent,0);

	    //azione di default
	    Intent defaultIntent = new Intent(context, ReplyReceiver.class);
	    defaultIntent.setAction(NotificationUtil.ACTION_INTENT);
	    defaultIntent.putExtra("action_tipo", 3);
	    PendingIntent defaultPending = PendingIntent.getBroadcast(context, 1, defaultIntent,0);

	    
		 String ticker = exampleString;
		 String title = res.getString(
				R.string.gcm_notification_title_template, titleString);
		String text = res.getString(
				R.string.gcm_notification_placeholder_text_template,
				exampleString);

		NotificationCompat.Builder actionBuilder = new NotificationCompat.Builder(
				context)
				.setDefaults(Notification.DEFAULT_SOUND)
				.setSmallIcon(R.drawable.ic_stat_gcm).setContentTitle(title)
				.setContentText(text)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setTicker(ticker)
				.setContentIntent(defaultPending)
				.addAction(R.drawable.ic_full_reply, replyAction, replyPending)
				.addAction(R.drawable.ic_full_action, refreshAction, refreshPending)
				.setAutoCancel(true);
		
		
		Notification mNoti=actionBuilder.build();
		
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