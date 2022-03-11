package com.med.medreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.med.medreminder.ui.dependency.view.DependentActivity;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, DependentActivity.class);
        startActivity(intent);

    }
}