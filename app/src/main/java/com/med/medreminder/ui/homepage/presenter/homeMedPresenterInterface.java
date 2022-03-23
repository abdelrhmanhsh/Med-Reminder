package com.med.medreminder.ui.homepage.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.med.medreminder.firebase.firebaseHomeMedsDelegate;
import com.med.medreminder.model.Medicine;

public interface homeMedPresenterInterface {
    void showMedsOnDate(LifecycleOwner owner,Long date, String email);
    void showAllStoredMedicines(LifecycleOwner owner);
    void updateMed(Medicine medicine);
    void updateMedFirestore(Medicine medicine, String email, long id);

    void getMedicinesOnDateFromFirebase(String email, long time);

}
