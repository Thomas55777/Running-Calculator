package thomasWilliams.RunningCalculator;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

public class SrnHelp extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.srnhelp);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.mylisthistory, myString));
	}

	static final String[] myString = new String[] {
			// Can use the following for quotes: \" \" Or use \"
			"There are three key measures for rowing: Distance, Time, and Average Split.  If you have two you can calculate the third.",
			"For example if you wanted to calculate your Average Split in order to row a 2k in 6:30\n\nFirst go to distance and enter \"2000\" for meters.  Then go to Time and enter \"06:30\" Please note after you enter the first two numbers the \":\" symbol will automatically generate.  So to properly enter your time you must enter 06 and not just 6.  The correct format for time is mm:ss[.0] and the last digit [.0] is in brackets because it is optional.  The Time 06:30 will calculate the same as 06:30.0.",
			"Once you have entered your Distance and Time press the \"AvgSplit\" button and the app will calculate your Average Split.",
			"Each time you press one of the calculate buttons a record of it is stored in your history.  This is a great feature as it allows you to keep track of weeks worth of workouts and training plans.  The History will record the Date/Time, Distance, Time, and AvgSplit.\n\nTo access your history go to the Calculate Tab, press the menu button, and select History.",
			"Once you have calculated one of these measures in the Calculate Tab you can now use the other tabs to calculate your weight adjusted scores and your Predictions using Paul's Law.",
			"",
			"Weight adjustment uses the Concept 2 formula:\n\n" +
					"Wf = [body weight in lbs / 270] raised to the power .222\n\n" +
					"Corrected time = Wf x actual time (seconds)\n\n" +
					"Corrected distance = actual distance / Wf",
			"",
			"Paul's Law says that for a rowing athlete with balanced speed and endurance capabilities, for every doubling of distance the 500m split should increase by five seconds."
	};
}
