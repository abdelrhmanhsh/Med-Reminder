package com.med.medreminder.model;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface RepositoryInterface {

    void insertMedicine(Medicine medicine);
    void updateMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);
    LiveData<Medicine> getMedicineById(int id);
    LiveData<List<Medicine>> getStoredMedicines();


}
