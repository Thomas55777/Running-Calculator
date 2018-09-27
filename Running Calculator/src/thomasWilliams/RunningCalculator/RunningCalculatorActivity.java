package thomasWilliams.RunningCalculator;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class RunningCalculatorActivity extends TabActivity implements OnTabChangeListener{
	/** Called when the activity is first created. */
	TabHost tabHost;
	private AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		
		//Set up AdMob Test Emulator
		AdRequest request = new AdRequest();
		request.addTestDevice(AdRequest.TEST_EMULATOR);
		request.addTestDevice("E5C9D2A59DE9935E0346A72780F8D1F0"); // My Samsung I9000 found in Logcat when requesting add in debug mode 
		request.addTestDevice("AF9ECACB4E3312D7EC1FD57677C984BA");
		////////////////////////////////////
		// Set up AdMob Main////////////////
		////////////////////////////////////
		adView = new AdView(this, AdSize.BANNER, "a14f6f8bc07ed0e");
		LinearLayout layoutTop = (LinearLayout) findViewById(R.id.AdMobMainBottom);
		layoutTop.addView(adView);
		adView.loadAd(new AdRequest());

		////////////////////////////////
		//TabHost Setup/////////////////
		////////////////////////////////
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setOnTabChangedListener(this);

		TabSpec TabSpec1 = tabHost.newTabSpec("tid1");
		TabSpec TabSpec2 = tabHost.newTabSpec("tid1");
		TabSpec TabSpec3 = tabHost.newTabSpec("tid1");
		TabSpec TabSpec4 = tabHost.newTabSpec("tid1");

		TabSpec1.setIndicator("Calculate").setContent(new Intent(this, TabCalculate.class));
		TabSpec2.setIndicator("Calories").setContent(new Intent(this, TabCalories.class));
		TabSpec3.setIndicator("Predictions").setContent(new Intent(this, TabPredictions.class));
		TabSpec4.setIndicator("Stopwatch").setContent(new Intent(this, TabStopwatch.class));

		TabSpec1.setIndicator("Calculate", getResources().getDrawable(R.drawable.calculator));
		TabSpec2.setIndicator("Calories", getResources().getDrawable(R.drawable.scales));
		TabSpec3.setIndicator("Predictions", getResources().getDrawable(R.drawable.trend));
		TabSpec4.setIndicator("Stopwatch", getResources().getDrawable(R.drawable.clock));
	
		getResources().getDrawable(R.drawable.ic_launcher);

		tabHost.addTab(TabSpec1);
		tabHost.addTab(TabSpec2);
		tabHost.addTab(TabSpec3);
		//tabHost.addTab(TabSpec4);

		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
		{
			setUnselectedTabs(i);
		}
		tabHost.setCurrentTab(0);
		setSelectedTabs();
		// //////////////////////////////
		// TabHost Setup End/////////////
		// //////////////////////////////
	}

	public void onTabChanged(String tabId) {
		tabHost.getCurrentTab();

		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
		{
			setUnselectedTabs(i);
		}
		setSelectedTabs();
	}

	private void setSelectedTabs() {
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#003663"));
		TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title);
		tv.setTextColor(Color.parseColor("#ffffff"));
		tv.setTypeface(null, 1); //bold
	}

	private void setUnselectedTabs(int i) {
		tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#7392B5"));
		TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
		tv.setTextColor(Color.parseColor("#000000"));
		tv.setTypeface(null, 1);
	}

}
