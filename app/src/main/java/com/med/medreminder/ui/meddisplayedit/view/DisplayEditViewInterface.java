package com.med.medreminder.ui.meddisplayedit.view;

import androidx.lifecycle.LiveData;

import com.med.medreminder.model.Medicine;

public interface DisplayEditViewInterface {

    LiveData<Medicine> getMedDetails(int id);
    void updateMed(Medicine medicine);
    void deleteMed(Medicine medicine);

}
