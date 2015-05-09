package com.example.user.OrderList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import com.example.user.database.DataProvider;
import com.example.user.pushnotificationexperiment.R;

/**
 * @author bi ran
 *
 */
@SuppressLint("NewApi")
public class OrderListActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>,View.OnClickListener {

    private SimpleCursorAdapter adapter;
    private final int Adapter_AccountName = 1;
    private ListView listView;
    private TextView searchText;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        addClickAction((View)this.getListView());

        adapter = new SimpleCursorAdapter(this,
                R.layout.activity_order_list,
                null,
                new String[]{DataProvider.COL_MEALID, DataProvider.COL_TABLEID,DataProvider.COL_ID},
                new int[]{R.id.text1, R.id.text2,R.id.addButton},
                0);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        //final ListView listView = getListView();
        listView = getListView();

        getLoaderManager().initLoader(0, null, this);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchPeople(final String name) {
//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                String msg = "";
//                try {
//                	ServerUtilities.searchPeople(name);
//                    //Log.i("Send Message: ", "TEST:////////////////"+txt);
//
//                } catch (IOException ex) {
//                    msg = "Message could not be sent";
//                }
//                return msg;
//            }
//
//            @Override
//            protected void onPostExecute(String msg) {
//            	if (!TextUtils.isEmpty(msg)) {
//            		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//            	}
//            }
//        }.execute(null, null, null);
    }
    private void searchAction() {
        String userName = searchText.getText().toString();
        searchPeople(userName);
        //delete the previous history for the database table SEARCHRESULT
        int count = getContentResolver().delete(DataProvider.CONTENT_URI_MESSAGES,null,null);
        Toast.makeText(getApplicationContext(), "Delete total: " + count, Toast.LENGTH_LONG).show();
        searchText.clearFocus();
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                final String username = cursor.getString(2);
                final String email = cursor.getString(1);
                final byte[] image = cursor.getBlob(3);
                switch(view.getId()) {
                    // here can add one more line in the main page for each account
                    case R.id.text2:
                        int count = cursor.getInt(columnIndex);
                        if (count > 0) {
                            ((TextView)view).setText(String.format("Add this people"));
                        }
                        return true;
                    case R.id.addButton:
                        final int col_id = cursor.getInt(columnIndex);
                        ((Button)view).setClickable(true);
                        ((Button)view).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getBaseContext(),"Username: "+username+"Col_id: "+col_id,Toast.LENGTH_LONG).show();
//					        SearchAddUserAction dialog = new SearchAddUserAction();
//			            	dialog.setSelectedAccountInfo(username,col_id,image,email,getBaseContext());
//			    			dialog.show(getFragmentManager(), "SearchAddUserAction");
                            }
                        });
                        return true;
                }
                return false;
            }
        });
        listView.setAdapter(adapter);
        listView.requestFocus();
        listView.setItemsCanFocus(true);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getApplicationContext(), "Enter the listener function", Toast.LENGTH_LONG).show();
//		Intent intent = new Intent(this, ChatActivity.class);
//		intent.putExtra(Common.PROFILE_ID, String.valueOf(id));
//		startActivity(intent);
    }

    //----------------------------------------------------------------------------
    //implement abstract functions for LoaderManager.LoaderCallbacks<Cursor>
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
                DataProvider.CONTENT_URI_MESSAGES,
                new String[]{DataProvider.COL_ID, DataProvider.COL_TABLEID,DataProvider.COL_MEALID},
                null,
                null,//new String[]{DataProvider.PROFILE_COL_LASTMSGAT},
                DataProvider.COL_ID + " DESC");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public void addClickAction(View view) {

    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}
