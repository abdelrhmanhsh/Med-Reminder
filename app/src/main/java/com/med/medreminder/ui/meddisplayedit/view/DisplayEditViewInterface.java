package com.med.medreminder.ui.meddisplayedit.view;

import androidx.lifecycle.LiveData;

import com.med.medreminder.model.Medicine;

public interface DisplayEditViewInterface {

    LiveData<Medicine> getMedDetails(long id);
    void updateMed(Medicine medicine);
    void deleteMed(Medicine medicine);
    void updateMedFirestore(Medicine medicine, String email, long id);
    void deleteMedFirestore(String email, long id);

}
