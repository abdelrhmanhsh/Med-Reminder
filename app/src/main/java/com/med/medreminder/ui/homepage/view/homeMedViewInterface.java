package com.med.medreminder.ui.homepage.view;

import com.med.medreminder.model.Medicine;

import java.util.List;

public interface homeMedViewInterface {

    void getAllStoredMedicines(List<Medicine> medicines);
    void updateMed(Medicine medicine);

}
