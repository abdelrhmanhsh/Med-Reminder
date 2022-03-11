package com.med.medreminder.ui.dependency.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.med.medreminder.ui.medicationScreen.view.MedicationsScreen;
import com.med.medreminder.R;

import java.util.Calendar;

public class DependentActivity extends AppCompatActivity {

    EditText birthdate_edt;
    DatePickerDialog.OnDateSetListener setListener;
    TextView done_txt;
    ImageView avatar;
    EditText firstName_edt;
    EditText lastName_edt;
    ImageView exit_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependent);
        birthdate_edt = findViewById(R.id.birthdate_edt);
        done_txt = findViewById(R.id.done_txt);
        avatar = findViewById(R.id.avatar);
        firstName_edt = findViewById(R.id.firstName_edt);
        lastName_edt = findViewById(R.id.lastName_edt);
        exit_img = findViewById(R.id.exit_img);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        birthdate_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DependentActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day + "/" +month + "/" + year;
                birthdate_edt.setText(date);
            }
        };

        exit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DependentActivity.this, MedicationsScreen.class);
                startActivity(intent);
            }
        });

        done_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DependentActivity.this, MedicationsScreen.class);
                startActivity(intent);
            }
        });


    }
}