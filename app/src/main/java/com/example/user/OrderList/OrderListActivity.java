package com.example.user.OrderList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.example.user.pushnotificationexperiment.R;
import com.example.user.updating_service.UpdateService;
import com.parse.ParseObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OrderListActivity extends Activity{

    private ListView listView;
    private OrderListAdapter orderListAdapter;

    class MessengerHandler extends Handler {

        // A weak reference to the enclosing class
        WeakReference<OrderListActivity> outerClass;

        public MessengerHandler(OrderListActivity outer) {
            outerClass = new WeakReference<OrderListActivity>(outer);
        }

        // Handle any messages that get sent to this Handler
        @Override
        public void handleMessage(Message msg) {

            final OrderListActivity activity = outerClass.get();

            if (activity != null) {
                Bundle allData = msg.getData();
                int dataSize = allData.getInt(UpdateService.DATA_SIZE);

                List<ParseObject> orderList = new ArrayList<>();
                for (int i = 0; i < dataSize; i++) {
                    Bundle data = allData.getBundle(UpdateService.DATA_KEY + i);

                    ParseObject order = ParseObject.create("Order");
                    ParseObject meal = ParseObject.create("Meal");

                    order.put("objectId", data.getString(UpdateService.OBJECTID_KEY));
                    order.put("tableName", data.getString(UpdateService.TABLENAME_KEY));
                    order.put("isServed", data.getBoolean(UpdateService.ISSERVED_KEY));

                    meal.put("name", data.getString(UpdateService.MEALNAME_KEY));
                    meal.setObjectId(data.getString(UpdateService.MEALID_KEY));
                    order.put("mealId", meal);
                    orderList.add(order);
                }

                orderListAdapter.clear();
                orderListAdapter.addAll(orderList);
            }
        }
    }

    MessengerHandler handler = new MessengerHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        Log.d("OrderListActivity", "done");

        listView = (ListView) findViewById(R.id.list);
        orderListAdapter = new OrderListAdapter(this);
        listView.setAdapter(orderListAdapter);

        Timer timer;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = UpdateService.makeIntent(getApplicationContext(), handler);
                startService(intent);
                Log.d("OrderListActivity", "done start service");
            }
        }, 0, 10000);
    }

    public boolean test(){
        return true;
    }
}
