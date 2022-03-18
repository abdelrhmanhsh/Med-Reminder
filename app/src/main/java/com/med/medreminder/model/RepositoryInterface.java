package com.med.medreminder.model;

import com.med.medreminder.firebase.firebaseDelegate;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface RepositoryInterface {

    void insertMedicine(Medicine medicine);
    void updateMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);
    LiveData<Medicine> getMedicineById(int id);
    LiveData<List<Medicine>> getStoredMedicines();

    LiveData<List<Medicine>> getActiveMedications(long time);
    LiveData<List<Medicine>> getInactiveMedications(long time);

    LiveData<List<Medicine>> getActiveMedsOnDateSelected(long time);


    void addUserToFirestore(User user, firebaseDelegate firebaseDelegate);
    void isUserExist(String email, firebaseDelegate firebaseDelegate);
    void addMedToFirestore(Medicine medicine, String email);
   void signup(String email, String password, firebaseDelegate firebaseDelegate, User user);

    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);



}
