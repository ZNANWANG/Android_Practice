package com.example.prac3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button button = findViewById(R.id.button_second);
        final Intent intent = getIntent();
        String extraData = intent.getStringExtra("extra_data");
        Toast.makeText(this, extraData, Toast.LENGTH_SHORT).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("return_data", "Hello, Main Activity!");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
