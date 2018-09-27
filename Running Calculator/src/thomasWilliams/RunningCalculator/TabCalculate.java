package thomasWilliams.RunningCalculator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class TabCalculate extends Activity implements OnClickListener {
	Button btnDistance;
	Button btnTime;
	Button btnPace;
	Button btnSplit;

	EditText editxtDistance;
	EditText editxtTimeHour;
	EditText editxtTimeMin;
	EditText editxtTimeSec;
	EditText editxtPaceHour;
	EditText editxtPaceMin;
	EditText editxtPaceSec;
	EditText editxtPaceSplit;

	TextView txtMPH;
	TextView txtKPH;

	private LinearLayout MainLayout;
	private String strDistance;
	private String strTimeHour;
	private String strTimeMin;
	private String strTimeSec;
	private String strPaceHour;
	private String strPaceMin;
	private String strPaceSec;
	private String strPaceSplit;
	private String strTime;
	private String strPace;
	double dblSplit;
	private double dblDistance;
	private double dblTime;
	private double dblPace;

	TabHost tabHost;
	String tempString;
	double tempDouble = 0;
	double toRound = 0;
	boolean booSpinnerOnCreate = true;
	private SharedPreferences app_preferences;
	private int intDistanceMeasure;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabcalculate);

		MainLayout = (LinearLayout) findViewById(R.id.MainLinearLayout01);
		MainLayout.setOnTouchListener(new OnTouchListener() {
			// @Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(MainLayout.getWindowToken(), 0);
				return false;
			}
		});
		Spinner spnDistanceMeasure = (Spinner) findViewById(R.id.spinTabCalcDistance);
		// spnDistanceMeasure.setSelection(1);
		spnDistanceMeasure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (booSpinnerOnCreate) {
					booSpinnerOnCreate = false;
				}
				else {
					SetTextViewFormatting(0);
					// onClick(btnDistance);
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		// Add My Code Below
		btnDistance = (Button) findViewById(R.id.btnDistance);
		btnTime = (Button) findViewById(R.id.btnTime);
		btnPace = (Button) findViewById(R.id.btnPace);
		btnSplit = (Button) findViewById(R.id.btnPaceSplit);
		editxtDistance = (EditText) findViewById(R.id.editxtDistance);
		editxtTimeHour = (EditText) findViewById(R.id.editxtTimeHour);
		editxtTimeMin = (EditText) findViewById(R.id.editxtTimeMin);
		editxtTimeSec = (EditText) findViewById(R.id.editxtTimeSec);
		editxtPaceHour = (EditText) findViewById(R.id.editxtPaceHour);
		editxtPaceMin = (EditText) findViewById(R.id.editxtPaceMin);
		editxtPaceSec = (EditText) findViewById(R.id.editxtPaceSec);
		editxtPaceSplit = (EditText) findViewById(R.id.editxtPaceSplit);
		txtMPH = (TextView) findViewById(R.id.txtMPH);
		txtKPH = (TextView) findViewById(R.id.txtKPH);

		btnDistance.setOnClickListener((OnClickListener) this);
		btnTime.setOnClickListener((OnClickListener) this);
		btnPace.setOnClickListener((OnClickListener) this);
		btnSplit.setOnClickListener((OnClickListener) this);

		// Set Cursor to always goto the end when being focused
		EditText ediDistance = (EditText) findViewById(R.id.editxtDistance);
		int posediDistance = ediDistance.getText().length();
		ediDistance.setSelection(posediDistance);

		EditText ediTimeHour = (EditText) findViewById(R.id.editxtTimeHour);
		int posediTimeHour = ediTimeHour.getText().length();
		ediTimeHour.setSelection(posediTimeHour);

		EditText ediTimeMin = (EditText) findViewById(R.id.editxtTimeMin);
		int posediTimeMin = ediTimeMin.getText().length();
		ediTimeMin.setSelection(posediTimeMin);

		EditText ediTimeSec = (EditText) findViewById(R.id.editxtTimeSec);
		int posediTimeSec = ediTimeSec.getText().length();
		ediTimeSec.setSelection(posediTimeSec);

		EditText ediPaceHour = (EditText) findViewById(R.id.editxtPaceHour);
		int posediPaceHour = ediPaceHour.getText().length();
		ediPaceHour.setSelection(posediPaceHour);

		EditText ediPaceMin = (EditText) findViewById(R.id.editxtPaceMin);
		int posediPaceMin = ediPaceMin.getText().length();
		ediPaceMin.setSelection(posediPaceMin);

		EditText ediPaceSec = (EditText) findViewById(R.id.editxtPaceSec);
		int posediPaceSec = ediPaceSec.getText().length();
		ediPaceSec.setSelection(posediPaceSec);

		EditText ediPaceSplit = (EditText) findViewById(R.id.editxtPaceSplit);
		int posediPaceSplit = ediPaceSplit.getText().length();
		ediPaceSplit.setSelection(posediPaceSplit);
		// Set Cursor to always goto the end when being focused

		// Use this to Switch Screens--> startActivity(new Intent(this,
		// SrnPredictions.class));
		app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String strDistancePref = app_preferences.getString("strDistance", "10.0");

		String strTimeHourPref = app_preferences.getString("strTimeHour", "00");
		String strTimeMinPref = app_preferences.getString("strTimeMin", "43");
		String strTimeSecPref = app_preferences.getString("strTimeSec", "30.0");

		String strPaceHourPref = app_preferences.getString("strPaceHour", "00");
		String strPaceMinPref = app_preferences.getString("strPaceMin", "07");
		String strPaceSecPref = app_preferences.getString("strPaceSec", "00.0");

		String strPaceSplitPref = app_preferences.getString("strPaceSplit", "1.0");
		String strMPHPref = app_preferences.getString("strMPH", "8.57");
		String strKPHPref = app_preferences.getString("strKPH", "13.79");

		String strSplit = app_preferences.getString("strSplit", "mi");
		int intDistanceMeasurePref = app_preferences.getInt("intSpinDistancePositon", 1);

		editxtDistance.setText(strDistancePref);
		editxtTimeHour.setText(strTimeHourPref);
		editxtTimeMin.setText(strTimeMinPref);
		editxtTimeSec.setText(strTimeSecPref);
		editxtPaceHour.setText(strPaceHourPref);
		editxtPaceMin.setText(strPaceMinPref);
		editxtPaceSec.setText(strPaceSecPref);
		editxtPaceSplit.setText(strPaceSplitPref);
		txtMPH.setText(strMPHPref);
		txtKPH.setText(strKPHPref);
		btnSplit.setText(strSplit);
		spnDistanceMeasure.setSelection(intDistanceMeasurePref);

		/*
		 * Intent i = getParent().getIntent(); String strDistance =
		 * i.getStringExtra("strDistance");
		 * 
		 * String strTimeHour = i.getStringExtra("strTimeHour"); String
		 * strTimeMin = i.getStringExtra("strTimeMin"); String strTimeSec =
		 * i.getStringExtra("strTimeSec");
		 * 
		 * String strPaceHour = i.getStringExtra("strPaceHour"); String
		 * strPaceMin = i.getStringExtra("strPaceMin"); String strPaceSec =
		 * i.getStringExtra("strPaceSec");
		 * 
		 * String strPaceSplit = i.getStringExtra("strPaceSplit");
		 * 
		 * if (strDistance != null && strTimeHour != null && strTimeMin != null
		 * && strTimeSec != null && strPaceHour != null && strPaceMin != null &&
		 * strPaceSec != null && strPaceSplit != null) {
		 * editxtDistance.setText(strDistance);
		 * editxtTimeHour.setText(strTimeHour);
		 * editxtTimeMin.setText(strTimeMin); editxtTimeSec.setText(strTimeSec);
		 * editxtPaceHour.setText(strPaceHour);
		 * editxtPaceMin.setText(strPaceMin); editxtPaceSec.setText(strPaceSec);
		 * editxtPaceSplit.setText(strPaceSplit); }
		 */
		String strDistanceMeasure = String.valueOf(spnDistanceMeasure.getSelectedItem());
		ArrayAdapter<String> myAdap = (ArrayAdapter<String>) spnDistanceMeasure.getAdapter();
		intDistanceMeasure = myAdap.getPosition(strDistanceMeasure);

		StoreIntent(editxtDistance.getText().toString(), editxtTimeHour.getText().toString(), editxtTimeMin.getText().toString(), editxtTimeSec.getText().toString(), editxtPaceHour.getText().toString(), editxtPaceMin.getText().toString(), editxtPaceSec.getText().toString(), editxtPaceSplit.getText().toString(), txtMPH.getText().toString(), txtKPH.getText().toString(), btnSplit.getText().toString(), intDistanceMeasure,strDistanceMeasure);
		editxtPaceSplit.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			public void afterTextChanged(Editable s) {
				SetTextViewFormatting(0);
			}
		});
		editxtDistance.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			public void afterTextChanged(Editable s) {
				SetTextViewFormatting(0);
			}
		});
		editxtTimeHour.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			public void afterTextChanged(Editable s) {
				SetTextViewFormatting(0);
			}
		});
		editxtTimeMin.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			public void afterTextChanged(Editable s) {
				SetTextViewFormatting(0);
			}
		});
		editxtPaceHour.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			public void afterTextChanged(Editable s) {
				SetTextViewFormatting(0);
			}
		});
		editxtPaceMin.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			public void afterTextChanged(Editable s) {
				SetTextViewFormatting(0);
			}
		});
		editxtTimeSec.addTextChangedListener(new TextWatcher() {
			private String editxtTimeTextChanged;
			public String editxtTimeTextChangedCharacter;

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				editxtTimeTextChanged = s.toString();
				if (editxtTimeTextChanged.length() >= 1) {
					editxtTimeTextChangedCharacter = editxtTimeTextChanged.substring(editxtTimeTextChanged.length() - 1, editxtTimeTextChanged.length());
					System.out.println("editxtTimeTextChanged:= " + editxtTimeTextChangedCharacter);
					System.out.println(editxtTimeSec.getText().toString().charAt(editxtTimeSec.getText().toString().length() - 1));
				}
				if (before == 0) {
					if (editxtTimeSec.getText().toString().length() == 3 && editxtTimeSec.getText().toString().indexOf(".") == -1) {
						if (isNumeric(String.valueOf(editxtTimeSec.getText().toString().charAt(editxtTimeSec.getText().toString().length() - 1)))) {
							String editxtInString = editxtTimeSec.getText().toString();
							String LastKeyPressed = String.valueOf(editxtTimeSec.getText().toString().charAt(editxtTimeSec.getText().toString().length() - 1));
							String editxtNewString = editxtInString.substring(0, editxtInString.length() - 1) + "." + LastKeyPressed;
							editxtTimeSec.setTextKeepState(editxtNewString);
							editxtTimeSec.setSelection(editxtTimeSec.getText().length());
						}
					}
					if (editxtTimeSec.getText().toString().indexOf(".") != editxtTimeSec.getText().toString().lastIndexOf(".")) {
						editxtTimeSec.setTextKeepState(editxtTimeSec.getText().toString().substring(0, editxtTimeSec.getText().toString().length() - 1));
					}
				}
			}

			public void afterTextChanged(Editable s) {
				SetTextViewFormatting(0);
			}
		});
		editxtPaceSec.addTextChangedListener(new TextWatcher() {
			private String editxtPaceTextChanged;
			public String editxtPaceTextChangedCharacter;

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				editxtPaceTextChanged = s.toString();
				if (editxtPaceTextChanged.length() >= 1) {
					editxtPaceTextChangedCharacter = editxtPaceTextChanged.substring(editxtPaceTextChanged.length() - 1, editxtPaceTextChanged.length());
					System.out.println("editxtPaceTextChanged:= " + editxtPaceTextChangedCharacter);
					System.out.println(editxtPaceSec.getText().toString().charAt(editxtPaceSec.getText().toString().length() - 1));
				}
				if (before == 0) {
					if (editxtPaceSec.getText().toString().length() == 3 && editxtPaceSec.getText().toString().indexOf(".") == -1) {
						if (isNumeric(String.valueOf(editxtPaceSec.getText().toString().charAt(editxtPaceSec.getText().toString().length() - 1)))) {
							String editxtInString = editxtPaceSec.getText().toString();
							String LastKeyPressed = String.valueOf(editxtPaceSec.getText().toString().charAt(editxtPaceSec.getText().toString().length() - 1));
							String editxtNewString = editxtInString.substring(0, editxtInString.length() - 1) + "." + LastKeyPressed;
							editxtPaceSec.setTextKeepState(editxtNewString);
							editxtPaceSec.setSelection(editxtPaceSec.getText().length());
						}
					}
					if (editxtPaceSec.getText().toString().indexOf(".") != editxtPaceSec.getText().toString().lastIndexOf(".")) {
						editxtPaceSec.setTextKeepState(editxtPaceSec.getText().toString().substring(0, editxtPaceSec.getText().toString().length() - 1));
					}
				}
			}

			public void afterTextChanged(Editable s) {
				SetTextViewFormatting(0);
			}
		});
	}

	public static boolean isInteger(String str) {
		try {
			int i = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	@Override
	public void onDestroy() {
		// adViewTop.destroy();
		super.onDestroy();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(MainLayout.getWindowToken(), 0);
		try {
			Spinner spnDistanceMeasure = (Spinner) findViewById(R.id.spinTabCalcDistance);
			String strDistanceMeasure = String.valueOf(spnDistanceMeasure.getSelectedItem());
			ArrayAdapter<String> myAdap = (ArrayAdapter<String>) spnDistanceMeasure.getAdapter();
			intDistanceMeasure = myAdap.getPosition(strDistanceMeasure);

			StoreIntent(editxtDistance.getText().toString(), editxtTimeHour.getText().toString(), editxtTimeMin.getText().toString(), editxtTimeSec.getText().toString(), editxtPaceHour.getText().toString(), editxtPaceMin.getText().toString(), editxtPaceSec.getText().toString(), editxtPaceSplit.getText().toString(), txtMPH.getText().toString(), txtKPH.getText().toString(), btnSplit.getText().toString(), intDistanceMeasure, strDistanceMeasure);
		} catch (Exception e) {
			SharedPreferences.Editor editor = app_preferences.edit();
			editor.clear();
			editor.commit();
		}
	}

	public void onClick(View v) {
		try {
			double dblConvertedDistance;
			double dblConvertedPace;
			double dblCoefficientSplit;

			strDistance = editxtDistance.getText().toString();

			strTimeHour = editxtTimeHour.getText().toString();
			strTimeMin = editxtTimeMin.getText().toString();
			strTimeSec = editxtTimeSec.getText().toString();
			strTime = SetToProperTimeFormat(strTimeHour, strTimeMin, strTimeSec);

			strPaceHour = editxtPaceHour.getText().toString();
			strPaceMin = editxtPaceMin.getText().toString();
			strPaceSec = editxtPaceSec.getText().toString();
			strPace = SetToProperTimeFormat(strPaceHour, strPaceMin, strPaceSec);

			Spinner spnDistanceMeasure = (Spinner) findViewById(R.id.spinTabCalcDistance);
			String strDistanceMeasure = String.valueOf(spnDistanceMeasure.getSelectedItem());

			Button btnViewSplit = (Button) findViewById(R.id.btnPaceSplit);
			System.out.println("PaceString:=  " + btnViewSplit.getText().toString());

			EditText editxtPaceSplit = (EditText) findViewById(R.id.editxtPaceSplit);
			String strPaceSplit = editxtPaceSplit.getText().toString();

			dblConvertedDistance = ConvertedMeasure(strDistanceMeasure);
			dblConvertedPace = ConvertedMeasure(btnViewSplit.getText().toString());
			dblCoefficientSplit = Double.parseDouble(strPaceSplit);
			if (v.getId() == R.id.btnPaceSplit) {
				if (btnViewSplit.getText().toString().equals("mi")) {
					btnViewSplit.setText("km");
				}
				else if (btnViewSplit.getText().toString().equals("km")) {
					btnViewSplit.setText("m");
				}
				else if (btnViewSplit.getText().toString().equals("m")) {
					btnViewSplit.setText("yd");
				}
				else if (btnViewSplit.getText().toString().equals("yd")) {
					btnViewSplit.setText("mi");
				}
				// dblConvertedPace =
				// ConvertedMeasure(btnViewSplit.getText().toString());
				SetTextViewFormatting(0);
			}
			if (v.getId() == R.id.btnDistance) {
				StringToMilliseconds CalculatedMillisecondsTime = new StringToMilliseconds(strTime);
				double dblTime = CalculatedMillisecondsTime.getMillisecs(strTime);
				// Convert StringPace into DoublePace
				StringToMilliseconds CalculatedMillisecondsAvgSplit = new StringToMilliseconds(strPace);
				double dblPace = CalculatedMillisecondsAvgSplit.getMillisecs(strPace);
				// Calculate Distance
				double dblDistance = CalculateDistance(dblTime, dblPace, dblConvertedDistance, dblConvertedPace, dblCoefficientSplit);

				strDistance = Double.toString(dblDistance);
				editxtDistance.setText(strDistance);

				System.out.println(strDistance);
				CalculateMPH(dblDistance, dblTime, dblConvertedDistance);
				CleanUpNullEditTexts();
				StoreData(strDistance, strTime, strPace);
				SetTextViewFormatting(1);
			} else if (v.getId() == R.id.btnTime) {
				// Convert StringPace into DoublePace
				StringToMilliseconds CalculatedMillisecondsAvgSplit = new StringToMilliseconds(strPace);
				double dblPace = CalculatedMillisecondsAvgSplit.getMillisecs(strPace);
				// Convert StringDistance into DoubleDistance
				double dblDistance = Double.parseDouble(strDistance);
				// Calculate Time
				double dblTime = CalculateTime(dblDistance, dblPace, dblConvertedDistance, dblConvertedPace, dblCoefficientSplit);

				MillisecondsToString ConvertedString = new MillisecondsToString(dblTime);
				strTime = ConvertedString.getConvertedString();
				editxtTimeHour.setText(strTime.substring(0, 2));
				editxtTimeMin.setText(strTime.substring(3, 5));
				editxtTimeSec.setText(strTime.substring(6, strTime.length()));
				System.out.println(strTime);
				CalculateMPH(dblDistance, dblTime, dblConvertedDistance);
				CleanUpNullEditTexts();
				StoreData(strDistance, strTime, strPace);
				SetTextViewFormatting(2);
			} else if (v.getId() == R.id.btnPace) {
				// Convert StringTime into DoubleTime
				StringToMilliseconds CalculatedMillisecondsTime = new StringToMilliseconds(strTime);
				double dblTime = CalculatedMillisecondsTime.getMillisecs(strTime);
				// Convert StringDistance into DoubleDistance
				double dblDistance = Double.parseDouble(strDistance);
				// Calculate Pace
				double dblPace = CalculatePace(dblDistance, dblTime, dblConvertedDistance, dblConvertedPace, dblCoefficientSplit);

				MillisecondsToString ConvertedString = new MillisecondsToString(dblPace);
				strPace = ConvertedString.getConvertedString();
				editxtPaceHour.setText(strPace.substring(0, 2));
				editxtPaceMin.setText(strPace.substring(3, 5));
				editxtPaceSec.setText(strPace.substring(6, strPace.length()));
				System.out.println(strPace);
				CalculateMPH(dblDistance, dblTime, dblConvertedDistance);
				CleanUpNullEditTexts();
				StoreData(strDistance, strTime, strPace);
				SetTextViewFormatting(3);
			}
		} catch (Exception e) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("There has been an Error in the Calculation");
			builder.setMessage("Please Enter 'Time' and 'Pace' in hh:mm:ss.0 Format");
			builder.setPositiveButton("OK", null);
			AlertDialog alert = builder.create();
			alert.show();
			// I could also use the below if I wanted more customization in my
			// dialog box
			// AlertDialog dialog = builder.show();
			// Must call show() prior to fetching text view
			// TextView messageView =
			// (TextView)dialog.findViewById(android.R.id.message);
			// messageView.setGravity(Gravity.CENTER);

		}
	}

	private void CalculateMPH(double dblDistance, double dblTime, double dblConvertedDistance) {
		TextView txtViewMPH = (TextView) findViewById(R.id.txtMPH);
		TextView txtViewKPH = (TextView) findViewById(R.id.txtKPH);
		double dblMPH = (dblDistance / dblConvertedDistance) / (dblTime / (1.0 / 24));

		double dblKMConversion = ConvertedMeasure("km");
		double dblKPH = dblMPH * dblKMConversion;

		dblMPH = (dblMPH * 100);
		dblMPH = Math.round(dblMPH);
		dblMPH = (dblMPH / 100);

		dblKPH = (dblKPH * 100);
		dblKPH = Math.round(dblKPH);
		dblKPH = (dblKPH / 100);

		if (dblMPH > 99.99) {
			dblMPH = 99.99;
		}
		if (dblKPH > 99.99) {
			dblKPH = 99.99;
		}
		String strMPH = Double.toString(dblMPH);
		String strKPH = Double.toString(dblKPH);

		txtViewMPH.setText(strMPH);
		txtViewKPH.setText(strKPH);
	}

	private void CleanUpNullEditTexts() {
		EditText editxtPaceSplit = (EditText) findViewById(R.id.editxtPaceSplit);
		EditText editxtDistance = (EditText) findViewById(R.id.editxtDistance);
		EditText editxtTimeHour = (EditText) findViewById(R.id.editxtTimeHour);
		EditText editxtTimeMin = (EditText) findViewById(R.id.editxtTimeMin);
		EditText editxtTimeSec = (EditText) findViewById(R.id.editxtTimeSec);
		EditText editxtPaceHour = (EditText) findViewById(R.id.editxtPaceHour);
		EditText editxtPaceMin = (EditText) findViewById(R.id.editxtPaceMin);
		EditText editxtPaceSec = (EditText) findViewById(R.id.editxtPaceSec);

		if (editxtPaceSplit.getText().toString().equals("")) {
			editxtPaceSplit.setText("0.0");
		}
		if (editxtDistance.getText().toString().equals("")) {
			editxtDistance.setText("0.0");
		}
		if (editxtTimeHour.getText().toString().equals("")) {
			editxtTimeHour.setText("00");
		}
		if (editxtTimeMin.getText().toString().equals("")) {
			editxtTimeMin.setText("00");
		}
		if (editxtTimeSec.getText().toString().equals("")) {
			editxtTimeSec.setText("00.0");
		}
		if (editxtPaceHour.getText().toString().equals("")) {
			editxtPaceHour.setText("00");
		}
		if (editxtPaceMin.getText().toString().equals("")) {
			editxtPaceMin.setText("00");
		}
		if (editxtPaceSec.getText().toString().equals("")) {
			editxtPaceSec.setText("00.0");
		}
		findViewById(R.id.MainLinearLayout01).requestFocus();
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

	public void SetTextViewFormatting(int txtButtonPressed) {
		TextView txtViewDistance = (TextView) findViewById(R.id.txtDistanceCalc);
		TextView txtViewTime = (TextView) findViewById(R.id.txtTimeCalc);
		TextView txtViewPace = (TextView) findViewById(R.id.txtPaceCalc);
		TextView txtViewPer = (TextView) findViewById(R.id.txtPer);

		EditText editxtViewDistance = (EditText) findViewById(R.id.editxtDistance);
		EditText editxtViewTimeHour = (EditText) findViewById(R.id.editxtTimeHour);
		EditText editxtViewTimeMin = (EditText) findViewById(R.id.editxtTimeMin);
		EditText editxtViewTimeSec = (EditText) findViewById(R.id.editxtTimeSec);
		EditText editxtViewPaceHour = (EditText) findViewById(R.id.editxtPaceHour);
		EditText editxtViewPaceMin = (EditText) findViewById(R.id.editxtPaceMin);
		EditText editxtViewPaceSec = (EditText) findViewById(R.id.editxtPaceSec);

		Button btnViewDistance = (Button) findViewById(R.id.btnDistance);
		Button btnViewTime = (Button) findViewById(R.id.btnTime);
		Button btnViewPace = (Button) findViewById(R.id.btnPace);

		String txtDistanceCalc = txtViewDistance.getText().toString();
		String txtTimeCalc = txtViewTime.getText().toString();
		String txtPaceCalc = txtViewPace.getText().toString();
		String txtPer = txtViewPer.getText().toString();

		String btnDistanceCalc = btnViewDistance.getText().toString();
		String btnTimeCalc = btnViewTime.getText().toString();
		String btnPaceCalc = btnViewPace.getText().toString();

		txtViewDistance.setText(Html.fromHtml(txtDistanceCalc));
		txtViewTime.setText(Html.fromHtml(txtTimeCalc));
		txtViewPace.setText(Html.fromHtml(txtPaceCalc));
		txtViewPer.setText(Html.fromHtml(txtPer));

		// it accepts 0xAARRGGBB AA for Alpha //(Color.BLUE);
		// //Color.rgb(0,0,255);
		editxtViewDistance.setTextColor(0xFF000000);
		editxtViewTimeHour.setTextColor(0xFF000000);
		editxtViewTimeMin.setTextColor(0xFF000000);
		editxtViewTimeSec.setTextColor(0xFF000000);
		editxtViewPaceHour.setTextColor(0xFF000000);
		editxtViewPaceMin.setTextColor(0xFF000000);
		editxtViewPaceSec.setTextColor(0xFF000000);

		btnViewDistance.setText(Html.fromHtml(btnDistanceCalc));
		btnViewTime.setText(Html.fromHtml(btnTimeCalc));
		btnViewPace.setText(Html.fromHtml(btnPaceCalc));

		boolean booCalculateAccurate = false;
		SharedPreferences.Editor editor = app_preferences.edit();
		editor.putBoolean("booCalculateAccurate", booCalculateAccurate);

		Intent i = getParent().getIntent();
		i.putExtra("booCalculateAccurate", booCalculateAccurate);

		if (txtButtonPressed == 1) {
			booCalculateAccurate = true;
			i.putExtra("booCalculateAccurate", booCalculateAccurate);
			editor.putBoolean("booCalculateAccurate", booCalculateAccurate);

			txtViewDistance.setText(Html.fromHtml("<font color=#8888ff>" + "<b>" + txtDistanceCalc + "</b>" + "</font>"));
			btnViewDistance.setText(Html.fromHtml("<font color=blue>" + "<b>" + btnDistanceCalc + "</b>" + "</font>"));
			editxtViewDistance.setTextColor(0xFF0000ff);
		} else if (txtButtonPressed == 2) {
			booCalculateAccurate = true;
			i.putExtra("booCalculateAccurate", booCalculateAccurate);
			editor.putBoolean("booCalculateAccurate", booCalculateAccurate);

			txtViewTime.setText(Html.fromHtml("<font color=#8888ff>" + "<b>" + txtTimeCalc + "</b>" + "</font>"));
			btnViewTime.setText(Html.fromHtml("<font color=blue>" + "<b>" + btnTimeCalc + "</b>" + "</font>"));
			editxtViewTimeHour.setTextColor(0xFF0000ff);
			editxtViewTimeMin.setTextColor(0xFF0000ff);
			editxtViewTimeSec.setTextColor(0xFF0000ff);
		} else if (txtButtonPressed == 3) {
			booCalculateAccurate = true;
			i.putExtra("booCalculateAccurate", booCalculateAccurate);
			editor.putBoolean("booCalculateAccurate", booCalculateAccurate);

			txtViewPace.setText(Html.fromHtml("<font color=#8888ff>" + txtPaceCalc + "</font>"));
			txtViewPer.setText(Html.fromHtml("<font color=#8888ff>" + txtPer + "</font>"));
			btnViewPace.setText(Html.fromHtml("<font color=blue>" + "<b>" + btnPaceCalc + "</b>" + "</font>"));
			editxtViewPaceHour.setTextColor(0xFF0000ff);
			editxtViewPaceMin.setTextColor(0xFF0000ff);
			editxtViewPaceSec.setTextColor(0xFF0000ff);
		}
		editor.commit();
	}

	// ///////////////////////////////////////////////////////
	// Methods to Calculate the Distance, Time, and Pace//
	// ///////////////////////////////////////////////////////

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

	private double CalculateDistance(double dblTime, double dblPace, double dblConvertedDistance, double dblConvertedPace, double dblCoefficientSplit) {
		dblDistance = (dblTime * dblConvertedDistance) * ((dblCoefficientSplit / dblConvertedPace) / dblPace);
		System.out.println(dblDistance);
		dblDistance = (dblDistance * 10);
		dblDistance = Math.round(dblDistance);
		dblDistance = (dblDistance / 10);
		if (dblDistance > 999999.9) {
			dblDistance = 999999.9;
		}
		return dblDistance;
	}

	private double CalculateTime(double dblDistance, double dblPace, double dblConvertedDistance, double dblConvertedPace, double dblCoefficientSplit) {
		dblTime = dblPace / ((dblConvertedDistance * (dblCoefficientSplit / dblConvertedPace)) / dblDistance);
		// add 00:00.001 seconds due to truncated value in calculating
		dblTime = dblTime + (1.0 / (10 * 10 * 10 * 60 * 60 * 24));
		dblTime = toRound(dblTime);
		return dblTime;
	}

	private double CalculatePace(double dblDistance, double dblTime, double dblConvertedDistance, double dblConvertedPace, double dblCoefficientSplit) {
		dblPace = ((dblConvertedDistance * (dblCoefficientSplit / dblConvertedPace)) / dblDistance) * dblTime;
		// add 00:00.001 seconds due to truncated value in calculating
		dblPace = dblPace + (1.0 / (10 * 10 * 10 * 60 * 60 * 24));
		dblPace = toRound(dblPace);
		return dblPace;
	}

	private double toRound(double dblUnroundedTime) {
		String strUnroundedTime = Double.toString(dblUnroundedTime * 10 * 60 * 60 * 24);
		toRound = Integer.parseInt(strUnroundedTime.substring(strUnroundedTime.indexOf(".") + 1, strUnroundedTime.indexOf(".") + 2));
		if (toRound >= 5) {
			dblUnroundedTime = dblUnroundedTime + (1.0 / (10 * 60 * 60 * 24));
		}
		return dblUnroundedTime;
	}

	private void StoreData(String strDistance, String strTime, String strPace) {

		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
		String strDate = df.format(c.getTime());

		StoreDataInSQLite DataStore = new StoreDataInSQLite(this);
		DataStore.open();
		DataStore.createRow(strDistance, strTime, strPace, strDate);
		DataStore.close();
	}

	private void StoreIntent(String strDistance,
			String strTimeHour, String strTimeMin, String strTimeSec,
			String strPaceHour, String strPaceMin, String strPaceSec,
			String strPaceSplit, String strMPH, String strKPH, String strSplit, int intSpinDistancePositon, String strSpinDistance) {
		/*
		 * Intent i = getParent().getIntent(); i.putExtra("strDistance",
		 * strDistance); i.putExtra("strTimeHour", strTimeHour);
		 * i.putExtra("strTimeMin", strTimeMin); i.putExtra("strTimeSec",
		 * strTimeSec); i.putExtra("strPaceHour", strPaceHour);
		 * i.putExtra("strPaceMin", strPaceMin); i.putExtra("strPaceSec",
		 * strPaceSec); i.putExtra("strPaceSplit", strPaceSec);
		 */

		SharedPreferences.Editor editor = app_preferences.edit();
		editor.putString("strDistance", strDistance);
		editor.putString("strTimeHour", strTimeHour);
		editor.putString("strTimeMin", strTimeMin);
		editor.putString("strTimeSec", strTimeSec);
		editor.putString("strPaceHour", strPaceHour);
		editor.putString("strPaceMin", strPaceMin);
		editor.putString("strPaceSec", strPaceSec);
		editor.putString("strPaceSplit", strPaceSplit);
		editor.putString("strMPH", strMPH);
		editor.putString("strKPH", strKPH);
		editor.putString("strSplit", strSplit);
		editor.putInt("intSpinDistancePositon", intSpinDistancePositon);
		editor.putString("strSpinDistance", strSpinDistance);

		editor.commit(); // Very important
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		super.onCreateOptionsMenu(menu);
		MenuInflater mnuInflater = getMenuInflater();
		mnuInflater.inflate(R.menu.main_menu, menu);
		return true;*/
		return false;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// case R.id.menuMainHistory:startActivity(new Intent(this,
		// SrnHistory.class));
		case R.id.menuMainHistory:
			startActivity(new Intent(this, SrnHistory.class));
			return true;
		case R.id.menuMainHelp:
			startActivity(new Intent(this, SrnHelp.class));
			// Set Up Later
			return true;
		}
		return false;
	}

	public String getEditTextDistance() {
		EditText ediText = (EditText) findViewById(R.id.editxtDistance);
		return ediText.getText().toString();
	}

}
