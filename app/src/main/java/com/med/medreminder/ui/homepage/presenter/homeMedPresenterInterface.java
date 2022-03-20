package com.med.medreminder.ui.homepage.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.med.medreminder.model.MedStatus;
import com.med.medreminder.model.Medicine;

public interface homeMedPresenterInterface {
    void showMedsOnDate(LifecycleOwner owner,Long date, String email);
    void showAllStoredMedicines(LifecycleOwner owner);
    void updateMed(Medicine medicine);
//    void addMedStatus(MedStatus medStatus);
//    void getMedStatus(LifecycleOwner lifecycleOwner, String date, String email);;
}
