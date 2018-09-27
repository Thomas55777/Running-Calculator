package thomasWilliams.RunningCalculator;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SrnHistory extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.srnhistory);

		// Add My Code Below

		generateListView();

		ListView list = getListView();
		// list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		list.setTextFilterEnabled(true);
		list.setOnItemClickListener(new OnItemClickListener() {

			/*
			 * You can also support single and multi selection. See the
			 * following snippets for examples. To get the selected item(s) use
			 * listView.getCheckedItemPosition() or
			 * listView.getCheckedItemPositions(). If you have stable ID you
			 * could also use listView.getCheckedItemIds() to get the selected
			 * ids.
			 * 
			 * listView.getCheckedItemPosition()
			 * listView.getCheckedItemPositions() listView.getCheckedItemIds()
			 */

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(), ((TextView) arg1).getText(), Toast.LENGTH_SHORT).show();
			}
		});
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// Toast.makeText(SrnHistory.this, "Item in position " + position + " clicked", Toast.LENGTH_SHORT).show();
				// Return true to consume the click event. In this case the
				// onListItemClick listener is not called anymore.
				return true;
			}
		});
	}

	private void generateListView() {
		StoreDataInSQLite RetrieveData = new StoreDataInSQLite(this);
		int columnIndex0 = 0; // KEY_ROWID
		int columnIndex1 = 1; // KEY_DISTANCE
		int columnIndex2 = 2; // KEY_TIME
		int columnIndex3 = 3; // KEY_PACE
		int columnIndex4 = 4; // KEY_DATE
		RetrieveData.open();
		// Cursor cursor = RetrieveData.getAllMyFloats();
		Cursor cursor = RetrieveData.fetchAllData();
		String[] myString = new String[cursor.getCount()];
		// I can replace all the Strings with Float to get a Float instead
		if (cursor.moveToFirst())
		{
			for (int i = 0; i < cursor.getCount(); i++)
			{
				myString[i] = "Date/Time: " + cursor.getString(columnIndex4) + "\n\nDistance:  " + cursor.getString(columnIndex1)
						+ " meters\nTime:      " + cursor.getString(columnIndex2) + " hh:mm:ss.0\nPace:      " + cursor.getString(columnIndex3) + " hh:mm:ss.0";
				cursor.moveToNext();
			}
		}
		cursor.close();
		RetrieveData.close();

		// Do what you want with myFloats[].

		// setListAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_multiple_choice, myString));
		setListAdapter(new ArrayAdapter<String>(this, R.layout.mylisthistory, myString));
	}

	static final String[] aryHistory = new String[] {
			"123", "456"
	};

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater mnuInflater = getMenuInflater();
		mnuInflater.inflate(R.menu.history_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuHistoryClearAll:

			StoreDataInSQLite ClearData = new StoreDataInSQLite(this);
			ClearData.open();
			ClearData.ClearAll();
			ClearData.close();
			generateListView();

			return true;
		case R.id.menuMainHelp:
			// Set Up Later
			return true;
		}
		return false;
	}
}
