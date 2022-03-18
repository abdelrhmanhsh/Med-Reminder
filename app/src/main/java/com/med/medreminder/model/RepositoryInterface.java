package com.med.medreminder.model;

import java.util.List;

import androidx.lifecycle.LiveData;

import com.med.medreminder.firebase.FirebaseDelegate;

public interface RepositoryInterface {

    void insertMedicine(Medicine medicine);
    void updateMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);
    LiveData<Medicine> getMedicineById(long id);
    LiveData<List<Medicine>> getStoredMedicines();

    LiveData<List<Medicine>> getActiveMedications(long time, String email);
    LiveData<List<Medicine>> getInactiveMedications(long time, String email);

    LiveData<List<Medicine>> getActiveMedsOnDateSelected(long time, String email);


    void addUserToFirestore(User user);
    void addMedToFirestore(Medicine medicine, String email, long id);
    void updateMedFirestore(Medicine medicine, String email, long id);
    void deleteMedFirestore(String email, long id);


    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);
    void addHelperToFirestore(String helperEmail,String patientEmail);
    void addRequestsToFirestore(String email,String name,String status,String helper_email);


    void showActiveMedFirestore(String email);



}
