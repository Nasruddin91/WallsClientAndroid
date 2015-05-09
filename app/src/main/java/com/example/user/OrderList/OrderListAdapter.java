package com.example.user.OrderList;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.pushnotificationexperiment.R;
import com.parse.ParseObject;

public class OrderListAdapter extends ArrayAdapter<ParseObject> {

    private final Context ctx;

    public OrderListAdapter(Context context) {
        super(context, 0);
        this.ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout;

        layout = LayoutInflater.from(getContext()).inflate(R.layout.order_list_item, null);

        ParseObject order = getItem(position);

        TextView mealName = (TextView) layout.findViewById(R.id.mealname);
        TextView tableName = (TextView) layout.findViewById(R.id.tableid);
        Button finishbutton = (Button) layout.findViewById(R.id.finishButton);
        String mealname = order.getParseObject("mealId").get("name").toString();
        String tableid = order.get("tableName").toString();
        final int index = position; // number of the rows
        finishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Finish the Meal/Position: "+  index, Toast.LENGTH_LONG).show();
            }
        });
        mealName.setText(mealname);
        tableName.setText("Table: " + tableid );

        return layout;
    }
}
