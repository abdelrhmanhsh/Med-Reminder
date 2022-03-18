package com.med.medreminder.ui.addmedicine.view;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.med.medreminder.model.Medicine;

public interface AddMedViewInterface {

    void addMed(Medicine medicine);
    void addMedToFirestore(Medicine medicine, String email, long id);

}
