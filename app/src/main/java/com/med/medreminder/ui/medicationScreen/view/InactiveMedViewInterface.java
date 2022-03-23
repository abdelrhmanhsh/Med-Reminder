package com.med.medreminder.ui.medicationScreen.view;

import com.med.medreminder.model.Medicine;

import java.util.List;

public interface InactiveMedViewInterface {
    void getInactiveMeds(List<Medicine> medicines);
    void successToFetchInactiveMeds(List<Medicine> meds);
    void failedToFetchInactiveMeds(String msg);
    void updateInactiveMed(Medicine medicine);

}
