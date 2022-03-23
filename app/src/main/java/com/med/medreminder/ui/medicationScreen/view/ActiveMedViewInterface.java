package com.med.medreminder.ui.medicationScreen.view;

import com.med.medreminder.model.Medicine;

import java.util.List;

public interface ActiveMedViewInterface {
    void getActiveMeds(List<Medicine> medicines);
    void successToFetchMeds(List<Medicine> meds);
    void failedToFetchMeds(String msg);
    void updateMed(Medicine medicine);

}
