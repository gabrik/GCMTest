package info.gdgcatania.android.app.gcmtest;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		Intent mIntent= getIntent();
		ViewFragment mFrag=null;
		MainFragment mMainFrag=null;
		
		if (savedInstanceState == null) {
			
			if(mMainFrag!=null){
				getFragmentManager().beginTransaction().remove(mMainFrag).commit();
			}
			if(mFrag!=null){
				getFragmentManager().beginTransaction().remove(mFrag).commit();
			}
			
			mMainFrag = new MainFragment();
			getFragmentManager().beginTransaction()
					.add(R.id.container, mMainFrag).commit();
		}
		
		int type=mIntent.getIntExtra(Constants.INTENT_TYPE, -1);
		
		Log.d("Intent", "type= "+type);
		Log.d("Intent", "action: "+mIntent.getAction());
		Log.d("Intent", "title: "+mIntent.getStringExtra(Constants.TITLE));
		Log.d("Intent", "body: "+mIntent.getStringExtra(Constants.BODY));
		if(type==Constants.OPENNOTI){
			
			if(mMainFrag!=null){
				getFragmentManager().beginTransaction().remove(mMainFrag).commit();
			}
			if(mFrag!=null){
				getFragmentManager().beginTransaction().remove(mFrag).commit();
			}
			
			mFrag = new ViewFragment();
			mFrag.setTitle(mIntent.getStringExtra(Constants.TITLE));
			mFrag.setBody(mIntent.getStringExtra(Constants.BODY));
			getFragmentManager().beginTransaction()
			.add(R.id.container,  mFrag).commit();
		}
			
		
		Constants.setmAppContext(getApplicationContext());
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			Intent aboutIntent = new Intent(getApplicationContext(), About.class);
			startActivity(aboutIntent);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	

}
