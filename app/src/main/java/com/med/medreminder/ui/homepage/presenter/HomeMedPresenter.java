package com.med.medreminder.ui.homepage.presenter;

import android.util.Log;

import com.med.medreminder.model.MedStatus;
import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.RepositoryInterface;
import com.med.medreminder.ui.homepage.view.homeMedViewInterface;

import java.util.Calendar;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class HomeMedPresenter implements homeMedPresenterInterface {

    homeMedViewInterface homeMedViewInterface;
    RepositoryInterface repositoryInterface;
    Long date;

    public HomeMedPresenter(homeMedViewInterface homeMedViewInterface, RepositoryInterface repositoryInterface, Long date) {
        this.homeMedViewInterface = homeMedViewInterface;
        this.repositoryInterface = repositoryInterface;
        this.date = date;
    }

    @Override
    public void showMedsOnDate(LifecycleOwner owner, Long date, String email) {
        Log.d("TAG", "showAllStoredMedicines: " + owner);
        Log.d("TAG", "showAllStoredMedicines: " + repositoryInterface);
        Log.d("TAG", "showAllStoredMedicines: " + repositoryInterface);
       repositoryInterface.getActiveMedsOnDateSelected(date, email).observe(owner, new Observer<List<Medicine>>() {
           @Override
           public void onChanged(List<Medicine> medicines) {
               Log.d("TAG", "showAllStoredMedicines: On Change-> " +date );

               homeMedViewInterface.getAllStoredMedicinesOnDate(medicines);
           }
       });
    }

    @Override
    public void showAllStoredMedicines(LifecycleOwner owner) {
        Log.d("TAG", "showAllStoredMedicines: " + owner);
        Log.d("TAG", "showAllStoredMedicines: " + repositoryInterface);
        Log.d("TAG", "showAllStoredMedicines: " + repositoryInterface);
        repositoryInterface.getStoredMedicines().observe(owner, new Observer<List<Medicine>>() {
            @Override
            public void onChanged(List<Medicine> medicines) {
                homeMedViewInterface.getAllStoredMedicines(medicines);
            }
        });
    }

    @Override
    public void updateMed(Medicine medicine) {
        repositoryInterface.updateMedicine(medicine);
    }

//    @Override
//    public void addMedStatus(MedStatus medStatus) {
//        repositoryInterface.insertMedStatus(medStatus);
//    }
//
//
//
//    @Override
//    public void getMedStatus(LifecycleOwner lifecycleOwner, String date, String email) {
////        repositoryInterface.getMedStatus(date, email);
//
//        repositoryInterface.getMedStatus(date, email).observe(lifecycleOwner, new Observer<List<MedStatus>>() {
//            @Override
//            public void onChanged(List<MedStatus> medStatusList) {
//                Log.d("TAG", "showAllStoredMedicines: On Change-> " +date );
//
//                homeMedViewInterface.getMedStatus(medStatusList);
//            }
//        });
//
//    }

}
