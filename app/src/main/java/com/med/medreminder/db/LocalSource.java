package com.med.medreminder.db;


import com.med.medreminder.model.Medicine;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface LocalSource {

    void insert(Medicine medicine);
    void update(Medicine medicine);
    void delete(Medicine medicine);
    LiveData<Medicine> getMedicineById(long id);
    LiveData<List<Medicine>> getAllStoredMedicines();

    LiveData<List<Medicine>> getActiveMedications(long time);
    LiveData<List<Medicine>> getInactiveMedications(long time);

    LiveData<List<Medicine>> getActiveMedsOnDateSelected(long time, String email);


}
