package com.med.medreminder.model;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface RepositoryInterface {

    void insertMedicine(Medicine medicine);
    LiveData<List<Medicine>> getStoredMedicines();

    LiveData<List<Medicine>> getActiveMedications(long time);
    LiveData<List<Medicine>> getInactiveMedications(long time);


}
