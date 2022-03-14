package com.med.medreminder.db;


import com.med.medreminder.model.Medicine;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface LocalSource {

    void insert(Medicine medicine);
    LiveData<List<Medicine>> getAllStoredMedicines();

    LiveData<List<Medicine>> getActiveMedications(long time);
    LiveData<List<Medicine>> getInactiveMedications(long time);


}
