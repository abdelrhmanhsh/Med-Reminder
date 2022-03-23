package com.med.medreminder.model;

import android.content.Context;

import com.med.medreminder.db.LocalSource;
import com.med.medreminder.firebase.FirebaseActiveMedDelegate;
import com.med.medreminder.firebase.FirebaseDisplayMedFriendsDelegate;
import com.med.medreminder.firebase.FirebaseInactiveMedDelegate;
import com.med.medreminder.firebase.FirebaseHelpersDelegate;
import com.med.medreminder.firebase.FirebaseLoadRequestsDelegate;
import com.med.medreminder.firebase.FirebaseSource;
import com.med.medreminder.firebase.FirebaseDelegate;
import com.med.medreminder.firebase.firebaseHomeMedsDelegate;
import com.med.medreminder.firebase.firebaseLoginDelegate;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

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
    public void updateAllMedicines(String email) {
        localSource.updateAllMedicines(email);
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        localSource.delete(medicine);
    }

    @Override
    public void deleteAllMedicines() {
        localSource.deleteAllMedicines();
    }

    @Override
    public LiveData<Medicine> getMedicineById(long id) {
        return localSource.getMedicineById(id);
    }

    @Override
    public LiveData<List<Medicine>> getStoredMedicines() {
        return localSource.getAllStoredMedicines();
    }

    @Override
    public void updateMedFirestore(Medicine medicine, String email, long id) {
        firebaseSource.updateMedFirestore(medicine, email, id);
    }

    @Override
    public void deleteMedFirestore(String email, long id) {
        firebaseSource.deleteMedFirestore(email, id);
    }


    @Override
    public LiveData<List<Medicine>> getActiveMedications(long time,String email) {
        return localSource.getActiveMedications(time,email);
    }

    @Override
    public LiveData<List<Medicine>> getInactiveMedications(long time, String email) {
        return localSource.getInactiveMedications(time, email);
    }

    @Override
    public LiveData<List<Medicine>> getActiveMedsOnDateSelected(long time, String email) {
        return localSource.getActiveMedsOnDateSelected(time, email);
    }

    //in signup step
    @Override
    public void addUserToFirestore(User user, FirebaseDelegate firebaseDelegate) {
        firebaseSource.addUserToFirestore(user,firebaseDelegate);
    }

    @Override
    public void isUserExist(String email, FirebaseDelegate firebaseDelegate) {
        firebaseSource.isUserExist(email,firebaseDelegate);
    }

    @Override
    public void addMedToFirestore(Medicine medicine, String email,long id) {
        firebaseSource.addMedToFirestore(medicine, email, id);
    }

    @Override
    public void signup(LifecycleOwner lifecycleOwner, String email, String password, FirebaseDelegate firebaseDelegate, User user) {
        firebaseSource.signup(lifecycleOwner, email,password,firebaseDelegate, user);
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
    public void addUserToFirestore(LifecycleOwner lifecycleOwner, User user, firebaseLoginDelegate firebaseLoginDelegate, String idToken, Context context) {
        firebaseSource.addUserToFirestoreGoogleLogin(lifecycleOwner, user,firebaseLoginDelegate,idToken, context);
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

    @Override
    public void getInactiveMedsFromFirebase(String email, long time, FirebaseInactiveMedDelegate firebaseInactiveMedDelegate) {
        firebaseSource.getInactiveMedsFromFirebase(email, time, firebaseInactiveMedDelegate);
    }

    @Override
    public void getActiveMedsFromFirebase(String email, long time, FirebaseActiveMedDelegate firebaseActiveMedDelegate) {
        firebaseSource.getActiveMedsFromFirebase(email, time, firebaseActiveMedDelegate);
    }

    @Override
    public void Helpers(String myEmail, FirebaseHelpersDelegate firebaseHelpersDelegate){
        firebaseSource.Helpers(myEmail, firebaseHelpersDelegate);
    }

    @Override
    public void loadRequests(String myEmail, FirebaseLoadRequestsDelegate firebaseLoadRequestsDelegate) {
        firebaseSource.loadRequests(myEmail, firebaseLoadRequestsDelegate);
    }

    @Override
    public void acceptedRequests(String myEmail, FirebaseDisplayMedFriendsDelegate firebaseDisplayMedFriendsDelegate) {
        firebaseSource.acceptedRequests(myEmail, firebaseDisplayMedFriendsDelegate);
    }

    @Override
    public void addHelperToFirestore(String helperEmail, String patientEmail) {
        firebaseSource.addHelperToFirestore(helperEmail,patientEmail);
    }

    @Override
    public void addRequestsToFirestore(String email, String name, String status, String helper_email) {
        firebaseSource.addRequestsToFirestore(email,name,status,helper_email);
    }


}
