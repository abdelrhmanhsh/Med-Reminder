package com.med.medreminder.ui.addmedicine.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.med.medreminder.model.Medicine;

public interface AddMedPresenterInterface {

    void addMed(Medicine medicine);
    void addMedToFirestore(Medicine medicine, String email);


}
