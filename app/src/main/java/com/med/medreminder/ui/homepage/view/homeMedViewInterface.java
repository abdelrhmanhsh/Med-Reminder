package com.med.medreminder.ui.homepage.view;

import com.med.medreminder.model.Medicine;

import java.util.List;

public interface homeMedViewInterface {
    void getAllStoredMedicinesOnDate(List<Medicine> medicines);
    void getAllStoredMedicines(List<Medicine> medicines);
    void updateMed(Medicine medicine);
    void updateMedFirestore(Medicine medicine, String email, long id);

    void failedToFetchMeds(String msg);
    void successToFetchMeds(List<Medicine> medicines);

}
