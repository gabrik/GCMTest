package info.gdgcatania.android.app.gcmtest;
import android.content.Context;
import android.provider.Settings.Secure;
public final class Constants {

	public static String android_id;
	
	public static String devname;
	
	private static Context mAppContext;

	//Costanti per gli Intent
	public static final String INTENT_TYPE = "I_TYPE";
	public static final String INTENT_CATEGORY = "info.gdgcatania.android.app.gcmtest.BROADCAST";
	
	public static final String INTENT_CALL_CLASS = "CLASS_TO_CALL";
	
	
	//Intent Registrazione
	public static final int MSG_REG = 99;
	public static final String REGISTED_ACTION = "info.gdgcatania.android.app.gcmtest.actions.REGISTED";
	public static final String MSG_REG_USER = "MSG_USR";
	public static final String MSG_REG_PSW = "MSG_PSW";
	public static final String MSG_ID = "MSG_ID";
	
	
	public static final int INSKEYID = 7;
	public static final String INSKEYID_ACTION ="info.gdgcatania.android.app.gcmtest.actions.INSKEYID";
	public static final String INSKEYID_BUNDLE="KEYID";

	
	public static final int OPENNOTI=8;
	public static final String OPEN_ACTION ="info.gdgcatania.android.app.gcmtest.actions.OPENNOTI";
	public static final String TITLE ="TIT";
	public static final String BODY="BOD";
	
	//costanti per il tipo di notifica
	public static final int SIMPLE = 1;
	public static final int ACTION_BUTTON = 2;
	public static final int BIG = 3;
	public static final int FEATURE_BUTTON = 4; 

	
	//Pagine del webservice
	
	public static final String SERVER = "";
	public static final String PATH = "/gcmgdgtest/";
	public static final String REGISTER_PAGE="register.php";
	public static final String INSERT_PAGE ="insertid.php";
	
	

	//ID app per il GCM
	public static final String SENDER_ID = "14592190621";
	
	//metodo per ottenere un id del dispositivo
	public static String getDeviceID(Context context){
		return android_id = Secure.getString(context.getContentResolver(),
	            Secure.ANDROID_ID); 
	}
	
	public static String getDeviceName(){
		return devname = android.os.Build.MODEL;
	}
	
	public static Context getmAppContext() {
		return mAppContext;
	}

	public static void setmAppContext(Context mAppContext) {
		Constants.mAppContext = mAppContext;
	}
	
}
