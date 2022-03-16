package com.med.medreminder.ui.homepage.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.med.medreminder.model.Medicine;

public interface homeMedPresenterInterface {

    void showAllStoredMedicines(LifecycleOwner owner);
    void updateMed(Medicine medicine);

}
