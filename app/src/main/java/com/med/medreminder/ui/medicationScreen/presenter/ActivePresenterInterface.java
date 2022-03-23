package com.med.medreminder.ui.medicationScreen.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.med.medreminder.firebase.FirebaseActiveMedDelegate;
import com.med.medreminder.firebase.firebaseHomeMedsDelegate;
import com.med.medreminder.model.Medicine;

public interface ActivePresenterInterface {
    void showActiveStoredMedicines(LifecycleOwner owner, String email);
    void getActiveMedsFromFirebase(String email, long time);
    void updateMed(Medicine medicine);


}
