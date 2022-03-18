package com.med.medreminder.ui.medicationScreen.presenter;

import androidx.lifecycle.LifecycleOwner;

public interface ActivePresenterInterface {
    void showActiveStoredMedicines(LifecycleOwner owner, String email);
}
