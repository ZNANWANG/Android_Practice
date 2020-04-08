package com.example.prac11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 3);
        Button createDatabase = (Button) findViewById(R.id.button);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        Button addData = (Button) findViewById(R.id.button2);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                // 开始组装第一条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values); // 插入第一条数据
                values.clear();
                // 开始组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values); // 插入第二条数据
                values.clear();
                // 开始组装第三条数据
                values.put("name", "A Storm of Swords");
                values.put("author", "Dan Brown");
                values.put("pages", 1020);
                values.put("price", 20.95);
                db.insert("Book", null, values); // 插入第三条数据
                Toast.makeText(MainActivity.this, "Add data succeeded", Toast.LENGTH_SHORT).show();
            }
        });
        Button updateData = (Button) findViewById(R.id.button3);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                db.update("Book", values, "name = ?", new String[] { "The Da Vinci Code" });
                Toast.makeText(MainActivity.this, "Update data succeeded", Toast.LENGTH_SHORT).show();
            }
        });
        Button deleteButton = (Button) findViewById(R.id.button4);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[] { "500" });
                Toast.makeText(MainActivity.this, "Delete data succeeded", Toast.LENGTH_SHORT).show();
            }
        });
        Button queryButton = (Button) findViewById(R.id.button5);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 查询Book表中所有的数据
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        // 遍历Cursor对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "book name is " + name);
                        Log.d("MainActivity", "book author is " + author);
                        Log.d("MainActivity", "book pages is " + pages);
                        Log.d("MainActivity", "book price is " + price);
                        //display(name, author, price, pages);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }

    public void display(String name, String author, double price, int pages){
        LayoutInflater layoutInflater = LayoutInflater.from(this);//获得layoutInflater对象
        final View view = layoutInflater.from(this).inflate(R.layout.display_data, null);//获得view对象
        TextView nameTextView = view.findViewById(R.id.textView);
        nameTextView.setText(name);
        TextView authorTextView = view.findViewById(R.id.textView2);
        authorTextView.setText(author);
        TextView priceTextView = view.findViewById(R.id.textView3);
        priceTextView.setText(String.valueOf(price));
        TextView pagesTextView = view.findViewById(R.id.textView4);
        pagesTextView.setText(String.valueOf(pages));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setView(view);
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
