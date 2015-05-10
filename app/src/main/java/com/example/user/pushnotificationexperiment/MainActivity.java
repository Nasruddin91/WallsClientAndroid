package com.example.user.pushnotificationexperiment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.MealList.MealListActivity;
import com.example.user.OrderList.OrderListActivity;
import com.example.user.updating_service.UpdateService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.android.gms.common.GooglePlayServicesUtil.getErrorDialog;
import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;
import static com.google.android.gms.common.GooglePlayServicesUtil.isUserRecoverableError;


public class MainActivity extends Activity implements View.OnClickListener {
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    String SENDER_ID = "Your-Sender-ID";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCMDemo";

    TextView mDisplay;
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    Context context;

    String regid;

    private String sellerid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getActionBar().hide();
        Button login_submit_button = (Button) this.findViewById(R.id.submit_button);
        login_submit_button.setOnClickListener(this);






//        ParseAnalytics.trackAppOpenedInBackground(getIntent());

//        // We get all order where havent been served, include meal information in the response
//        ParseQuery<ParseObject> queryAllOrders = ParseQuery.getQuery("Order");
//        queryAllOrders.whereEqualTo("isServed", false);
//        queryAllOrders.include("mealId");
//        queryAllOrders.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                if (e != null) {
//                    // Handle exception
//                    Log.e("Order", e.getMessage());
//                    return;
//                }
//
//                Log.d("Parse", "Retrieved " + list.size() + " orders");
//                for (int i = 0; i < list.size(); i++) {
//                    Log.d("Parse", "Seller " + i + ": " + list.get(i).getParseObject("mealId").get("name"));
//                }
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_list:

                Intent intent_order_list = new Intent(this, OrderListActivity.class);
                startActivity(intent_order_list);
                Toast.makeText(getBaseContext(), "Order List Button", Toast.LENGTH_LONG).show();
                break;
            case R.id.meal_list:
                Intent intent_meal_list = new Intent(this, MealListActivity.class);
                startActivity(intent_meal_list);
                Toast.makeText(getBaseContext(), "Meal List Button", Toast.LENGTH_LONG).show();
                break;
            case R.id.submit_button:
                EditText login_edittext_sellerid = (EditText) this.findViewById(R.id.edittext_sellerid);
                sellerid = login_edittext_sellerid.getText().toString();
                UpdateService.SELLER_ID = sellerid;
                Toast.makeText(getBaseContext(), "Submit seller id " + sellerid, Toast.LENGTH_LONG).show();

                setContentView(R.layout.activity_main);

                Button order_list_button = (Button) this.findViewById(R.id.order_list);
                order_list_button.setOnClickListener(this);

                Button meal_list_button = (Button) this.findViewById(R.id.meal_list);
                meal_list_button.setOnClickListener(this);

                break;

        }
    }
}
