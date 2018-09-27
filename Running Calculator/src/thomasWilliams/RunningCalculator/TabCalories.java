package thomasWilliams.RunningCalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TabCalories extends Activity {
	private Intent i;
	private LinearLayout MainLayout;
	private String strDistance;
	private String strTime;
	private String strPace;
	private double dblWeightFactor;
	private String strWeightType;
	private String strWeight;
	private double dblWeight;
	private double dblDistance;
	private String strAvgSplit;
	private double dblTime;
	private double dblAvgSplit;
	private SharedPreferences app_preferences;
	private int intSpinWeightPositon;
	private String strSpinDistance;
	private String strSpinWeight;
	private String strTimeCleanFormat;
	private Toast errToast = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabcalories);

		app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String strDistancePref = app_preferences.getString("strDistance", "000.0");
		strDistance = strDistancePref;

		String strTimeHourPref = app_preferences.getString("strTimeHour", "00");
		String strTimeMinPref = app_preferences.getString("strTimeMin", "00");
		String strTimeSecPref = app_preferences.getString("strTimeSec", "00.0");
		strTime = SetToProperTimeFormat(strTimeHourPref, strTimeMinPref, strTimeSecPref);
		strTimeCleanFormat = CleanTimeFormat(strTime);
		// strTime = strTimeHourPref + ":" + strTimeMinPref + ":" +
		// strTimeSecPref;

		String strPaceHourPref = app_preferences.getString("strPaceHour", "00");
		String strPaceMinPref = app_preferences.getString("strPaceMin", "00");
		String strPaceSecPref = app_preferences.getString("strPaceSec", "00.0");
		strPace = SetToProperTimeFormat(strPaceHourPref, strPaceMinPref, strPaceSecPref);
		// strPace = strPaceHourPref + ":" + strPaceMinPref + ":" +
		// strPaceSecPref;

		String strPaceSplitPref = app_preferences.getString("strPaceSplit", "0.0");

		String strSplit = app_preferences.getString("strSplit", "xx");

		int intDistanceMeasurePref = app_preferences.getInt("intSpinDistancePositon", 1);
		strSpinDistance = app_preferences.getString("strSpinDistance", "xx");

		int intWeightMeasurePref = app_preferences.getInt("intSpinWeightPositon", 0);
		strWeight = app_preferences.getString("strWeight", "155");

		MainLayout = (LinearLayout) findViewById(R.id.MainLinearLayout01);
		MainLayout.setOnTouchListener(new OnTouchListener() {
			// @Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(MainLayout.getWindowToken(), 0);
				return false;
			}
		});

		// Set up and check for previous inputs
		Spinner spinWeight = (Spinner) findViewById(R.id.spinnerWeight);
		strSpinWeight = String.valueOf(spinWeight.getSelectedItem());
		EditText ediTextWeight = (EditText) findViewById(R.id.editxtWeight);

		if (intWeightMeasurePref != 0) {
			spinWeight.setSelection(intWeightMeasurePref);
		}
		if (strWeight != null) {
			ediTextWeight.setText(strWeight);
		}
		strWeightType = String.valueOf(spinWeight.getSelectedItem());
		System.out.println("strWeightType:= " + strWeightType);

		// Set up Variables from TabCalculate
		TextView txtDistanceCalc = (TextView) findViewById(R.id.txtDistanceCalc);
		TextView txtTimeCalc = (TextView) findViewById(R.id.txtTimeCalc);
		TextView txtPaceCalc = (TextView) findViewById(R.id.txtPaceCalc);

		TextView txtWgtDistance = (TextView) findViewById(R.id.txtWgtDistance);
		TextView txtWgtPace = (TextView) findViewById(R.id.txtWgtPace);
		TextView txtWgtPaceSplit = (TextView) findViewById(R.id.txtWgtPaceSplit);

		txtDistanceCalc.setText(strDistance);
		txtTimeCalc.setText(strTimeCleanFormat);
		txtPaceCalc.setText(strPace);

		txtWgtDistance.setText(strSpinDistance);
		txtWgtPace.setText(strPaceSplitPref);
		txtWgtPaceSplit.setText(strSplit);

		// Check to see if it is all Calculated
		/*
		 * boolean booCalculateAccurate =
		 * app_preferences.getBoolean("booCalculateAccurate", true); if
		 * (booCalculateAccurate == false) { TextView txtCalBurnedError =
		 * (TextView) findViewById(R.id.txtCalBurnedError);
		 * txtCalBurnedError.setVisibility(0); return; }
		 */
		// Perform the initial calculations WeightAdjustedCalculations();
		CalorieAdjustedCalculations();

		// Set Up Text Change Listener
		// ediTextWeight.setOnClickListener((OnClickListener) this);
		ediTextWeight.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable arg0) {

				CalorieAdjustedCalculations();
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

		// Set Up Spinner Change Listener

		spinWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				CalorieAdjustedCalculations();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void CalorieAdjustedCalculations() {
		try {

			TextView txtCalBurned = (TextView) findViewById(R.id.txtCalBurned);
			TextView txtCalHourBurned = (TextView) findViewById(R.id.txtCalHourBurned);

			double dblDistance = Double.parseDouble(strDistance);

			Spinner spinWeight = (Spinner) findViewById(R.id.spinnerWeight);
			strSpinWeight = String.valueOf(spinWeight.getSelectedItem());

			EditText ediTextWeight = (EditText) findViewById(R.id.editxtWeight);
			strWeight = ediTextWeight.getText().toString();
			if (strWeight.equals("")) {
				dblWeight = 0;
			}
			else {
				dblWeight = Double.parseDouble(strWeight);
			}

			// strDistance strTime strPace strSpinDistance strSpinWeight
			// strWeight

			double dblConvertedWgt = 0;
			if (strSpinWeight.equals("Lbs")) {
				dblConvertedWgt = 0.45359237 * dblWeight;
			}
			else if (strSpinWeight.equals("Kg")) {
				dblConvertedWgt = dblWeight;
			}

			double dblMiles = ConvertedMeasure(strSpinDistance);
			double dblCalBurned = dblConvertedWgt * ((dblMiles * 1.609344) * dblDistance) * 1.036;
			dblCalBurned = Math.round(dblCalBurned);
			txtCalBurned.setText(Integer.toString((int) dblCalBurned));

			StringToMilliseconds ConvertedTime = new StringToMilliseconds(strTime);
			dblTime = ConvertedTime.getMillisecs(strTime);
			double dblCalHourBurned = ((1.0 / 24) / dblTime) * dblCalBurned;
			dblCalHourBurned = Math.round(dblCalHourBurned);
			txtCalHourBurned.setText(Integer.toString((int) dblCalHourBurned));

		} catch (Exception e) {
			TextView txtCalBurnedError = (TextView) findViewById(R.id.txtCalBurnedError);
			txtCalBurnedError.setVisibility(0);

			/*
			 * AlertDialog.Builder builder = new AlertDialog.Builder(this);
			 * builder.setTitle("There has been an Error in the Calculation");
			 * builder.setMessage(
			 * "Cannot Calculate Calories with the current inputs for Distance and Time.  Please go back to the calculate tab and adjusts the inputs."
			 * ); builder.setPositiveButton("OK", null); AlertDialog alert =
			 * builder.create(); alert.show();
			 */
			// Toast.makeText(this,
			// "Cannot Calculate Calories with the current inputs for Distance and Time.  Please go back to the calculate tab and adjusts the inputs.",
			// Toast.LENGTH_SHORT).show();

			if (errToast == null) {
				errToast = Toast.makeText(this, "Cannot Calculate Calories with the current inputs for Distance and Time.  Please go back to the calculate tab and adjusts the inputs.", Toast.LENGTH_SHORT);
				errToast.show();
			}
			return;

		}

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
		double dblMiles = (1.0 / dblConvertedMeasure);
		return dblMiles;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (errToast != null){
			errToast.cancel();	
		}
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(MainLayout.getWindowToken(), 0);

		EditText ediTextWeight = (EditText) findViewById(R.id.editxtWeight);
		strWeight = ediTextWeight.getText().toString();

		Spinner spinWeight = (Spinner) findViewById(R.id.spinnerWeight);
		String strWeightType = String.valueOf(spinWeight.getSelectedItem());

		@SuppressWarnings("unchecked")
		ArrayAdapter<String> myAdap = (ArrayAdapter<String>) spinWeight.getAdapter();
		int intSpinnerPosition = myAdap.getPosition(strWeightType);

		/*
		 * i.putExtra("intSpinnerPosition", intSpinnerPosition);
		 * i.putExtra("strWeight", strWeight);
		 */
		SharedPreferences.Editor editor = app_preferences.edit();
		editor.putInt("intSpinWeightPositon", intSpinnerPosition);
		editor.putString("strWeight", strWeight);
		editor.putString("strWeightType", strWeightType);
		editor.commit();
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

	private String CleanTimeFormat(String strCleanTimeFormat) {
		if (strCleanTimeFormat.substring(0, 2).equals("00")) {
			strCleanTimeFormat = strCleanTimeFormat.substring(3);
		}
		if (strCleanTimeFormat.substring(0, 1).equals("0")) {
			strCleanTimeFormat = strCleanTimeFormat.substring(1);
		}
		return strCleanTimeFormat;
	}
}
/*
 * 
 * private void WeightAdjustedCalculations() { EditText ediTextWeight =
 * (EditText) findViewById(R.id.editxtWeight); TextView txtDistanceCalc =
 * (TextView) findViewById(R.id.txtDistanceCalc); TextView txtTimeCalc =
 * (TextView) findViewById(R.id.txtTimeCalc); TextView txtAvgSplitCalc =
 * (TextView) findViewById(R.id.txtAvgSplitCalc); TextView txtDistanceWeight =
 * (TextView) findViewById(R.id.txtDistanceWeight); TextView txtTimeWeight =
 * (TextView) findViewById(R.id.txtTimeWeight); TextView txtAvgSplitWeight =
 * (TextView) findViewById(R.id.txtAvgSplitWeight);
 * 
 * strWeight = ediTextWeight.getText().toString(); strDistance =
 * txtDistanceCalc.getText().toString(); strTime =
 * txtTimeCalc.getText().toString(); strAvgSplit =
 * txtAvgSplitCalc.getText().toString(); if ("".equals(strWeight)) { strWeight =
 * "0"; // return; } dblWeight = Double.parseDouble(strWeight); dblDistance =
 * Double.parseDouble(strDistance); StringToMilliseconds TimeCalc = new
 * StringToMilliseconds(strTime); dblTime = TimeCalc.getMillisecs(strTime);
 * StringToMilliseconds AvgSplitCalc = new StringToMilliseconds(strAvgSplit);
 * dblAvgSplit = AvgSplitCalc.getMillisecs(strAvgSplit); Spinner spinWeight =
 * (Spinner) findViewById(R.id.spinnerWeight); strWeightType =
 * String.valueOf(spinWeight.getSelectedItem());
 * 
 * if ("Lbs".equals(strWeightType)) { dblWeightFactor = Math.pow((dblWeight /
 * 270), (2.0 / 9)); } else if ("Kg".equals(strWeightType)) { dblWeightFactor =
 * Math.pow(((dblWeight * 2.20462262) / 270), (2.0 / 9)); } // Corrected
 * distance = actual distance / Wf // Corrected time = Wf x actual time
 * (seconds)
 * 
 * // Calculate Weight Adjusted Distance long lngDistanceWeightAdjusted =
 * Math.round(dblDistance / dblWeightFactor); if (lngDistanceWeightAdjusted >
 * 999999) { txtDistanceWeight.setText("999999"); } else {
 * txtDistanceWeight.setText(Long.toString(lngDistanceWeightAdjusted)); }
 * 
 * // Calculate Weight Adjusted Time double dblTimeWeightAdjusted =
 * dblWeightFactor * dblTime; dblTimeWeightAdjusted =
 * toRound(dblTimeWeightAdjusted); MillisecondsToString TimeWeightAdjusted = new
 * MillisecondsToString(dblTimeWeightAdjusted); String strTimeWeightAdjusted =
 * TimeWeightAdjusted.getConvertedString();
 * txtTimeWeight.setText(strTimeWeightAdjusted);
 * 
 * // Calculate Weight Adjusted AvgSplit //Use TabCalculate Distance and //
 * Weight AdjustedTime double dblAvgSplitWeightAdjusted = (dblTimeWeightAdjusted
 * * 500) / dblDistance; dblAvgSplitWeightAdjusted =
 * toRound(dblAvgSplitWeightAdjusted); MillisecondsToString
 * AvgSplitWeightAdjusted = new MillisecondsToString(dblAvgSplitWeightAdjusted);
 * String strAvgSplitWeightAdjusted =
 * AvgSplitWeightAdjusted.getConvertedString();
 * txtAvgSplitWeight.setText(strAvgSplitWeightAdjusted); }
 * 
 * private double toRound(double tempDouble) { tempDouble = tempDouble + (1.0 /
 * (10 * 10 * 10 * 60 * 60 * 24)); String tempString =
 * Double.toString(tempDouble * 10 * 60 * 60 * 24);
 * 
 * int toRound = Integer.parseInt(tempString.substring(tempString.indexOf(".") +
 * 1, tempString.indexOf(".") + 2)); if (toRound >= 5) { tempDouble = tempDouble
 * + (1.0 / (10 * 60 * 60 * 24)); } return tempDouble; }
 */
