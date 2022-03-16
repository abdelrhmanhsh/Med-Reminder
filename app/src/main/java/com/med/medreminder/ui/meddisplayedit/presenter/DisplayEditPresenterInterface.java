package com.med.medreminder.ui.meddisplayedit.presenter;

import androidx.lifecycle.LiveData;

import com.med.medreminder.model.Medicine;

public interface DisplayEditPresenterInterface {

    LiveData<Medicine> getMedDetails(int id);
    void updateMed(Medicine medicine);
    void deleteMed(Medicine medicine);

}
