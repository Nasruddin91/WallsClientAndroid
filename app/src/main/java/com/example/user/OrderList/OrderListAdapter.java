package com.example.user.OrderList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.pushnotificationexperiment.R;
import com.parse.ParseObject;

public class OrderListAdapter extends ArrayAdapter<ParseObject> {
    public OrderListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout;

        layout = LayoutInflater.from(getContext()).inflate(R.layout.order_item, null);

        ParseObject order = getItem(position);

        TextView mealName = (TextView) layout.findViewById(R.id.mealName);
        TextView tableName = (TextView) layout.findViewById(R.id.tableName);

        mealName.setText(order.getParseObject("mealId").get("name").toString());
        tableName.setText(order.get("tableName").toString());

        return layout;
    }
}
