package com.med.medreminder.ui.medicationScreen.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.med.medreminder.model.Medicine;

public interface ActivePresenterInterface {
    void showActiveStoredMedicines(LifecycleOwner owner);
    void showActiveMedFirestore(String email);

}
