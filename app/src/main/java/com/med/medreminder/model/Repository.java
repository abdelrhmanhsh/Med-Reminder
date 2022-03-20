package com.med.medreminder.model;

import android.content.Context;

import com.med.medreminder.db.LocalSource;
import com.med.medreminder.firebase.FirebaseSource;
import com.med.medreminder.firebase.FirebaseWork;
import com.med.medreminder.firebase.firebaseDelegate;
import com.med.medreminder.firebase.firebaseHomeMedsDelegate;
import com.med.medreminder.firebase.firebaseLoginDelegate;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Repository implements RepositoryInterface {

    private Context context;
    LocalSource localSource;
    FirebaseSource firebaseSource;
    private static Repository repository = null;

    public Repository(Context context, LocalSource localSource, FirebaseSource firebaseSource) {
        this.context = context;
        this.localSource = localSource;
        this.firebaseSource = firebaseSource;
    }

    public static Repository getInstance(Context context, LocalSource localSource, FirebaseSource firebaseSource) {
        if(repository == null){
            repository = new Repository(context, localSource, firebaseSource);
        }
        return repository;
    }


    @Override
    public void insertMedicine(Medicine medicine) {
        localSource.insert(medicine);
    }

    @Override
    public void updateMedicine(Medicine medicine) {
        localSource.update(medicine);
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        localSource.delete(medicine);
    }

    @Override
    public LiveData<Medicine> getMedicineById(int id) {
        return localSource.getMedicineById(id);
    }

    @Override
    public LiveData<List<Medicine>> getStoredMedicines() {
        return localSource.getAllStoredMedicines();
    }

    @Override
    public LiveData<List<Medicine>> getActiveMedications(long time) {
        return localSource.getActiveMedications(time);
    }

    @Override
    public LiveData<List<Medicine>> getInactiveMedications(long time) {
        return localSource.getInactiveMedications(time);
    }

    @Override
    public LiveData<List<Medicine>> getActiveMedsOnDateSelected(long time) {
        return localSource.getActiveMedsOnDateSelected(time);
    }

    //in signup step
    @Override
    public void addUserToFirestore(User user, firebaseDelegate firebaseDelegate) {
      firebaseSource.addUserToFirestore(user,firebaseDelegate);
    }

    @Override
    public void isUserExist(String email, firebaseDelegate firebaseDelegate) {
        firebaseSource.isUserExist(email,firebaseDelegate);
    }

    @Override
    public void addMedToFirestore(Medicine medicine, String email) {
        firebaseSource.addMedToFirestore(medicine, email);
    }

    @Override
    public void signup(String email, String password, firebaseDelegate firebaseDelegate, User user) {
        firebaseSource.signup(email,password,firebaseDelegate, user);
    }

    @Override
    public void loginWithGoogle(Context context, firebaseLoginDelegate firebaseLoginDelegate) {
        firebaseSource.loginWithGoogle(context, firebaseLoginDelegate);
    }

    @Override
    public void isUserExistFromGoogle(String email, firebaseLoginDelegate firebaseLoginDelegate, User user, String idToken) {
        firebaseSource.isUserExistFromGoogleLogin(email,firebaseLoginDelegate,user,idToken);
    }

    //in login via google step
    @Override
    public void addUserToFirestore(User user, firebaseLoginDelegate firebaseLoginDelegate, String idToken, Context context) {
        firebaseSource.addUserToFirestoreGoogleLogin(user,firebaseLoginDelegate,idToken, context);
    }

    @Override
    public void authWithGoogle(String idToken, String email, Context context, firebaseLoginDelegate firebaseLoginDelegate) {
        firebaseSource.authWithGoogle(idToken, email, context, firebaseLoginDelegate);
    }

    @Override
    public void login(String email, String password, Context context, firebaseLoginDelegate firebaseLoginDelegate) {
        firebaseSource.login(email,password,context,firebaseLoginDelegate);
    }


    @Override
    public void updateStatusInFirestore(String helperEmail, String patientEmail, String status) {
        firebaseSource.updateStatusInFirestore(helperEmail,patientEmail,status);
    }

    @Override
    public void getMedicinesOnDateFromFirebase(String email, long time, firebaseHomeMedsDelegate firebaseHomeMedsDelegate) {
        firebaseSource.getMedicinesOnDateFromFirebase(email,time,firebaseHomeMedsDelegate);
    }
}
