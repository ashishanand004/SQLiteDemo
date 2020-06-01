package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    private EditText name;
    private EditText contact;
    private EditText address;
    private int studentId;
    Button saveButton;
    Button updateButton;
    Button deleteButton;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dbHelper = new DBHelper(getApplicationContext());
        name = findViewById(R.id.name_editText);
        contact = findViewById(R.id.contact_editText);
        address = findViewById(R.id.address_editText);
        saveButton = findViewById(R.id.save_details);
        updateButton = findViewById(R.id.update_details);
        deleteButton = findViewById(R.id.delete_details);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode",0);
        if (mode == 1) {
            saveButton.setVisibility(View.VISIBLE);
        } else {
            saveButton.setVisibility(View.INVISIBLE);
            updateButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }
        studentId = intent.getIntExtra("id",0);
        if (studentId != 0) {
            Log.d("test", "cursor ID : " + studentId);
            Cursor cursor = dbHelper.getData(studentId);
            Log.d("test", "cursor : " + cursor.toString());
            Log.d("test", "cursor : " + cursor.getString(2));
            name.setText(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Name)));
            contact.setText(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Contact)));
            address.setText(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Address)));
        }
    }

    public void saveData(View view) {
        long value = dbHelper.insertStudentData(name.getText().toString(), contact.getText().toString(), address.getText().toString());
        if (value != -1) {
            Toast.makeText(getApplicationContext(), "data inserted successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }

    public void updateData(View view) {
        int value = dbHelper.updateStudentData(studentId, name.getText().toString(), contact.getText().toString(), address.getText().toString());
        if (value != 0) {
            Toast.makeText(getApplicationContext(), "data updated successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    public void deleteData(View view) {
        int value = dbHelper.deleteStudentData(studentId);
        if (value != 0) {
            Toast.makeText(getApplicationContext(), "data updated successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}
