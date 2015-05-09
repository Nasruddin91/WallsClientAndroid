package com.example.user.OrderList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.user.pushnotificationexperiment.R;
import com.example.user.updating_service.UpdateService;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class OrderListActivity extends Activity{

    private ListView listView;
    private OrderListAdapter orderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        listView = (ListView) findViewById(R.id.list);
        orderListAdapter = new OrderListAdapter(this);
        listView.setAdapter(orderListAdapter);
        Intent intent = new Intent(this, UpdateService.class);
        startService(intent);
//        // We get all order where havent been served, include meal information in the response
        ParseQuery<ParseObject> queryAllOrders = ParseQuery.getQuery("Order");
        queryAllOrders.whereEqualTo("isServed", false);
        queryAllOrders.include("mealId");
        queryAllOrders.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    // Handle exception
                    Log.e("Order", e.getMessage());
                    return;
                }

                Log.d("Parse", "Retrieved " + list.size() + " orders");
                for (int i = 0; i < list.size(); i++) {
                    Log.d("Parse", "Seller " + i + ": " + list.get(i).getParseObject("mealId").get("name"));
                }

                orderListAdapter.clear();
                orderListAdapter.addAll(list);
                orderListAdapter.notifyDataSetChanged();
            }
        });
    }

    public boolean test(){
        return true;
    }
}
