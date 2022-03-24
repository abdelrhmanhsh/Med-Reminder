package com.med.medreminder.ui.meddisplayedit.presenter;

import androidx.lifecycle.LiveData;

import com.med.medreminder.firebase.FirebaseGetMedDelegate;
import com.med.medreminder.model.Medicine;

public interface DisplayPresenterInterface {

    LiveData<Medicine> getMedDetails(long id);
    void updateMed(Medicine medicine);
    void getMedByIdFirestore(String email, long id);
    void deleteMed(Medicine medicine);
    void updateMedFirestore(Medicine medicine, String email, long id);
    void deleteMedFirestore(String email, long id);

}
