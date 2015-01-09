package info.gdgcatania.android.app.gcmtest;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.gdgcatania.android.app.gcmtest.R;


public class ViewFragment extends Fragment {

	
	private String title;
	private String body;
	
	
	private TextView titleView;
	private TextView bodyView;
	
	

	public ViewFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view, container,
				false);
		
		
		titleView=(TextView) rootView.findViewById(R.id.textViewTitle);
		bodyView=(TextView) rootView.findViewById(R.id.textBody);
		
		titleView.setText(title);
		bodyView.setText(body);
		CancelNotification(Constants.getmAppContext());
		return rootView;
	}

	
	
	public static void CancelNotification(Context ctx) {
	    String ns = Context.NOTIFICATION_SERVICE;
	    NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
	    nMgr.cancelAll();
	}

	

	public void setTitle(String title) {
		this.title = title;
	}

	

	public void setBody(String body) {
		this.body = body;
	}
	
	
	
	

}
