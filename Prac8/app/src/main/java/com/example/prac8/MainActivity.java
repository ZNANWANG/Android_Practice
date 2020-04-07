package com.example.prac8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {
    private EditText accountEditText;
    private EditText passwordEditText;
    private CheckBox rememberCheckBox;
    private Button loginButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountEditText = findViewById(R.id.editText);
        passwordEditText = findViewById(R.id.editText1);
        rememberCheckBox = findViewById(R.id.checkBox);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        boolean doRemember = sharedPreferences.getBoolean("remember", false);
        if(doRemember){
            String account = sharedPreferences.getString("account", "");
            String password = sharedPreferences.getString("password", "");
            accountEditText.setText(account);
            passwordEditText.setText(password);
            rememberCheckBox.setChecked(true);
        }
        loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(rememberCheckBox.isChecked()){
                    editor.putString("account", account);
                    editor.putString("password", password);
                    editor.putBoolean("remember", true);
                } else {
                    editor.clear();
                }
                editor.apply();
                if(account.equals("admin") && password.equals("123456")){
                    Toast.makeText(MainActivity.this, "Login succeed", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
