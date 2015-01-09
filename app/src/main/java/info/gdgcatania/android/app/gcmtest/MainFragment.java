package info.gdgcatania.android.app.gcmtest;



import java.io.File;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import info.gdgcatania.android.app.gcmtest.R;
import info.gdgcatania.android.app.gcmtest.services.RegistrationService;


public class MainFragment extends Fragment {

	
	private static final String TAG = "GDG GCM Test MainFragment";

    private ServiceReceiver receiver;
	private String username;
	private String password;
	
	private ProgressDialog waitingDialog;
	
	private EditText userName;
	private EditText userPass;
	private Button sendButton;
	private TextView allertText;


	
	
	public MainFragment() {
		super();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		userName = (EditText) rootView.findViewById(R.id.editTextUser);
		userPass = (EditText) rootView.findViewById(R.id.editTextPassword);
		sendButton = (Button) rootView.findViewById(R.id.buttonSend);
		allertText = (TextView) rootView.findViewById(R.id.textViewAlreadyRegistred);
		
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				username=userName.getText().toString();
				password=userPass.getText().toString();
				if((username.compareTo("")==0) || (password.compareTo("")==0)){
					Toast.makeText(Constants.getmAppContext(), "Inserisci username/password",Toast.LENGTH_LONG).show();
				} else {
					waitingDialog = new ProgressDialog(getActivity());
					waitingDialog.setMessage("Registrazione...");
					waitingDialog.show();
					register();
				}
				
				
			}
		});
		
		return rootView;
	}
	
	
	
	@Override
	public void onPause() {
		try{
			LocalBroadcastManager.getInstance(Constants.getmAppContext()).unregisterReceiver(receiver);
		} catch(Exception e) {
			Log.e(TAG, "unregister error" +  e.getMessage()) ;
		}
		
		super.onPause();
	}

	@Override
	public void onResume() {
		IntentFilter filter = new IntentFilter(Constants.REGISTED_ACTION);
		
		filter.addCategory(Constants.INTENT_CATEGORY);
		receiver = new ServiceReceiver();
		
		LocalBroadcastManager.getInstance(Constants.getmAppContext()).registerReceiver(receiver,filter);
		
		if(isRegistred()){
			userName.setActivated(false);
			userPass.setActivated(false);
			sendButton.setClickable(false);
			userName.setFocusable(false);
			userName.setFocusableInTouchMode(false);
			userPass.setFocusable(false);
			userPass.setFocusableInTouchMode(false);
			allertText.setVisibility(View.VISIBLE);
		} else
			allertText.setVisibility(View.INVISIBLE);
		
		super.onResume();
	}

	
	
	private boolean isRegistred(){
		
		File fileKey = new File(Constants.getmAppContext().getFilesDir() + "/keyID.conf");
		return fileKey.exists();
			
	}
	
	
	private void register(){
		
		
		Bundle data = new Bundle();
		data.putString(Constants.MSG_REG_USER, username);
		data.putString(Constants.MSG_REG_PSW, password);
		
		
	    Intent mRegIntent = new Intent(getActivity().getApplicationContext(), RegistrationService.class);
	    mRegIntent.putExtra(Constants.INTENT_TYPE, Constants.MSG_REG);
	    mRegIntent.putExtra(Constants.INTENT_CALL_CLASS, MainFragment.ServiceReceiver.class.getName());
	    mRegIntent.putExtras(data);
    	getActivity().startService(mRegIntent);
    	
		
	}
	
	
	public class ServiceReceiver extends BroadcastReceiver {
		
				@Override
				public void onReceive(Context context, Intent intent) {
					
					int type=intent.getIntExtra(Constants.INTENT_TYPE, -1);
					Log.d(TAG+" " + ServiceReceiver.class.getSimpleName(), "intent ricevuto tipo " + type);
					int res=intent.getIntExtra(Constants.MSG_ID, -1);
					waitingDialog.dismiss();
					if(res!=-1)
						Toast.makeText(Constants.getmAppContext(), "Complimenti adesso sei registrato per ricevere messaggi dal GCM",Toast.LENGTH_LONG).show();
					else
						Toast.makeText(Constants.getmAppContext(), "Errore durante la registrazione",Toast.LENGTH_LONG).show();	
					
				
				}

			}
}