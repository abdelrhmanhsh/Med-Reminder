package com.med.medreminder.model;

import android.content.Context;

import com.med.medreminder.firebase.FirebaseActiveMedDelegate;
import com.med.medreminder.firebase.FirebaseDelegate;
import com.med.medreminder.firebase.FirebaseInactiveMedDelegate;
import com.med.medreminder.firebase.firebaseHomeMedsDelegate;
import com.med.medreminder.firebase.firebaseLoginDelegate;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

public interface RepositoryInterface {

    void insertMedicine(Medicine medicine);
    void updateMedicine(Medicine medicine);
    void updateAllMedicines(String email);
    void deleteMedicine(Medicine medicine);
    void deleteAllMedicines();
    LiveData<Medicine> getMedicineById(long id);
    LiveData<List<Medicine>> getStoredMedicines();
    LiveData<List<Medicine>> getActiveMedications(long time, String email);
    LiveData<List<Medicine>> getInactiveMedications(long time, String email);

    LiveData<List<Medicine>> getActiveMedsOnDateSelected(long time,String email);


    void addUserToFirestore(User user, FirebaseDelegate firebaseDelegate);
    void isUserExist(String email, FirebaseDelegate firebaseDelegate);
    void addMedToFirestore(Medicine medicine, String email, long id);
    void signup(LifecycleOwner lifecycleOwner, String email, String password, FirebaseDelegate firebaseDelegate, User user);
    void updateMedFirestore(Medicine medicine, String email, long id);
    void deleteMedFirestore(String email, long id);

    void loginWithGoogle(Context context, firebaseLoginDelegate firebaseLoginDelegate);
    void isUserExistFromGoogle(String email, firebaseLoginDelegate firebaseLoginDelegate, User user, String idToken);
    void addUserToFirestore(LifecycleOwner lifecycleOwner, User user, firebaseLoginDelegate firebaseLoginDelegate, String idToken, Context context);
    void authWithGoogle(String idToken, String email, Context context, firebaseLoginDelegate firebaseLoginDelegate);
    void login(String email, String password, Context context,firebaseLoginDelegate firebaseLoginDelegate);

    void addHelperToFirestore(String helperEmail,String patientEmail);
    void addRequestsToFirestore(String email,String name,String status,String helper_email);
    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);


    void getMedicinesOnDateFromFirebase(String email, long time, firebaseHomeMedsDelegate firebaseHomeMedsDelegate);
    void getInactiveMedsFromFirebase(String email,long time, FirebaseInactiveMedDelegate firebaseInactiveMedDelegate);
    void getActiveMedsFromFirebase(String email,long time, FirebaseActiveMedDelegate firebaseActiveMedDelegate);


//    void insertMedStatus(MedStatus medStatus);
//    LiveData<List<MedStatus>> getMedStatus(String date, String email);

}
