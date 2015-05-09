package com.example.user.updating_service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by User on 9/5/2015.
 */

public class UpdateService extends IntentService {

    public static final String MESSENGER_KEY = "MESSENGER";

    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public UpdateService() {
        super("UpdateService");
    }

    public static Intent makeIntent(Context context, Handler handler) {
        Messenger messenger = new Messenger(handler);

        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(MESSENGER_KEY, messenger);

        return intent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("UpdateService", "on start command");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("UpdateService", "on handle intent");
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

                sendPath(list, (Messenger) intent.getParcelableExtra(MESSENGER_KEY));

            }catch (Exception e){
                e.printStackTrace();
                Log.e("Parse", e.getMessage());
            }
        }
    }

    public static String OBJECTID_KEY = "ObjectId Key";
    public static String TABLENAME_KEY = "TableName Key";
    public static String ISSERVED_KEY = "IsServed Key";
    public static String MEALNAME_KEY = "MealName Key";
    public static String DATA_KEY = "Data Key";
    public static String DATA_SIZE = "Data Size";

    public static void sendPath (List<ParseObject> orderList, Messenger messenger) {
        Message msg = Message.obtain();

        Bundle allData = new Bundle();

        for (int i = 0; i < orderList.size(); i++) {
            ParseObject order = orderList.get(i);

            Bundle data = new Bundle();
            data.putString(OBJECTID_KEY, order.getObjectId());
            data.putString(TABLENAME_KEY, order.getString("tableName"));
            data.putBoolean(ISSERVED_KEY, order.getBoolean("isServed"));
            data.putString(MEALNAME_KEY, order.getParseObject("mealId").getString("name"));

            allData.putBundle(DATA_KEY + i, data);
        }
        allData.putInt(DATA_SIZE, orderList.size());

        // Make the Bundle the "data" of the Message.
        msg.setData(allData);

        try {
            // Send the Message back to the client Activity.
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

