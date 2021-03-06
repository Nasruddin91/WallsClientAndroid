package com.example.user.OrderList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.pushnotificationexperiment.R;
import com.example.user.updating_service.UpdateService;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class OrderListAdapter extends ArrayAdapter<ParseObject> {

    private final Activity activity;
    private FragmentManager fm = null;

    public OrderListAdapter(Activity ac) {
        super(ac, 0);
        this.activity = ac;
        this.fm = ac.getFragmentManager();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout;

        layout = LayoutInflater.from(getContext()).inflate(R.layout.order_list_item, null);

        final ParseObject order = getItem(position);

        TextView mealName = (TextView) layout.findViewById(R.id.mealname);
        TextView tableName = (TextView) layout.findViewById(R.id.tableid);
        Button finishbutton = (Button) layout.findViewById(R.id.finishButton);
        final String mealid = order.getParseObject("mealId").getObjectId();
        final String orderid = order.getObjectId();
        final String mealname = order.getParseObject("mealId").get("name").toString();
        final String tableid = order.get("tableName").toString();
        final int index = position; // number of the rows
        finishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Finish the Meal/Position: "+  index + "OrderID: " + orderid +"Meal ID:" + mealid, Toast.LENGTH_LONG).show();

//                //dialog does not work. the app suddenly close
//                FinishConfirmDialog dialog = new FinishConfirmDialog();
//                dialog.setSelectedAccountInfo(mealname, tableid, activity.getBaseContext());
//                if(fm != null)
//                    dialog.show(fm, "Finish One Order");

                order.put("isServed", true);
                order.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        remove(order);
                        notifyDataSetChanged();
                        //Log.i("e print state",e.getMessage());
                    }
                });
                //
            }
        });


        mealName.setText(mealname);
        tableName.setText("Table: " + tableid );

        return layout;
    }
}
