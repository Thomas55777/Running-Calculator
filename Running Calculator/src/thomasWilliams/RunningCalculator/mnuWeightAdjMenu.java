package thomasWilliams.RunningCalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class mnuWeightAdjMenu extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.srnweightadjexplain);
		TextView WeightAdjText = (TextView) findViewById(R.id.txtWeightAdjExplain);
		WeightAdjText.setText("What the Erg Score Means: \nThe Time you get from a Concept2 Rowing Machine represents the Time it will take a boat of 8 rowers if all 8 rowers were exactly like you.  This time assumes that you row perfectly with perfect rowing conditions and you weigh 270 lbs.  As it is not possible to meet these assumptions adjustments must be made.  Adjustments for rowing technique are subjective but adjustments for weight can be done objectively.\n\nCoaches Note: Coaches should never use Erg scores alone when determining Boat selection.  It is common that a heavier rower will produce a better erg score than a lighter rower but that does not mean that they are capable of moving the boat faster.  When rowing on the water the more weight in a boat the more drag the boat has to overcome and because the erg does not factor in drag the heavier rower will always have an advantage on the erg.  The weight adjusted score is intended to equalize the weight difference between rowers and provide a fair comparison when looking at potential boat speed.");
		WeightAdjText.setText(WeightAdjText.getText() + "\n\nTo visualize: To help understand the effect of weight on a boat the general guideline can be applied: 10lbs on a boat will add 0.6 - 0.7 seconds across the length of 2000m.  If you take a boat that has an average weight of 150 lbs to a boat with an average weight of 180 lbs there will be a difference of 30 lbs per rower and 240 lbs across the whole boat.  The extra 240 lbs will add roughly 160 seconds to the boat.  This means that each rower on the 180 lbs boat will have to have an erg score 20 seconds faster to maintain the same speed as the lighter weight boat.\n\nHeavy weight rowers do have an inherent advantage in rowing.  This is for two reasons: first weight and drag do not have a linear relationship. The heavier you are the less drag each additional lbs adds.  And second the more power you have and the less drag has a factor on boat speed.  These two advantages can be measured in fractions of a second but when it comes to high level rowing races are often won and lost by fractions of seconds.");

		
	}
}
