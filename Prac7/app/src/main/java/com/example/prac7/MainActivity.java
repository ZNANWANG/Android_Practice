package com.example.prac7;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button saveButton;
    private Button restoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveButton = findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putString("name", "Tom");
                editor.putInt("age", 28);
                editor.putBoolean("married", false);
                editor.apply();
            }
        });

        restoreButton = findViewById(R.id.button2);
        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
//                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                String name = sharedPreferences.getString("name", null);
                int age = sharedPreferences.getInt("age", 0);
                boolean isMarried = sharedPreferences.getBoolean("married", false);
                Log.d(TAG, "name: " + name);
                Log.d(TAG, "age: " + age);
                Log.d(TAG, "married: " + isMarried);
            }
        });
    }
}
