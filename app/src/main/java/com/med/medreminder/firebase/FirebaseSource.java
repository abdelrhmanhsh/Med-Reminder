package com.med.medreminder.firebase;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.med.medreminder.model.Medicine;
import com.med.medreminder.model.User;

public interface FirebaseSource {

    void addUserToFirestore(User user, FirebaseDelegate firebaseDelegate);
    void isUserExist(String email, FirebaseDelegate firebaseDelegate);
    void updateMedFirestore(Medicine medicine, String email, long id);
    void deleteMedFirestore(String email, long id);
    void addMedToFirestore(Medicine medicine, String email, long id);
    void updateMedAmountFirestore(String email, long id, int newAmount);
    void updateStatusInFirestore(String helperEmail,String patientEmail,String status);
    void signup(LifecycleOwner lifecycleOwner, String email, String password, FirebaseDelegate firebaseDelegate, User user);

    void loginWithGoogle(Context context, firebaseLoginDelegate firebaseLoginDelegate);
    void isUserExistFromGoogleLogin(String email, firebaseLoginDelegate firebaseLoginDelegate, User user, String idToken);
    void addUserToFirestoreGoogleLogin(LifecycleOwner lifecycleOwner, User user, firebaseLoginDelegate firebaseLoginDelegate, String idToken, Context context);
    void authWithGoogle(String idToken, String email, Context context, firebaseLoginDelegate firebaseLoginDelegate);
    void login(String email, String password, Context context, firebaseLoginDelegate firebaseLoginDelegate);

    void getMedicinesOnDateFromFirebase(String email, long time, firebaseHomeMedsDelegate firebaseHomeMedsDelegate);

    void addHelperToFirestore(String helperEmail,String patientEmail);
    void addRequestsToFirestore(String email,String name,String status,String helper_email);
    void getInactiveMedsFromFirebase(String email,long time, FirebaseInactiveMedDelegate firebaseInactiveMedDelegate);
    void getActiveMedsFromFirebase(String email,long time, FirebaseActiveMedDelegate firebaseActiveMedDelegate);
    void Helpers(String myEmail, FirebaseHelpersDelegate firebaseHelpersDelegate);
    void loadRequests(String myEmail, FirebaseLoadRequestsDelegate firebaseLoadRequestsDelegate);
    void acceptedRequests(String myEmail, FirebaseDisplayMedFriendsDelegate firebaseDisplayMedFriendsDelegate);

    }