package com.example.prac5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AnotherBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //Log.d("help", "another");
        Toast.makeText(context, "reveived in AnotherBroadcastReceiver", Toast.LENGTH_LONG).show();
        abortBroadcast();
    }
}
