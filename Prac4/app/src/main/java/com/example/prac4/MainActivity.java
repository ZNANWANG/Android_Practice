package com.example.prac4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.midi.MidiDeviceService;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private MyBroadcastReceiver myBroadcastReceiver;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver, intentFilter);

        //动态注册系统全局广播接收器（用于接收implicit intent)
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("com.example.prac4.MY_BROADCAST");
//        myBroadcastReceiver = new MyBroadcastReceiver();
//        registerReceiver(myBroadcastReceiver , intentFilter);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send gloabal broadcast using explicit intent
                Intent intent = new Intent("com.example.prac4.MY_BROADCAST");
                intent.setComponent(new ComponentName(MainActivity.this.getPackageName(), "com.example.prac4.MyBroadcastReceiver"));
                sendBroadcast(intent);
                intent.setClassName("com.example.prac5", "com.example.prac5.AnotherBroadcastReceiver");
                sendBroadcast(intent);

                //send global broadcast using inplicit intent
//                Intent intent = new Intent("com.example.prac4.MY_BROADCAST");
//                sendOrderedBroadcast(intent, null);

                //send local broadcast using localBroadcastReceiver
//                Intent intent = new Intent("com.example.prac4.LOCAL_BROADCAST");
//                localBroadcastManager.sendBroadcast(intent);

                //静态注册的广播接收器无法接收本地广播
//                Intent intent = new Intent("com.example.prac4.LOCAL_BROADCAST");
//                intent.setComponent(new ComponentName(MainActivity.this.getPackageName(), "com.example.prac4.TestReceiver"));
//                localBroadcastManager.sendBroadcast(intent);
            }
        });

        //动态注册本地广播接收器
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.prac4.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);

    }

    protected void onDestroy(){
        super.onDestroy();
//        unregisterReceiver(myBroadcastReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent){
//            Toast.makeText(context, "network changes", Toast.LENGTH_SHORT).show();
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received in LocalReceiver", Toast.LENGTH_LONG).show();
        }
    }
}
