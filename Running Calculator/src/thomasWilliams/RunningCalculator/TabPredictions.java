package thomasWilliams.RunningCalculator;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TabPredictions extends Activity {
	private AdView adView;
	private SharedPreferences app_preferences;
	private ArrayList<HashMap<String, String>> lstPredictionsResults;
	private Toast errToast = null;
	private String strDistance;
	private String strTime;
	private String strPace;
	private String strSpinDistance;
	private String strWeight;
	private String strWeightType;
	private String strPaceSplitPref;
	private String strSplit;
	private String strPredictedDistance;
	private double dblPredictedDistance;
	private double dblTime;
	private double dblPredictedTime;
	private double dblDistance;
	private String strPredictedTime;
	private double dblPredictedPace;
	private double dblPaceSplitPref;
	private double dblConvertedSplit;
	private double dblConvertedDistance;
	private String strPredictedPace;
	private double dblWeight;
	private double dblConvertedWeight;
	private double dblPredictedCalories;
	private String strPredictedCalories;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabpredictions);
		
		try {
			
		
		app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String strDistancePref = app_preferences.getString("strDistance", "000.0");
		strDistance = strDistancePref;
		dblDistance = Double.parseDouble(strDistance); 
		strSpinDistance = app_preferences.getString("strSpinDistance", "xx");
		dblConvertedDistance= ConvertedMeasure(strSpinDistance);  
				
		String strTimeHourPref = app_preferences.getString("strTimeHour", "00");
		String strTimeMinPref = app_preferences.getString("strTimeMin", "00");
		String strTimeSecPref = app_preferences.getString("strTimeSec", "00.0");
		strTime = SetToProperTimeFormat(strTimeHourPref, strTimeMinPref, strTimeSecPref);

		String strPaceHourPref = app_preferences.getString("strPaceHour", "00");
		String strPaceMinPref = app_preferences.getString("strPaceMin", "00");
		String strPaceSecPref = app_preferences.getString("strPaceSec", "00.0");
		strPace = SetToProperTimeFormat(strPaceHourPref, strPaceMinPref, strPaceSecPref);

		strPaceSplitPref = app_preferences.getString("strPaceSplit", "0.0");
		dblPaceSplitPref = Double.parseDouble(strPaceSplitPref);
		strSplit = app_preferences.getString("strSplit", "xx");
		dblConvertedSplit = ConvertedMeasure(strSplit);  
				
		strWeight = app_preferences.getString("strWeight", "155");
		dblWeight = Double.parseDouble(strWeight);
		strWeightType = app_preferences.getString("strWeightType", "lbs");
		dblConvertedWeight = ConvertedWeight(strWeightType);
		
		//Set up Display with Preferences from other Tabs
		TextView txtPredictionsDistance = (TextView) findViewById(R.id.txtPredictionsDistance);
		TextView txtPredictionsDistanceMeasure = (TextView) findViewById(R.id.txtPredictionsDistanceMeasure);
		TextView txtPredictionsTime = (TextView) findViewById(R.id.txtPredictionsTime);
		TextView txtPredictionsPaceSplit = (TextView) findViewById(R.id.txtPredictionsPaceSplit);
		TextView txtPredictionsWeight = (TextView) findViewById(R.id.txtPredictionsWeight);
		
		String strTimeCleanFormat = CleanTimeFormat(strTime);
		txtPredictionsTime.setText(strTimeCleanFormat);
		txtPredictionsDistance.setText(strDistance);
		String strSpinDistanceClean = strSpinDistance.substring(strSpinDistance.indexOf(" ")+1);
		txtPredictionsDistanceMeasure.setText(strSpinDistanceClean);
		txtPredictionsPaceSplit.setText("("+strPaceSplitPref+" "+strSplit+")");
		txtPredictionsWeight.setText("("+strWeight+" "+strWeightType+")");
		
		//Do Calculations
		SetUpPredictionsListView();
		CalculatePredictions();
		}catch (Exception e){
			errToast = Toast.makeText(this, "Cannot Calculate Predictions with the current inputs for Distance, Time, and Weight.  Please go back to the calculate tab and adjusts the inputs.", Toast.LENGTH_SHORT);
			errToast.show();
			return;
		}
		
	}
	private void SetUpPredictionsListView() {
		// Set up the List
		ListView lstPredictions = (ListView) findViewById(R.id.lstPredictions);
		lstPredictionsResults = new ArrayList<HashMap<String, String>>();
		
		SimpleAdapter loadPredictionsListView = new SimpleAdapter(this, lstPredictionsResults,
				R.layout.mylistpredictions, new String[] { "Distance", "Time", "Pace", "Cal" }, new int[] {
				R.id.PredictionsListDistance, R.id.PredictionsListTime, R.id.PredictionsListPace, R.id.PredictionsListCal});
		lstPredictions.setAdapter(loadPredictionsListView);
	}
	private void PopulatePredictionsListView() {
		//Populate List
		HashMap<String, String>
		map = new HashMap<String, String>();

		map.put("Distance", strPredictedDistance);
		map.put("Time", strPredictedTime);
		map.put("Pace", strPredictedPace);
		map.put("Cal", strPredictedCalories);
		lstPredictionsResults.add(map);
	}
	private void CalculatePredictions() {
		int intPredictionsCount = 18;
		for (int i = 0; i <= intPredictionsCount; i++) {
			if (i==0){
				strPredictedDistance = "400 m";
				dblPredictedDistance = 400.0/1609.344;
			}else if (i==1){
				strPredictedDistance = "1 mi";
				dblPredictedDistance = 1.0;
			}else if (i==2){
				strPredictedDistance = "2,000 m";
				dblPredictedDistance = 2.0/1.609344;
			}else if (i==3){
				strPredictedDistance = "3,000 m";
				dblPredictedDistance = 3.0/1.609344;
			}else if (i==4){
				strPredictedDistance = "2 mi";
				dblPredictedDistance = 2.0;
			}else if (i==5){
				strPredictedDistance = "4,000 m";
				dblPredictedDistance = 4.0/1.609344;
			}else if (i==6){
				strPredictedDistance = "5,000 m";
				dblPredictedDistance = 5.0/1.609344;
			}else if (i==7){
				strPredictedDistance = "6,000 m";
				dblPredictedDistance = 6.0/1.609344;
			}else if (i==8){
				strPredictedDistance = "7,000 m";
				dblPredictedDistance = 7.0/1.609344;
			}else if (i==9){
				strPredictedDistance = "8,000 m";
				dblPredictedDistance = 8.0/1.609344;
			}else if (i==10){
				strPredictedDistance = "5 mi";
				dblPredictedDistance = 5.0;
			}else if (i==11){
				strPredictedDistance = "9,000 m";
				dblPredictedDistance = 9.0/1.609344;
			}else if (i==12){
				strPredictedDistance = "10 km";
				dblPredictedDistance = 10.0/1.609344;
			}else if (i==13){
				strPredictedDistance = "15 km";
				dblPredictedDistance = 15.0/1.609344;
			}else if (i==14){
				strPredictedDistance = "10 mi";
				dblPredictedDistance = 10.0;
			}else if (i==15){
				strPredictedDistance = "20 km";
				dblPredictedDistance = 20.0/1.609344;
			}else if (i==16){
				strPredictedDistance = "Half-Mar\n(13.1 mi)";
				dblPredictedDistance = 13.1;
			}else if (i==17){
				strPredictedDistance = "30 km";
				dblPredictedDistance = 30.0/1.609344;
			}else if (i==18){
				strPredictedDistance = "Marathon\n(26.2 mi)";
				dblPredictedDistance = 26.2;
			}			

		
			//Calculate PredictedTime
			StringToMilliseconds ConvertedTime = new StringToMilliseconds(strTime);
			dblTime=ConvertedTime.getMillisecs(strTime);
			dblPredictedTime = dblTime*Math.pow((dblPredictedDistance/(dblDistance/dblConvertedDistance)),1.06);
			MillisecondsToString ConvertedPredictedTime = new MillisecondsToString(dblPredictedTime);  
			strPredictedTime = ConvertedPredictedTime.getConvertedString();
			strPredictedTime = CleanTimeFormat(strPredictedTime);
			//Calculate PredictedPace
			dblPredictedPace = (dblPaceSplitPref/dblConvertedSplit/dblPredictedDistance)*dblPredictedTime;
			MillisecondsToString ConvertedPredictedPace = new MillisecondsToString(dblPredictedPace);  
			strPredictedPace = ConvertedPredictedPace.getConvertedString();
			strPredictedPace = CleanTimeFormat(strPredictedPace);
			//Calculate Calories
			dblPredictedCalories = (dblWeight/dblConvertedWeight)*(dblPredictedDistance*1.609344)*1.036;
			dblPredictedCalories =Math.round(dblPredictedCalories);
			strPredictedCalories=Integer.toString((int)dblPredictedCalories); 
			//Populate the ListView
			PopulatePredictionsListView();
		}

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (errToast != null){
			errToast.cancel();	
		}
	}

	private String CleanTimeFormat(String strCleanTimeFormat) {
		if (strCleanTimeFormat.substring(0,2).equals("00")) {
			strCleanTimeFormat=strCleanTimeFormat.substring(3);
		}
		if (strCleanTimeFormat.substring(0,1).equals("0")) {
			strCleanTimeFormat=strCleanTimeFormat.substring(1);
		}
		return strCleanTimeFormat;
	}
	private double ConvertedMeasure(String strConvertedMeasure) {
		double dblConvertedMeasure = 0;
		if (strConvertedMeasure.equals("mi") || strConvertedMeasure.equals("[mi] Miles")) {
			dblConvertedMeasure = 1.0;
		}
		if (strConvertedMeasure.equals("km") || strConvertedMeasure.equals("[km] Kilometers")) {
			dblConvertedMeasure = 1.609344;
		}
		if (strConvertedMeasure.equals("m") || strConvertedMeasure.equals("[m] Meters")) {
			dblConvertedMeasure = 1609.344;
		}
		if (strConvertedMeasure.equals("yd") || strConvertedMeasure.equals("[yd] Yards")) {
			dblConvertedMeasure = 1760.0;
		}
		return dblConvertedMeasure;
	}
	private double ConvertedWeight(String strConvertedWeight) {
		double dblConvertedWeight = 0;
		if(strConvertedWeight.equals("Lbs")){
			dblConvertedWeight = 2.20462262;
		}else if(strConvertedWeight.equals("Kg")){
			dblConvertedWeight = 1.0;
		}
		return dblConvertedWeight;
	}
	private String SetToProperTimeFormat(String strHour, String strMin, String strSec) {
		String strCorrectTimeFormat = "00:00:00.0";
		if (strHour.equals("")) {
			strHour = "00";
		}
		if (strMin.equals("")) {
			strMin = "00";
		}
		if (strSec.equals("")) {
			strSec = "00.0";
		}
		while (strHour.length() < 2) {
			strHour = "0" + strHour;
		}
		while (strMin.length() < 2) {
			strMin = "0" + strMin;
		}
		if (strSec.indexOf(".") == -1) {
			strSec = strSec + ".0";
		}
		else if (strSec.indexOf(".") == 0) {
			strSec = "00" + strSec;
		}
		else if (strSec.indexOf(".") == 1) {
			strSec = "0" + strSec;
		}

		strCorrectTimeFormat = strHour + ":" + strMin + ":" + strSec;
		return strCorrectTimeFormat;
	}

	/*
 		setContentView(R.layout.tabpredictions);
 		// startActivity(new Intent(this, SrnPredictions.class));
		TextView textDistance = (TextView) findViewById(R.id.txtDistancePredictions);
		TextView textTime = (TextView) findViewById(R.id.txtTimePredictions);
		TextView textAvgSplit = (TextView) findViewById(R.id.txtAvgSplitPredictions);

		Intent intentGetParentCalcs = getParent().getIntent();
		String strDistance = intentGetParentCalcs.getStringExtra("strDistance");
		String strTime = intentGetParentCalcs.getStringExtra("strTime");
		String strAvgSplit = intentGetParentCalcs.getStringExtra("strAvgSplit");
		boolean booCalculateAccurate = intentGetParentCalcs.getBooleanExtra("booCalculateAccurate", true);
		
		textDistance.setText(strDistance);
		textTime.setText(strTime);
		textAvgSplit.setText(strAvgSplit);

		int intPredictionsCount = 50;
		int intPredictionsDistance = 0;
		double mSplit = 500;
		double dblScrCalculateDistance = Double.parseDouble(strDistance);
		StringToMilliseconds CalculatedMillisecondsTime = new StringToMilliseconds(strAvgSplit);
		double dblScrCalculateAvgSplit = CalculatedMillisecondsTime.getMillisecs(strAvgSplit);
		double dblPredictionsAvgSplit;
		double dblPredictionsTime;
		String strPredictionsAvgSplit;
		String strPredictionsTime;
		// Check to see if Data is usable
		if (booCalculateAccurate == false) {
			return;
		}
		// Set up the List
		ListView list = (ListView) findViewById(R.id.PREDICTIONS);
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		
		for (int i = 0; i < intPredictionsCount; i++) {
			HashMap<String, String>
			map = new HashMap<String, String>();

			//Calculate Distance
			intPredictionsDistance = intPredictionsDistance + 500;

			//Calculate AvgSplit
			dblPredictionsAvgSplit = (logx(intPredictionsDistance/dblScrCalculateDistance,2)*5)/(60*60*24)+(dblScrCalculateAvgSplit);
			dblPredictionsAvgSplit = toRound(dblPredictionsAvgSplit);
			MillisecondsToString ConvertedAvgSplit = new MillisecondsToString(dblPredictionsAvgSplit);
			strPredictionsAvgSplit = ConvertedAvgSplit.getConvertedString();
			
			//Calculate Time
			dblPredictionsTime = (intPredictionsDistance/mSplit)*dblPredictionsAvgSplit;
			dblPredictionsTime = toRound(dblPredictionsTime);
			MillisecondsToString ConvertedTime = new MillisecondsToString(dblPredictionsTime);
			strPredictionsTime = ConvertedTime.getConvertedString();

			//Populate List
			map.put("Distance", Integer.toString(intPredictionsDistance) + " m");
			map.put("Time", strPredictionsTime);
			map.put("AvgSplit", strPredictionsAvgSplit);
			mylist.add(map);
		}

		SimpleAdapter loadPredictions = new SimpleAdapter(this, mylist,
				R.layout.mylistpredictions, new String[] { "Distance", "Time", "AvgSplit" }, new int[] {
						R.id.PredictionsDistance, R.id.PredictionsTime, R.id.PredictionsAvgSplit });
		list.setAdapter(loadPredictions);
	}

	private double toRound(double tempDouble) {
		tempDouble = tempDouble + (1.0/(10*10*10*60*60*24));
		String tempString = Double.toString(tempDouble * 10 * 60 * 60 * 24);

		int toRound = Integer.parseInt(tempString.substring(tempString.indexOf(".") + 1, tempString.indexOf(".") + 2));
		if (toRound >= 5) {
			tempDouble = tempDouble + (1.0 / (10 * 60 * 60 * 24));
		}
		return tempDouble;
	}
*/
	private double logx(double dblNumber, int intBase) {
		return Math.log(dblNumber) / Math.log(intBase);
	
	}

}
