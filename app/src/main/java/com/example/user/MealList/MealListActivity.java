package com.example.user.MealList;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.user.OrderList.OrderListAdapter;
import com.example.user.pushnotificationexperiment.R;
import com.example.user.updating_service.UpdateService;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MealListActivity extends Activity {

    private ListView listView;
    private MealListAdapter mealListAdapter;
    private String seller_id = UpdateService.SELLER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        listView = (ListView) findViewById(R.id.list);
        mealListAdapter = new MealListAdapter(this);
        listView.setAdapter(mealListAdapter);
        Intent intent = new Intent(this, UpdateService.class);
        startService(intent);

        ParseQuery<ParseObject> queryAllOrders = ParseQuery.getQuery("Meal");
        queryAllOrders.whereEqualTo("sellerId", ParseObject.createWithoutData("Seller", seller_id) );
        queryAllOrders.include("mealId");
        queryAllOrders.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    // Handle exception
                    Log.e("Meal", e.getMessage());
                    return;
                }else{
                    Log.d("Meal", "e is null");
                }

                Log.d("Parse", "Retrieved " + list.size() + " orders");
                for (int i = 0; i < list.size(); i++) {
                    Log.d("Parse", "Meal " + i + ": " + list.get(i).get("name"));
                }

                mealListAdapter.clear();
                mealListAdapter.addAll(list);
                mealListAdapter.notifyDataSetChanged();
            }
        });

    }
}
