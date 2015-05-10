package com.example.user.MealList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.user.pushnotificationexperiment.R;
import com.example.user.updating_service.UpdateService;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by User on 9/5/2015.
 */
public class MealListAdapter extends ArrayAdapter<ParseObject> {

    private final Context ctx;

    public MealListAdapter(Context context) {
        super(context, 0);
        this.ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout;

        layout = LayoutInflater.from(getContext()).inflate(R.layout.meal_list_item, null);

        final ParseObject meal = getItem(position);

        TextView mealName = (TextView) layout.findViewById(R.id.mealname);
        final Button toggleButton = (Button) layout.findViewById(R.id.stockToggleButton);

        final String mealIsSoldOut = meal.get("isSoldOut").toString();
        if (mealIsSoldOut == "true"){
            toggleButton.setText("Sold Out");
        }else{
            toggleButton.setText("In Stock");
        }
        toggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mealIsSoldOut == "true") {
                    //set meal isSoldOut to false
                    toggleButton.setText("In Stock");
                    setMealIsSoldOut(meal.getObjectId(), false );
                } else {
                    //set meal isSoldOut to true
                    toggleButton.setText("Sold Out");
                    setMealIsSoldOut(meal.getObjectId(), true);
                }
            }
        });

        String mealname = meal.get("name").toString();

        mealName.setText(mealname);

        return layout;
    }

    public void setMealIsSoldOut(String mealId, final boolean isSoldOut){
        final ParseQuery<ParseObject> queryMeals = ParseQuery.getQuery("Meal");
        queryMeals.getInBackground(mealId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                parseObject.put("isSoldOut", isSoldOut);
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        resetView();
                    }
                });
            }
        });
    }

    public void resetView(){
        ParseQuery<ParseObject> queryAllOrders = ParseQuery.getQuery("Meal");
        queryAllOrders.whereEqualTo("sellerId", ParseObject.createWithoutData("Seller", UpdateService.SELLER_ID) );
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

                Log.d("Parse", "Retrieved " + list.size() + " meals");
                for (int i = 0; i < list.size(); i++) {
                    Log.d("Parse", "Meal " + i + ": " + list.get(i).get("name"));
                }

                clear();
                addAll(list);
                notifyDataSetChanged();
            }
        });


    }
}
