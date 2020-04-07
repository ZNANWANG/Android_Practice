package com.example.prac2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore;
import android.media.midi.MidiDeviceService;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        init();
    }

    public void init(){
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You click Button", Toast.LENGTH_SHORT).show();
                //explicit inetnt
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(intent);

                //explicit intent
//                Intent intent=new Intent();
//                intent.setComponent(new ComponentName(MainActivity.this.getPackageName(),"com.example.prac2.SecondActivity"));
//                startActivity(intent);

                //explicit intent
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, "com.example.prac2.SecondActivity");
                startActivity(intent);

                //implicit intent(指定action和category)
//                Intent intent = new Intent("com.example.prac2.ACTION_START");
//                intent.addCategory("com.example.prac2.MY_CATEGORY");
//                startActivity(intent);

                //用intent打开其他程序（网页）
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.baidu.com"));
//                startActivity(intent);

                //用intent拨电话
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:10086"));
//                startActivity(intent);

                //活动之间传递信息
//                String data = "Hello second activity!";
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                intent.putExtra("extra_data", data);
//                startActivity(intent);

                //活动结束后传回信息
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                intent.putExtra("extra_data", "Hello second activity!");
//                startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String returnedData = data.getStringExtra("data_return");
                    Toast.makeText(MainActivity.this, returnedData, Toast.LENGTH_SHORT).show();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
