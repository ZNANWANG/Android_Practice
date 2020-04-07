package com.example.prac6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//        startActivity(intent);
        editText = findViewById(R.id.editText);
        String text = retrive();
        if(!TextUtils.isEmpty(text)){
            editText.setText(text);
            editText.setSelection(text.length());
            Toast.makeText(this, "load succeed", Toast.LENGTH_LONG).show();
        }
    }

    public String retrive(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuffer text = new StringBuffer();
        try{
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                text.append(line);
                Log.d("mark", "infinite");
            }
        }catch(EOFException e){
            try {
                reader.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(in != null){
                    in.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return text.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputString = String.valueOf(editText.getText());
        save(inputString);
    }

    public void save(String inputString){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputString);
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
