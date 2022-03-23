package com.med.medreminder.db;

import com.med.medreminder.model.Medicine;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface LocalSource {

    void insert(Medicine medicine);
    void update(Medicine medicine);
    void updateAllMedicines(String email);
    void updateMedAmount(long id, int newAmount);
    void delete(Medicine medicine);
    void deleteAllMedicines();
    LiveData<Medicine> getMedicineById(long id);
    LiveData<List<Medicine>> getAllStoredMedicines();

    LiveData<List<Medicine>> getActiveMedications(long time, String email);
    LiveData<List<Medicine>> getInactiveMedications(long time, String email);

    LiveData<List<Medicine>> getActiveMedsOnDateSelected(long time, String email);

//    void insertMedStatus(MedStatus medStatus);
//    LiveData<List<MedStatus>> getMedStatus(String date, String email);

}
