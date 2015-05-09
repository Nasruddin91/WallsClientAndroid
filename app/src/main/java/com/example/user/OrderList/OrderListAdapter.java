package com.example.user.OrderList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
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
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout;

        layout = LayoutInflater.from(getContext()).inflate(R.layout.order_list_item, null);

        ParseObject order = getItem(position);

        TextView mealName = (TextView) layout.findViewById(R.id.mealname);
        TextView tableName = (TextView) layout.findViewById(R.id.tableid);
        Button finishbutton = (Button) layout.findViewById(R.id.finishButton);
        final String mealname = order.getParseObject("mealId").get("name").toString();
        final String mealid = order.getParseObject("mealId").getObjectId();
        final String tableid = order.get("tableName").toString();
        final String orderid = order.getObjectId();
        final int index = position; // number of the rows
        finishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Finish the Meal/Position: "+  index + "Meal ID:" + mealid, Toast.LENGTH_LONG).show();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");

                // Retrieve the object by id

                query.getInBackground(orderid, new GetCallback<ParseObject>() {
                    public void done(ParseObject order, ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data. In this case, only cheatMode and score
                            // will get sent to the Parse Cloud. playerName hasn't changed.
                            order.put("isServed", true);
                            order.saveInBackground();

                        }
                    }
                });
                //
            }
        });

//               //dialog does not work. the app suddenly close
//                FinishConfirmDialog dialog = new FinishConfirmDialog();
//                dialog.setSelectedAccountInfo(mealname, tableid, activity.getBaseContext());
//                if(fm != null)
//                    dialog.show(fm, "Finish One Order");
        mealName.setText(mealname);
        tableName.setText("Table: " + tableid );

        return layout;
    }
}
