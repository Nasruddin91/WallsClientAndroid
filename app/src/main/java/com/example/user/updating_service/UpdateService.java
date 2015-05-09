package com.example.user.updating_service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by User on 9/5/2015.
 */

public class UpdateService extends IntentService {

    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public UpdateService() {
        super("UpdateServcice");
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        long endTime = System.currentTimeMillis() + 5 * 1000;
        while (true) {
            synchronized (this) {
                try {
                    ParseQuery<ParseObject> queryAllOrders = ParseQuery.getQuery("Order");
                    queryAllOrders.whereEqualTo("isServed", false);
                    queryAllOrders.include("mealId");
                    List<ParseObject> list = queryAllOrders.find();

                    Log.d("Parse", "Retrieved " + list.size() + " orders");
                    for (int i = 0; i < list.size(); i++) {
                        Log.d("Parse", "Seller " + i + ": " + list.get(i).getParseObject("mealId").get("name"));
                    }

                }catch (Exception e){
                    Log.d("Parse", "canot get parse object");
                }
            }
        }
    }
}

