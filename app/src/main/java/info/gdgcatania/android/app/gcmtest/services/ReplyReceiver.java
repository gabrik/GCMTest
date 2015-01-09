package info.gdgcatania.android.app.gcmtest.services;

import info.gdgcatania.android.app.gcmtest.utils.NotificationUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ReplyReceiver extends BroadcastReceiver {

	public ReplyReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		String prova = intent
				.getStringExtra(NotificationUtil.EXTRA_VOICE_REPLY);
		int lockReply = intent.getIntExtra("reply_lock", -1);
		int tipeAction = intent.getIntExtra("action_tipo", -1);

		// Toast.makeText(context, "Reply", Toast.LENGTH_SHORT).show();

		// Log.d("Action_Receiver", "lock: " + lockReply);

		if (lockReply != 0) {
			Log.d("Action_Receiver", "action_receiver: " + tipeAction);

			switch (tipeAction) {
			case 1:
				Toast.makeText(context, "Reply", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(context, "Refresh", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(context, "Open", Toast.LENGTH_SHORT).show();
				break;

			default:
				Toast.makeText(context, "Errore", Toast.LENGTH_SHORT).show();
				break;
			}
		}

		if (lockReply == 0) {
			try {
				if (prova.compareTo("Yes") == 0) {
					Toast.makeText(context, prova, Toast.LENGTH_SHORT).show();
					lockReply = -1;
				}
				if (prova.compareTo("No") == 0) {
					Toast.makeText(context, prova, Toast.LENGTH_SHORT).show();
					lockReply = -1;
				}
				if (prova.compareTo("Maybe") == 0) {
					Toast.makeText(context, prova, Toast.LENGTH_SHORT).show();
					lockReply = -1;
				}
			} catch (NullPointerException e) {
				Toast.makeText(context, "Reply Smartphone", Toast.LENGTH_SHORT)
						.show();
				lockReply = -1;
			}
		}

	}
}
