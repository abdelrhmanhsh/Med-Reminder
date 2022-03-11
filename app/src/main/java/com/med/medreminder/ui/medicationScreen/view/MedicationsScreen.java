package com.med.medreminder.ui.medicationScreen.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;

import com.med.medreminder.R;
import com.med.medreminder.model.Medicine;

public class MedicationsScreen extends AppCompatActivity {

    public static final String CHANNEL_ID = "notify";
    public static final int NOTIFICATION_ID = 1;
    public static Medicine[] active_meds = {new Medicine(1,"Panadol","Pills","200","headache","yes","3","10:00", R.drawable.ic_medication,20,"am","20","gm")};
    public static Medicine[] inactive_meds = {new Medicine(1,"Panadol+","Pills","200","headache","yes","3","10:00",R.drawable.ic_medication,20,"am","20","gm"),new Medicine(1,"Paracetamol","Pills","200","headache","yes","3","10:00",R.drawable.ic_medication,20,"am","20","gm")};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications_screen);
    }
}