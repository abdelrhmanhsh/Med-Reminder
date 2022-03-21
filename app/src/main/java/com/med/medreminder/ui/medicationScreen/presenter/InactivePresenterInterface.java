package com.med.medreminder.ui.medicationScreen.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.med.medreminder.firebase.FirebaseInactiveMedDelegate;
import com.med.medreminder.model.Medicine;

public interface InactivePresenterInterface {
    void showInactiveStoredMedicines(LifecycleOwner owner, String email);
    void getInactiveMedsFromFirebase(String email,long time);
    void updateMed(Medicine medicine);
}
