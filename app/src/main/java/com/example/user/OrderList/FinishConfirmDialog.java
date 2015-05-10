package com.example.user.OrderList;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/5/9.
 */
public class FinishConfirmDialog extends DialogFragment {
    private String mealname;
    private String tableid;
    private Context ctx;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] items = new String[]{"Add","Cancel"};
        final AlertDialog alert = new AlertDialog.Builder(ctx).setTitle("Confrim to finish "+mealname+" for table "+tableid)
                .setMessage("Confrim to finish "+mealname+" for table "+tableid).setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(ctx, "Confirm delete this", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        }).show();
        return alert;
    }
    public boolean setSelectedAccountInfo(String mealname, String tableid, Context c){
        this.mealname = mealname;
        this.tableid = tableid;
        this.ctx = c;
        Log.i("set selection","Set values");
        //Toast.makeText(c, "Account"+this.userName+"/"+this.userId, Toast.LENGTH_SHORT).show();
        return false;
    }
}
